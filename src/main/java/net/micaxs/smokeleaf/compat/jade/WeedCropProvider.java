// Java
package net.micaxs.smokeleaf.compat.jade;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

/**
 * Client-side only component provider for weed crops.
 * Server data is provided by WeedCropDataProvider (separate class since 1.21.6).
 */
public enum WeedCropProvider implements IBlockComponentProvider {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "weed_crop_jade");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    // Client tooltip
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        if (!hasAll(data, "n", "p", "k", "tn", "tp", "tk")) return;

        int n = data.getInt("n").orElse(0);
        int p = data.getInt("p").orElse(0);
        int k = data.getInt("k").orElse(0);
        int tn = data.getInt("tn").orElse(0);
        int tp = data.getInt("tp").orElse(0);
        int tk = data.getInt("tk").orElse(0);

        // Don't add the NPK if the plant doesn't have a target (e.g. non-weed crops)
        if (tn == 0 && tp == 0 && tk == 0) return;
        
        // Nitrogen
        tooltip.add(Component.literal("Nitrogen (N): ")
                .append(Component.literal(String.valueOf(n)).withStyle(colorForDiff(Math.abs(n - tn))))
                .append(Component.literal("/").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(String.valueOf(tn)).withStyle(ChatFormatting.GREEN)));

        // Phosphorus
        tooltip.add(Component.literal("Phosphorus (P): ")
                .append(Component.literal(String.valueOf(p)).withStyle(colorForDiff(Math.abs(p - tp))))
                .append(Component.literal("/").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(String.valueOf(tp)).withStyle(ChatFormatting.GREEN)));

        // Potassium
        tooltip.add(Component.literal("Potassium (K): ")
                .append(Component.literal(String.valueOf(k)).withStyle(colorForDiff(Math.abs(k - tk))))
                .append(Component.literal("/").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(String.valueOf(tk)).withStyle(ChatFormatting.GREEN)));
    }

    private static boolean hasAll(CompoundTag t, String... keys) {
        for (String k : keys) if (!t.contains(k)) return false;
        return true;
    }

    private static ChatFormatting colorForDiff(int diff) {
        if (diff == 0) return ChatFormatting.GREEN;
        if (diff == 1) return ChatFormatting.GOLD;
        if (diff == 2) return ChatFormatting.RED;
        return ChatFormatting.DARK_RED;
    }
}
