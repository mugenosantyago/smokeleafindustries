package net.micaxs.smokeleaf.item.custom;

import net.micaxs.smokeleaf.block.custom.BaseWeedCropBlock;
import net.micaxs.smokeleaf.block.custom.GrowPotBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class PlantAnalyzerItem extends Item {

    public PlantAnalyzerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        if (player == null) return InteractionResult.PASS;

        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof BaseWeedCropBlock) && !(state.getBlock() instanceof GrowPotBlock)) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide) {
            openAnalyzerScreen(pos);
        }

        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    public static void openAnalyzerScreen(BlockPos pos) {
        // Use reflection to avoid NoClassDefFoundError on server side
        try {
            Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft");
            Object minecraftInstance = minecraftClass.getMethod("getInstance").invoke(null);
            Class<?> screenClass = Class.forName("net.micaxs.smokeleaf.screen.custom.MagnifyingGlassScreen");
            Object screenInstance = screenClass.getConstructor(BlockPos.class).newInstance(pos);
            Class<?> screenBaseClass = Class.forName("net.minecraft.client.gui.screens.Screen");
            minecraftClass.getMethod("setScreen", screenBaseClass).invoke(minecraftInstance, screenInstance);
        } catch (Exception e) {
            // Log or handle the exception if client-side classes are not available
            System.err.println("Could not open MagnifyingGlassScreen: " + e.getMessage());
        }
    }


    // @Override removed - base Item class appendHoverText signature doesn't match in 1.21.8
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.smokeleafindustries.plant_analyzer").withStyle(ChatFormatting.GRAY));
    }

}
