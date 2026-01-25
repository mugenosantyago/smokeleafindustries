package net.micaxs.smokeleaf.recipe;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public record IngredientWithCount(Ingredient ingredient, int count) {
    // Decodes entries like { "ingredient":"...", "count":N } or { "item":"...", "count":N } (legacy) or { "tag":"...", "count":N } (legacy)
    // In 1.21.8, ingredient should be a string like "minecraft:stone" or "#c:stones"
    public static final Codec<IngredientWithCount> CODEC = new Codec<>() {
        @Override
        public <T> DataResult<Pair<IngredientWithCount, T>> decode(DynamicOps<T> ops, T input) {
            // First, read count from the object
            final int count = readCount(ops, input);
            
            // Try to find the ingredient in various ways
            var mapResult = ops.getMap(input);
            if (mapResult.result().isPresent()) {
                var m = mapResult.result().get();
                
                // Check for "ingredient" field (new format: {"ingredient": "item_id", "count": N})
                T ingredientElem = m.get("ingredient");
                if (ingredientElem != null) {
                    DataResult<Ingredient> ingr = Ingredient.CODEC.parse(ops, ingredientElem);
                    if (ingr.error().isPresent()) {
                        SmokeleafIndustries.LOGGER.error("[IngredientWithCount] Failed to parse 'ingredient' field: {}", ingr.error().get().message());
                    }
                    return ingr.map(i -> Pair.of(new IngredientWithCount(i, Math.max(1, count)), input));
                }
                
                // Check for legacy "item" field (old format: {"item": "mod:id", "count": N})
                T itemElem = m.get("item");
                if (itemElem != null) {
                    var idResult = ops.getStringValue(itemElem);
                    if (idResult.result().isPresent()) {
                        String itemId = idResult.result().get();
                        ResourceLocation rl = ResourceLocation.parse(itemId);
                        var itemHolder = BuiltInRegistries.ITEM.get(rl);
                        if (itemHolder.isPresent()) {
                            Ingredient ing = Ingredient.of(itemHolder.get().value());
                            SmokeleafIndustries.LOGGER.debug("[IngredientWithCount] Parsed legacy 'item' field: {}", itemId);
                            return DataResult.success(Pair.of(new IngredientWithCount(ing, Math.max(1, count)), input));
                        } else {
                            SmokeleafIndustries.LOGGER.error("[IngredientWithCount] Item not found in registry: {}", itemId);
                        }
                    }
                }
                
                // Check for legacy "tag" field (old format: {"tag": "mod:tag", "count": N})
                // Create Ingredient directly from TagKey instead of parsing string
                T tagElem = m.get("tag");
                if (tagElem != null) {
                    var idResult = ops.getStringValue(tagElem);
                    if (idResult.result().isPresent()) {
                        String tagId = idResult.result().get();
                        try {
                            // Create TagKey directly and build Ingredient from it
                            ResourceLocation tagLocation = ResourceLocation.parse(tagId);
                            TagKey<Item> tagKey = ItemTags.create(tagLocation);
                            
                            // Get the tag from registry and create Ingredient
                            var tagHolder = BuiltInRegistries.ITEM.get(tagKey);
                            if (tagHolder.isPresent()) {
                                Ingredient ing = Ingredient.of(tagHolder.get());
                                SmokeleafIndustries.LOGGER.debug("[IngredientWithCount] Parsed legacy 'tag' field: {} -> created Ingredient", tagId);
                                return DataResult.success(Pair.of(new IngredientWithCount(ing, Math.max(1, count)), input));
                            } else {
                                SmokeleafIndustries.LOGGER.warn("[IngredientWithCount] Tag not found in registry: {}, will try string parse", tagId);
                                // Fallback to string parsing
                                String newFormat = "#" + tagId;
                                T converted = ops.createString(newFormat);
                                DataResult<Ingredient> ingr = Ingredient.CODEC.parse(ops, converted);
                                if (ingr.error().isPresent()) {
                                    SmokeleafIndustries.LOGGER.error("[IngredientWithCount] Failed to parse tag '{}' as string: {}", tagId, ingr.error().get().message());
                                } else {
                                    SmokeleafIndustries.LOGGER.debug("[IngredientWithCount] Successfully parsed tag '{}' as string format", tagId);
                                }
                                return ingr.map(i -> Pair.of(new IngredientWithCount(i, Math.max(1, count)), input));
                            }
                        } catch (Exception e) {
                            SmokeleafIndustries.LOGGER.error("[IngredientWithCount] Error creating tag ingredient for '{}': {}", tagId, e.getMessage());
                        }
                    }
                }
            }
            
            // Fallback: try parsing the whole thing as an ingredient (unlikely for object format)
            DataResult<Ingredient> ingr = Ingredient.CODEC.parse(ops, input);
            if (ingr.error().isPresent()) {
                SmokeleafIndustries.LOGGER.error("[IngredientWithCount] Fallback parse failed: {}", ingr.error().get().message());
            }
            return ingr.map(i -> Pair.of(new IngredientWithCount(i, Math.max(1, count)), input));
        }

        private <T> int readCount(DynamicOps<T> ops, T input) {
            int c = 1;
            var map = ops.getMap(input);
            if (map.result().isPresent()) {
                var m = map.result().get();
                T countElem = m.get("count");
                if (countElem != null) {
                    var parsed = Codec.INT.parse(ops, countElem);
                    if (parsed.result().isPresent()) {
                        c = parsed.result().get();
                    }
                }
            }
            return c;
        }

        @Override
        public <T> DataResult<T> encode(IngredientWithCount value, DynamicOps<T> ops, T prefix) {
            var base = Ingredient.CODEC.encodeStart(ops, value.ingredient());
            if (base.result().isPresent()) {
                T enc = base.result().get();
                // Build a map with "ingredient" and "count"
                var builder = ops.mapBuilder();
                builder.add(ops.createString("ingredient"), enc);
                if (value.count() > 1) {
                    builder.add(ops.createString("count"), Codec.INT.encodeStart(ops, value.count()));
                }
                return builder.build(prefix);
            }
            return DataResult.error(() -> "Failed to encode ingredient");
        }
    };

    public static final StreamCodec<RegistryFriendlyByteBuf, IngredientWithCount> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, IngredientWithCount::ingredient,
                    ByteBufCodecs.VAR_INT, IngredientWithCount::count,
                    IngredientWithCount::new
            );

    // Builds a display ingredient that carries the required count
    // TODO: Update for 1.21.8 - Ingredient API changed, getItems() and of() methods removed
    public Ingredient asDisplayIngredient() {
        // For now, return the original ingredient - count display may need to be handled differently
        // The count is still available via the count() field of this record
        return ingredient;
    }
}