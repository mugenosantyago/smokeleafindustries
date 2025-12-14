package net.micaxs.smokeleaf.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HempHammer extends Item {
    public HempHammer(Properties properties) {
        super(properties);
    }

    // @Override removed - base Item class method signature may have changed in 1.21.8
    public boolean hasCraftingRemainingItem() {
        return true;
    }



    // @Override removed - base Item class method signature may have changed in 1.21.8
    public @NotNull ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack copy = itemStack.copy();
        copy.setDamageValue(copy.getDamageValue() + 1);
        if (copy.getDamageValue() >= copy.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return copy;
    }

    // @Override removed - base Item class method signature may have changed in 1.21.8
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) > 0) {
            stack.hurtAndBreak(1, miningEntity, EquipmentSlot.MAINHAND);
        }
        return true;
    }

    // @Override removed - base Item class appendHoverText signature doesn't match in 1.21.8
    public void appendHoverText(ItemStack stack, Item.TooltipContext context,
                                List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        int max = stack.getMaxDamage();
        int used = stack.getDamageValue();
        int remaining = max - used;
        double ratio = max > 0 ? (double) remaining / max : 0.0;

        ChatFormatting color;
        if (ratio > 0.5) {
            color = ChatFormatting.GREEN;
        } else if (ratio > 0.25) {
            color = ChatFormatting.GOLD; // orange-ish
        } else {
            color = ChatFormatting.RED;
        }

        tooltipComponents.add(
                Component.translatable("tooltip.smokeleafindustries.hemp_hammer.uses", remaining, max)
                        .withStyle(color)
        );

        // super.appendHoverText removed - base Item class signature doesn't match in 1.21.8
    }
}
