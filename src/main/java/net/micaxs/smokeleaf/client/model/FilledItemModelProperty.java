package net.micaxs.smokeleaf.client.model;

import com.mojang.serialization.MapCodec;
import net.micaxs.smokeleaf.component.ModDataComponentTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Conditional item model property: returns true when the Manual Grinder has contents.
 * Registered as "smokeleafindustries:filled".
 */
public record FilledItemModelProperty() implements ConditionalItemModelProperty {

    public static final MapCodec<FilledItemModelProperty> MAP_CODEC =
            MapCodec.unit(new FilledItemModelProperty());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level,
                       @Nullable LivingEntity entity, int seed, ItemDisplayContext context) {
        return stack.has(ModDataComponentTypes.MANUAL_GRINDER_CONTENTS.get());
    }

    @Override
    public MapCodec<FilledItemModelProperty> type() {
        return MAP_CODEC;
    }
}
