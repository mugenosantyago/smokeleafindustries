package net.micaxs.smokeleaf.block.entity;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.ModBlocks;
import net.micaxs.smokeleaf.block.custom.SequencerBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, SmokeleafIndustries.MODID);


    // BlockEntityType API changed in 1.21.8 - Builder.of() may have been removed
    // TODO: Fix BlockEntityType creation API for 1.21.8
    public static final Supplier<BlockEntityType<BaseWeedCropBlockEntity>> BASE_WEED_CROP_BE = BLOCK_ENTITIES.register("base_weed_crop_be",
            () -> {
                // Temporarily using Builder pattern - may need to change
                var builder = BlockEntityType.Builder.of(BaseWeedCropBlockEntity::new,
                    ModBlocks.HEMP_CROP.get(),
                    ModBlocks.WHITE_WIDOW_CROP.get(),
                    ModBlocks.BUBBLE_KUSH_CROP.get(),
                    ModBlocks.LEMON_HAZE_CROP.get(),
                    ModBlocks.SOUR_DIESEL_CROP.get(),
                    ModBlocks.BLUE_ICE_CROP.get(),
                    ModBlocks.BUBBLEGUM_CROP.get(),
                    ModBlocks.PURPLE_HAZE_CROP.get(),
                    ModBlocks.OG_KUSH_CROP.get(),
                    ModBlocks.JACK_HERER_CROP.get(),
                    ModBlocks.GARY_PEYTON_CROP.get(),
                    ModBlocks.AMNESIA_HAZE_CROP.get(),
                    ModBlocks.AK47_CROP.get(),
                    ModBlocks.GHOST_TRAIN_CROP.get(),
                    ModBlocks.GRAPE_APE_CROP.get(),
                    ModBlocks.COTTON_CANDY_CROP.get(),
                    ModBlocks.BANANA_KUSH_CROP.get(),
                    ModBlocks.CARBON_FIBER_CROP.get(),
                    ModBlocks.BIRTHDAY_CAKE_CROP.get(),
                    ModBlocks.BLUE_COOKIES_CROP.get(),
                    ModBlocks.AFGHANI_CROP.get(),
                    ModBlocks.MOONBOW_CROP.get(),
                    ModBlocks.LAVA_CAKE_CROP.get(),
                    ModBlocks.JELLY_RANCHER_CROP.get(),
                    ModBlocks.STRAWBERRY_SHORTCAKE_CROP.get(),
                    ModBlocks.PINK_KUSH_CROP.get()
                );
                // TODO: Fix build() method call for 1.21.8
                return builder.build();
            });


    // BlockEntityType.Builder API removed in 1.21.8
    // TODO: Fix BlockEntityType creation API for 1.21.8
    public static final Supplier<BlockEntityType<GeneratorBlockEntity>> GENERATOR_BE = BLOCK_ENTITIES.register("generator_be",
            () -> {
                throw new UnsupportedOperationException("BlockEntityType.Builder API removed in 1.21.8 - TODO: Fix BlockEntityType creation");
            });

    // BlockEntityType.Builder API removed in 1.21.8
    // TODO: Fix BlockEntityType creation API for 1.21.8
    public static final Supplier<BlockEntityType<GrinderBlockEntity>> GRINDER_BE = BLOCK_ENTITIES.register("grinder_be",
            () -> { throw new UnsupportedOperationException("BlockEntityType.Builder API removed in 1.21.8 - TODO: Fix"); });

    public static final Supplier<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR_BE = BLOCK_ENTITIES.register("extractor_be",
            () -> { throw new UnsupportedOperationException("BlockEntityType.Builder API removed in 1.21.8 - TODO: Fix"); });

    public static final Supplier<BlockEntityType<LiquifierBlockEntity>> LIQUIFIER_BE = BLOCK_ENTITIES.register("liquifier_be",
            () -> { throw new UnsupportedOperationException("BlockEntityType.Builder API removed in 1.21.8 - TODO: Fix"); });

    public static final Supplier<BlockEntityType<MutatorBlockEntity>> MUTATOR_BE = BLOCK_ENTITIES.register("mutator_be",
            () -> { throw new UnsupportedOperationException("BlockEntityType.Builder API removed in 1.21.8 - TODO: Fix"); });

    public static final Supplier<BlockEntityType<SynthesizerBlockEntity>> SYNTHESIZER_BE = BLOCK_ENTITIES.register("synthesizer_be",
            () -> { throw new UnsupportedOperationException("BlockEntityType.Builder API removed in 1.21.8 - TODO: Fix"); });

    public static final Supplier<BlockEntityType<SequencerBlockEntity>> SEQUENCER_BE = BLOCK_ENTITIES.register("sequencer_be",
            () -> { throw new UnsupportedOperationException("BlockEntityType.Builder API removed in 1.21.8 - TODO: Fix"); });

    public static final Supplier<BlockEntityType<DryerBlockEntity>> DRYER_BE = BLOCK_ENTITIES.register("dryer_be",
            () -> { throw new UnsupportedOperationException("BlockEntityType.Builder API removed in 1.21.8 - TODO: Fix"); });

    public static final Supplier<BlockEntityType<DryingRackBlockEntity>> DRYING_RACK_BE = BLOCK_ENTITIES.register("drying_rack_be",
            () -> { throw new UnsupportedOperationException("BlockEntityType.Builder API removed in 1.21.8 - TODO: Fix"); });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ReflectorBlockEntity>> REFLECTOR = BLOCK_ENTITIES.register("reflector_be",
            () -> { throw new UnsupportedOperationException("BlockEntityType.Builder API removed in 1.21.8 - TODO: Fix"); });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrowLightBlockEntity>> GROW_LIGHT = BLOCK_ENTITIES.register("grow_light_be",
            () -> { throw new UnsupportedOperationException("BlockEntityType.Builder API removed in 1.21.8 - TODO: Fix"); });

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrowPotBlockEntity>> GROW_POT = BLOCK_ENTITIES.register("grow_pot_be",
            () -> { throw new UnsupportedOperationException("BlockEntityType.Builder API removed in 1.21.8 - TODO: Fix"); });



    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
