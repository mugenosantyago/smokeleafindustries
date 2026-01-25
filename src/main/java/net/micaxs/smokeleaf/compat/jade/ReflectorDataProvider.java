package net.micaxs.smokeleaf.compat.jade;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.entity.ReflectorBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

/**
 * Server-side data provider for reflectors.
 * Separated from ReflectorProvider since Minecraft 1.21.6.
 */
public enum ReflectorDataProvider implements IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "reflector_data");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        if (!(accessor.getBlockEntity() instanceof ReflectorBlockEntity be)) return;

        ItemStack lamp = be.getLampStack();
        boolean hasLamp = lamp != null && !lamp.isEmpty();
        tag.putBoolean("hasLamp", hasLamp);

        if (hasLamp) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(lamp.getItem());
            if (id != null) tag.putString("lampId", id.toString());

            int remTicks = be.getLampRemainingTicks();
            int remSec = (remTicks + 19) / 20;
            tag.putInt("remSec", Math.max(0, remSec));
        }
    }
}
