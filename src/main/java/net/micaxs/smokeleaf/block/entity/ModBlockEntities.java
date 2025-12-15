package net.micaxs.smokeleaf.block.entity;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.ModBlocks;
import net.micaxs.smokeleaf.block.custom.SequencerBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Set;
import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, SmokeleafIndustries.MODID);

    // BlockEntityTypes - will be registered in RegisterEvent handler after blocks are registered
    public static BlockEntityType<BaseWeedCropBlockEntity> BASE_WEED_CROP_BE;

    public static BlockEntityType<GeneratorBlockEntity> GENERATOR_BE;
    public static BlockEntityType<GrinderBlockEntity> GRINDER_BE;
    public static BlockEntityType<ExtractorBlockEntity> EXTRACTOR_BE;
    public static BlockEntityType<LiquifierBlockEntity> LIQUIFIER_BE;
    public static BlockEntityType<MutatorBlockEntity> MUTATOR_BE;
    public static BlockEntityType<SynthesizerBlockEntity> SYNTHESIZER_BE;
    public static BlockEntityType<SequencerBlockEntity> SEQUENCER_BE;
    public static BlockEntityType<DryerBlockEntity> DRYER_BE;
    public static BlockEntityType<DryingRackBlockEntity> DRYING_RACK_BE;
    public static BlockEntityType<ReflectorBlockEntity> REFLECTOR;
    public static BlockEntityType<GrowLightBlockEntity> GROW_LIGHT;
    public static BlockEntityType<GrowPotBlockEntity> GROW_POT;

    @EventBusSubscriber(modid = SmokeleafIndustries.MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void onRegister(RegisterEvent event) {
            // Register BlockEntityTypes only after blocks are registered
            if (event.getRegistryKey() == Registries.BLOCK_ENTITY_TYPE) {
                // Blocks should be registered by now, so we can safely access them
                BASE_WEED_CROP_BE = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "base_weed_crop_be"),
                    () -> new BlockEntityType<>(BaseWeedCropBlockEntity::new, Set.of(ModBlocks.HEMP_CROP.get()), false)
                );
                GENERATOR_BE = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "generator_be"),
                    () -> new BlockEntityType<>(GeneratorBlockEntity::new, Set.of(ModBlocks.GENERATOR.get()), false)
                );
                GRINDER_BE = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "grinder_be"),
                    () -> new BlockEntityType<>(GrinderBlockEntity::new, Set.of(ModBlocks.GRINDER.get()), false)
                );
                EXTRACTOR_BE = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "extractor_be"),
                    () -> new BlockEntityType<>(ExtractorBlockEntity::new, Set.of(ModBlocks.EXTRACTOR.get()), false)
                );
                LIQUIFIER_BE = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "liquifier_be"),
                    () -> new BlockEntityType<>(LiquifierBlockEntity::new, Set.of(ModBlocks.LIQUIFIER.get()), false)
                );
                MUTATOR_BE = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "mutator_be"),
                    () -> new BlockEntityType<>(MutatorBlockEntity::new, Set.of(ModBlocks.MUTATOR.get()), false)
                );
                SYNTHESIZER_BE = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "synthesizer_be"),
                    () -> new BlockEntityType<>(SynthesizerBlockEntity::new, Set.of(ModBlocks.SYNTHESIZER.get()), false)
                );
                SEQUENCER_BE = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "sequencer_be"),
                    () -> new BlockEntityType<>(SequencerBlockEntity::new, Set.of(ModBlocks.SEQUENCER.get()), false)
                );
                DRYER_BE = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "dryer_be"),
                    () -> new BlockEntityType<>(DryerBlockEntity::new, Set.of(ModBlocks.DRYER.get()), false)
                );
                DRYING_RACK_BE = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "drying_rack_be"),
                    () -> new BlockEntityType<>(DryingRackBlockEntity::new, Set.of(ModBlocks.DRYING_RACK.get()), false)
                );
                REFLECTOR = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "reflector_be"),
                    () -> new BlockEntityType<>(ReflectorBlockEntity::new, Set.of(ModBlocks.REFLECTOR.get()), false)
                );
                GROW_LIGHT = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "grow_light_be"),
                    () -> new BlockEntityType<>(GrowLightBlockEntity::new, Set.of(ModBlocks.LED_LIGHT.get()), false)
                );
                GROW_POT = event.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "grow_pot_be"),
                    () -> new BlockEntityType<>(GrowPotBlockEntity::new, Set.of(ModBlocks.GROW_POT.get()), false)
                );
            }
        }
    }

    public static void register(IEventBus eventBus) {
        // BlockEntityTypes are now registered via RegisterEvent handler
        // No need to register DeferredRegister
    }

}
