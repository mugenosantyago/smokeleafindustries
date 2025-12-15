package net.micaxs.smokeleaf.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SmokeleafGuideItem extends Item {
    public SmokeleafGuideItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide) {
            openGuideScreen();
        }
        return InteractionResult.SUCCESS;
    }

    private static void openGuideScreen() {
        // Use reflection to avoid loading client-side classes on server
        try {
            Class<?> screenClass = Class.forName("net.micaxs.smokeleaf.screen.custom.SmokeleafGuideScreen");
            screenClass.getMethod("open").invoke(null);
        } catch (Exception e) {
            // Silently fail on server side
        }
    }
}
