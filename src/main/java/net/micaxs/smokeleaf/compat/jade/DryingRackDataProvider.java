package net.micaxs.smokeleaf.compat.jade;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.entity.DryingRackBlockEntity;
import net.micaxs.smokeleaf.component.ModDataComponentTypes;
import net.micaxs.smokeleaf.item.custom.BaseBudItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

/**
 * Server-side data provider for drying racks.
 * Separated from DryingRackProvider since Minecraft 1.21.6.
 */
public enum DryingRackDataProvider implements IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "drying_rack_data");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        if (!(accessor.getBlockEntity() instanceof DryingRackBlockEntity rack)) return;

        for (int i = 0; i < DryingRackBlockEntity.SLOT_COUNT; i++) {
            ItemStack stack = rack.getItem(i);
            if (stack.isEmpty()) continue;

            boolean isBud = stack.getItem() instanceof BaseBudItem;
            boolean isDryBud = false;
            if (isBud) {
                Boolean dry = stack.get(ModDataComponentTypes.DRY);
                isDryBud = dry != null && dry;
            }

            int needed = rack.getTotalTimeForSlot(accessor.getLevel(), i);
            int prog = rack.getProgressForSlot(i);
            int remainTicks = 0;
            boolean active = false;

            if (isBud && isDryBud) {
                active = false;
                remainTicks = 0;
            } else if (needed > 0) {
                remainTicks = Math.max(0, needed - prog);
                active = remainTicks > 0;
            } else {
                active = false;
                remainTicks = 0;
            }

            int remainSeconds = (int) Math.ceil(remainTicks / 20.0);

            CompoundTag slot = new CompoundTag();
            slot.putString("id", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
            slot.putBoolean("bud", isBud);
            slot.putBoolean("dry", isDryBud);
            slot.putBoolean("active", active);
            slot.putInt("sec", remainSeconds);

            tag.put("S" + i, slot);
        }
    }
}
