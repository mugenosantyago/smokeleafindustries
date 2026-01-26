package net.micaxs.smokeleaf.compat.jade;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.entity.DryingRackBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

/**
 * Client-side only component provider for drying racks.
 * Server data is provided by DryingRackDataProvider (separate class since 1.21.6).
 */
public enum DryingRackProvider implements IBlockComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "drying_rack");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        for (int i = 0; i < DryingRackBlockEntity.SLOT_COUNT; i++) {
            String key = "S" + i;
            if (!accessor.getServerData().contains(key)) continue;

            CompoundTag s = accessor.getServerData().getCompound(key).orElse(null);
            if (s == null) continue;
            ResourceLocation id = ResourceLocation.tryParse(s.getString("id").orElse(""));
            if (id == null) continue;

            var itemHolder = BuiltInRegistries.ITEM.get(id);
            if (itemHolder.isEmpty()) continue;
            Item item = itemHolder.get().value();

            boolean isBud = s.getBoolean("bud").orElse(false);
            boolean isDryBud = s.getBoolean("dry").orElse(false);
            boolean active = s.getBoolean("active").orElse(false);
            int seconds = s.getInt("sec").orElse(0);

            Component name = Component.translatable(item.getDescriptionId()).withStyle(ChatFormatting.WHITE);
            Component line;

            if (isBud && isDryBud) {
                line = Component.empty()
                        .append(name)
                        .append(Component.literal(" (").withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal("Dry").withStyle(ChatFormatting.GREEN))
                        .append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY));
            } else if (active) {
                String mmss = formatMMSS(seconds);
                line = Component.empty()
                        .append(name)
                        .append(Component.literal(" (").withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal("Time Left: " + mmss).withStyle(ChatFormatting.GRAY))
                        .append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY));
            } else {
                line = name;
            }

            tooltip.add(line);
        }
    }

    private static String formatMMSS(int totalSeconds) {
        int m = Math.max(0, totalSeconds) / 60;
        int s = Math.max(0, totalSeconds) % 60;
        return m + ":" + (s < 10 ? "0" + s : String.valueOf(s));
    }
}
