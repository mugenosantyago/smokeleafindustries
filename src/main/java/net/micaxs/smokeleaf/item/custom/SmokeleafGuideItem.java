package net.micaxs.smokeleaf.item.custom;

import net.micaxs.smokeleaf.screen.custom.SmokeleafGuideScreen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SmokeleafGuideItem extends Item {
    public SmokeleafGuideItem(Properties properties) {
        super(properties);
    }

    // @Override - temporarily removed to check base class signature
    public ItemStack use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide) {
            SmokeleafGuideScreen.open();
        }
        return player.getItemInHand(usedHand); // success case
    }
}
