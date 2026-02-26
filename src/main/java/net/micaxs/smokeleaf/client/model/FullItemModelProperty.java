package net.micaxs.smokeleaf.client.model;

import com.mojang.serialization.MapCodec;
import net.micaxs.smokeleaf.item.custom.DNAStrandItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Conditional item model property: returns true when the DNA Strand is fully loaded.
 * Registered as "smokeleafindustries:full".
 */
public record FullItemModelProperty() implements ConditionalItemModelProperty {

    public static final MapCodec<FullItemModelProperty> MAP_CODEC =
            MapCodec.unit(new FullItemModelProperty());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level,
                       @Nullable LivingEntity entity, int seed, ItemDisplayContext context) {
        return DNAStrandItem.isFull(stack);
    }

    @Override
    public MapCodec<FullItemModelProperty> type() {
        return MAP_CODEC;
    }
}
