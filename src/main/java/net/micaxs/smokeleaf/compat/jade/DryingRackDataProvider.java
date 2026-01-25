package net.micaxs.smokeleaf.compat.jade;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.entity.DryingRackBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public enum DryingRackDataProvider implements IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "drying_rack_data");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        BlockEntity be = accessor.getBlockEntity();
        if (be instanceof DryingRackBlockEntity rack) {
            int itemCount = 0;
            for (int i = 0; i < DryingRackBlockEntity.SLOT_COUNT; i++) {
                ItemStack stack = rack.getItem(i);
                if (!stack.isEmpty()) itemCount++;
            }
            tag.putInt("itemCount", itemCount);
            tag.putInt("maxSlots", DryingRackBlockEntity.SLOT_COUNT);
        }
    }
}
