package net.micaxs.smokeleaf.event;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.custom.LiquifierBlock;
import net.micaxs.smokeleaf.block.entity.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID)
public class ModBusEvents {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Generator BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.GENERATOR_BE.get().get(), GeneratorBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.GENERATOR_BE.get().get(), GeneratorBlockEntity::getItemHandler);

        // Grinder BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.GRINDER_BE.get().get(), GrinderBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.GRINDER_BE.get().get(), GrinderBlockEntity::getItemHandler);

        // Extractor BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.EXTRACTOR_BE.get().get(), ExtractorBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.EXTRACTOR_BE.get().get(), ExtractorBlockEntity::getItemHandler);

        // Liquifier BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.LIQUIFIER_BE.get().get(), LiquifierBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.LIQUIFIER_BE.get().get(), LiquifierBlockEntity::getTank);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.LIQUIFIER_BE.get().get(), LiquifierBlockEntity::getItemHandler);

        // Mutator BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.MUTATOR_BE.get().get(), MutatorBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.MUTATOR_BE.get().get(), MutatorBlockEntity::getTank);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.MUTATOR_BE.get().get(), MutatorBlockEntity::getItemHandler);

        // Synthesizer BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.SYNTHESIZER_BE.get().get(), SynthesizerBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.SYNTHESIZER_BE.get().get(), SynthesizerBlockEntity::getItemHandler);

        // Sequencer BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.SEQUENCER_BE.get().get(), SequencerBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.SEQUENCER_BE.get().get(), SequencerBlockEntity::getItemHandler);

        // Dryer BlockEntity Capabilities
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.DRYER_BE.get().get(), DryerBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DRYER_BE.get().get(), DryerBlockEntity::getItemHandler);

    }




}
