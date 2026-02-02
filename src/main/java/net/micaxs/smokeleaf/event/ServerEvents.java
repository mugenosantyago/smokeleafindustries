package net.micaxs.smokeleaf.event;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.compat.RecipeCache;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID)
public class ServerEvents {
    
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        RecipeCache.cacheRecipes(event.getServer().getRecipeManager());
    }
}
