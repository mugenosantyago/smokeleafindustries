package net.micaxs.smokeleaf.datagen;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// DataGenerators temporarily disabled while fixing API issues
// Resources are already generated in src/generated/resources
// TODO: Re-enable datagen once NeoForge 1.21.8 API is fully documented
// @EventBusSubscriber(modid = SmokeleafIndustries.MODID)
public class DataGenerators {

    // @SubscribeEvent
    // public static void gatherData(GatherDataEvent event) {
    //     DataGenerator generator = event.getGenerator();
    //     PackOutput packOutput = generator.getPackOutput();
    //     CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
    //
    //     // Loot tables
    //     generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(), 
    //         List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), 
    //         lookupProvider));
    //
    //     // Tags
    //     BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(packOutput, lookupProvider);
    //     generator.addProvider(true, blockTagsProvider);
    //     generator.addProvider(true, new ModItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter()));
    //     generator.addProvider(true, new ModFluidTagsProvider(packOutput, lookupProvider));
    //
    //     // Other data providers
    //     generator.addProvider(true, new ModDataMapProvder(packOutput, lookupProvider));
    //     generator.addProvider(true, new ModGlobalLootModifierProvider(packOutput, lookupProvider));
    //     generator.addProvider(true, new ModWorldgenProvider(packOutput, lookupProvider));
    // }

}
