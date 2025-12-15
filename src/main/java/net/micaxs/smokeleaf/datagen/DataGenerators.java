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

@EventBusSubscriber(modid = SmokeleafIndustries.MODID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // LootTableProvider API changed in 1.21.8 - temporarily disabled
        // TODO: Fix LootTableProvider for 1.21.8
        // generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));

        // ModRecipeProvider temporarily disabled - RecipeProvider API changed significantly in 1.21.8
        // TODO: Fix ModRecipeProvider for 1.21.8 - all helper methods (slab, pressurePlate, wall, shapeless, shaped) have new signatures
        // generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));

        // Tag providers temporarily disabled - constructor signatures changed in 1.21.8
        // TODO: Fix tag provider constructors for 1.21.8
        // BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(packOutput, lookupProvider, null);
        // generator.addProvider(event.includeServer(), blockTagsProvider);
        // generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), null));
        // generator.addProvider(event.includeServer(), new ModFluidTagsProvider(packOutput, lookupProvider, null));

        // Model generators removed in NeoForge 1.21.8 - need to use vanilla data generators or manual JSON
        // generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, null));
        // generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, null));

        // Data generation providers temporarily disabled - API changed in 1.21.8
        // TODO: Fix these providers for 1.21.8
        // generator.addProvider(event.includeServer(), new ModDataMapProvder(packOutput, lookupProvider));
        // generator.addProvider(event.includeServer(), new ModGlobalLootModifierProvider(packOutput, lookupProvider));
        // AdvancementProvider removed in NeoForge 1.21.8 - need to use vanilla approach
        // generator.addProvider(event.includeClient(), new ModAdvancementProvider(packOutput, lookupProvider, null));
        // generator.addProvider(event.includeServer(), new ModWorldgenProvider(packOutput, lookupProvider));

    }

}
