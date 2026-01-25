// Java
package net.micaxs.smokeleaf.compat.jade;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

/**
 * Client-side only component provider for reflectors.
 * Server data is provided by ReflectorDataProvider (separate class since 1.21.6).
 */
public enum ReflectorProvider implements IBlockComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID =
            ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "reflector");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();

        boolean hasLamp = data.getBoolean("hasLamp").orElse(false);
        if (!hasLamp) {
            tooltip.add(Component.literal("Lamp: ").withStyle(ChatFormatting.GRAY)
                    .append(Component.literal("None").withStyle(ChatFormatting.DARK_GRAY)));
            return;
        }

        // Lamp name
        Item item = null;
        if (data.contains("lampId")) {
            ResourceLocation id = ResourceLocation.tryParse(data.getString("lampId").orElse(""));
            if (id != null) item = BuiltInRegistries.ITEM.get(id).map(h -> h.value()).orElse(null);
        }
        Component lampName = item != null
                ? Component.translatable(item.getDescriptionId()).withStyle(ChatFormatting.WHITE)
                : Component.literal("Unknown").withStyle(ChatFormatting.DARK_GRAY);

        tooltip.add(Component.literal("Lamp: ").withStyle(ChatFormatting.GRAY).append(lampName));

        // Time left
        int sec = data.getInt("remSec").orElse(0);
        tooltip.add(Component.literal("Time Left: ").withStyle(ChatFormatting.GRAY)
                .append(Component.literal(formatMMSS(sec)).withStyle(ChatFormatting.WHITE)));
    }

    private static String formatMMSS(int totalSeconds) {
        int m = Math.max(0, totalSeconds) / 60;
        int s = Math.max(0, totalSeconds) % 60;
        return String.format("%02d:%02d", m, s);
    }
}
