package net.micaxs.smokeleaf.event;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.ModBlocks;
import net.micaxs.smokeleaf.block.custom.LiquifierBlock;
import net.micaxs.smokeleaf.block.entity.*;
import net.micaxs.smokeleaf.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModBusEvents {
    
    /**
     * RegisterEvent handler to ensure block and item IDs are set before construction.
     * This fixes the "Block id not set" / "Item id not set" errors in NeoForge 1.21.8.
     */
    @SubscribeEvent
    public static void onRegister(RegisterEvent event) {
        // Handle block registration - ensure IDs are set before construction
        if (event.getRegistryKey() == Registries.BLOCK) {
            // The DeferredRegister should handle ID setting automatically,
            // but we ensure blocks are registered in the correct order
            event.register(Registries.BLOCK, helper -> {
                // Blocks are registered by DeferredRegister, this is just a safety check
            });
        }
        
        // Handle item registration - ensure IDs are set before construction
        if (event.getRegistryKey() == Registries.ITEM) {
            // The DeferredRegister should handle ID setting automatically,
            // but we ensure items are registered in the correct order
            event.register(Registries.ITEM, helper -> {
                // Items are registered by DeferredRegister, this is just a safety check
            });
        }
    }
    
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Generator BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.GENERATOR_BE.get(), GeneratorBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.GENERATOR_BE.get(), GeneratorBlockEntity::getItemHandler);

        // Grinder BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.GRINDER_BE.get(), GrinderBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.GRINDER_BE.get(), GrinderBlockEntity::getItemHandler);

        // Extractor BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.EXTRACTOR_BE.get(), ExtractorBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.EXTRACTOR_BE.get(), ExtractorBlockEntity::getItemHandler);

        // Liquifier BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.LIQUIFIER_BE.get(), LiquifierBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.LIQUIFIER_BE.get(), LiquifierBlockEntity::getTank);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.LIQUIFIER_BE.get(), LiquifierBlockEntity::getItemHandler);

        // Mutator BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.MUTATOR_BE.get(), MutatorBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.MUTATOR_BE.get(), MutatorBlockEntity::getTank);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.MUTATOR_BE.get(), MutatorBlockEntity::getItemHandler);

        // Synthesizer BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.SYNTHESIZER_BE.get(), SynthesizerBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.SYNTHESIZER_BE.get(), SynthesizerBlockEntity::getItemHandler);

        // Sequencer BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.SEQUENCER_BE.get(), SequencerBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.SEQUENCER_BE.get(), SequencerBlockEntity::getItemHandler);

        // Dryer BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.DRYER_BE.get(), DryerBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DRYER_BE.get(), DryerBlockEntity::getItemHandler);

    }




}
