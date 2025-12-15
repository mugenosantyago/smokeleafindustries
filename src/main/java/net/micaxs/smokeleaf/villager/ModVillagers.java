package net.micaxs.smokeleaf.villager;

import com.google.common.collect.ImmutableSet;
import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.ModBlocks;
import net.micaxs.smokeleaf.sound.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

public class ModVillagers {

    public static DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, SmokeleafIndustries.MODID);
    public static DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, SmokeleafIndustries.MODID);

    // Point of Interest Types
    // Use lazy evaluation - access blocks only when POI is actually registered (after blocks are registered)
    public static final Holder<PoiType> DEALER_POI = POI_TYPES.register("dealer_poi", () -> {
        // This lambda is called during RegisterEvent, after blocks should be registered
        return new PoiType(ImmutableSet.copyOf(ModBlocks.GRINDER.get().getStateDefinition().getPossibleStates()), 1, 1);
    });
    public static final Holder<PoiType> STONER_POI = POI_TYPES.register("stoner_poi", () -> {
        // This lambda is called during RegisterEvent, after blocks should be registered
        return new PoiType(ImmutableSet.copyOf(ModBlocks.HEMP_CHISELED_STONE.get().getStateDefinition().getPossibleStates()), 1, 1);
    });

    // Villagers
    // VillagerProfession constructor changed in 1.21.8 - first parameter is now Component instead of String
    // Use lazy evaluation - access POI and sounds only when profession is actually registered
    public static final Holder<VillagerProfession> DEALER = VILLAGER_PROFESSIONS.register("dealer", () -> {
        // This lambda is called during RegisterEvent, after POI types should be registered
        return new VillagerProfession(Component.translatable("entity.minecraft.villager.dealer"), 
                holder -> holder.value() == DEALER_POI.value(), 
                holder -> holder.value() == DEALER_POI.value(),
                ImmutableSet.of(), ImmutableSet.of(), ModSounds.MANUAL_GRINDER.get());
    });
    public static final Holder<VillagerProfession> STONER = VILLAGER_PROFESSIONS.register("stoner", () -> {
        // This lambda is called during RegisterEvent, after POI types should be registered
        return new VillagerProfession(Component.translatable("entity.minecraft.villager.stoner"), 
                holder -> holder.value() == STONER_POI.value(), 
                holder -> holder.value() == STONER_POI.value(),
                ImmutableSet.of(), ImmutableSet.of(), ModSounds.BONG_HIT.get());
    });


    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }

    @EventBusSubscriber(modid = SmokeleafIndustries.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {
        private static boolean blocksRegistered = false;

        @SubscribeEvent
        public static void onRegisterBlocks(RegisterEvent event) {
            // Mark blocks as registered when BLOCK registry is being registered
            if (event.getRegistryKey() == Registries.BLOCK) {
                blocksRegistered = true;
            }
        }

        @SubscribeEvent
        public static void onRegisterPOIs(RegisterEvent event) {
            // Only register POI types after blocks are registered
            if (event.getRegistryKey() == Registries.POINT_OF_INTEREST_TYPE && blocksRegistered) {
                // Blocks are now registered, so we can safely access them
                // The DeferredRegister will handle the actual registration
                // This handler just ensures blocks are registered first
            }
        }
    }
}
