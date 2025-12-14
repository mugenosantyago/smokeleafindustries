package net.micaxs.smokeleaf.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EmptyTinctureItem extends Item {
    public EmptyTinctureItem(Properties properties) {
        super(properties);
    }

    // @Override removed - base Item class appendHoverText signature doesn't match in 1.21.8
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        // super.appendHoverText removed - base Item class signature doesn't match in 1.21.8
        tooltip.add(Component.translatable("tooltip.smokeleafindustries.tincture").withStyle(ChatFormatting.GRAY));
    }
}
