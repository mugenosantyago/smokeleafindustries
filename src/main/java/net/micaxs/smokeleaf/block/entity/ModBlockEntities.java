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

    // BlockEntityTypes - using lazy evaluation to ensure blocks are registered first
    // The lambda is evaluated when BlockEntityType is registered, at which point blocks should be available
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BaseWeedCropBlockEntity>> BASE_WEED_CROP_BE = BLOCK_ENTITIES.register("base_weed_crop_be",
            () -> {
                // Get block when BlockEntityType is actually created (during registration)
                Block block = ModBlocks.HEMP_CROP.get();
                return new BlockEntityType<BaseWeedCropBlockEntity>(BaseWeedCropBlockEntity::new, Set.of(block), false);
            });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GeneratorBlockEntity>> GENERATOR_BE = BLOCK_ENTITIES.register("generator_be",
            () -> new BlockEntityType<GeneratorBlockEntity>(GeneratorBlockEntity::new, Set.of(ModBlocks.GENERATOR.get()), false));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrinderBlockEntity>> GRINDER_BE = BLOCK_ENTITIES.register("grinder_be",
            () -> new BlockEntityType<GrinderBlockEntity>(GrinderBlockEntity::new, Set.of(ModBlocks.GRINDER.get()), false));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ExtractorBlockEntity>> EXTRACTOR_BE = BLOCK_ENTITIES.register("extractor_be",
            () -> new BlockEntityType<ExtractorBlockEntity>(ExtractorBlockEntity::new, Set.of(ModBlocks.EXTRACTOR.get()), false));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LiquifierBlockEntity>> LIQUIFIER_BE = BLOCK_ENTITIES.register("liquifier_be",
            () -> new BlockEntityType<LiquifierBlockEntity>(LiquifierBlockEntity::new, Set.of(ModBlocks.LIQUIFIER.get()), false));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MutatorBlockEntity>> MUTATOR_BE = BLOCK_ENTITIES.register("mutator_be",
            () -> new BlockEntityType<MutatorBlockEntity>(MutatorBlockEntity::new, Set.of(ModBlocks.MUTATOR.get()), false));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SynthesizerBlockEntity>> SYNTHESIZER_BE = BLOCK_ENTITIES.register("synthesizer_be",
            () -> new BlockEntityType<SynthesizerBlockEntity>(SynthesizerBlockEntity::new, Set.of(ModBlocks.SYNTHESIZER.get()), false));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SequencerBlockEntity>> SEQUENCER_BE = BLOCK_ENTITIES.register("sequencer_be",
            () -> new BlockEntityType<SequencerBlockEntity>(SequencerBlockEntity::new, Set.of(ModBlocks.SEQUENCER.get()), false));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DryerBlockEntity>> DRYER_BE = BLOCK_ENTITIES.register("dryer_be",
            () -> new BlockEntityType<DryerBlockEntity>(DryerBlockEntity::new, Set.of(ModBlocks.DRYER.get()), false));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DryingRackBlockEntity>> DRYING_RACK_BE = BLOCK_ENTITIES.register("drying_rack_be",
            () -> new BlockEntityType<DryingRackBlockEntity>(DryingRackBlockEntity::new, Set.of(ModBlocks.DRYING_RACK.get()), false));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ReflectorBlockEntity>> REFLECTOR = BLOCK_ENTITIES.register("reflector_be",
            () -> new BlockEntityType<ReflectorBlockEntity>(ReflectorBlockEntity::new, Set.of(ModBlocks.REFLECTOR.get()), false));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrowLightBlockEntity>> GROW_LIGHT = BLOCK_ENTITIES.register("grow_light_be",
            () -> new BlockEntityType<GrowLightBlockEntity>(GrowLightBlockEntity::new, Set.of(ModBlocks.LED_LIGHT.get()), false));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrowPotBlockEntity>> GROW_POT = BLOCK_ENTITIES.register("grow_pot_be",
            () -> new BlockEntityType<GrowPotBlockEntity>(GrowPotBlockEntity::new, Set.of(ModBlocks.GROW_POT.get()), false));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
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
        public static void onRegisterBlockEntities(RegisterEvent event) {
            // Only register BlockEntityTypes after blocks are registered
            if (event.getRegistryKey() == Registries.BLOCK_ENTITY_TYPE && blocksRegistered) {
                // Blocks are now registered, so we can safely access them
                // The DeferredRegister will handle the actual registration
                // This handler just ensures blocks are registered first
            }
        }
    }

}
