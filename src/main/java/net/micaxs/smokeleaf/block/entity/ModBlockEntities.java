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


    // BlockEntityType.Builder API changed in 1.21.8 - trying BlockEntityType.of() instead
    public static final Supplier<BlockEntityType<BaseWeedCropBlockEntity>> BASE_WEED_CROP_BE = BLOCK_ENTITIES.register("base_weed_crop_be",
            () -> BlockEntityType.of(BaseWeedCropBlockEntity::new,
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
    ));


    // BlockEntityType.Builder API changed in 1.21.8 - trying BlockEntityType.of() instead
    public static final Supplier<BlockEntityType<GeneratorBlockEntity>> GENERATOR_BE = BLOCK_ENTITIES.register("generator_be",
            () -> BlockEntityType.of(GeneratorBlockEntity::new, ModBlocks.GENERATOR.get()));

    // BlockEntityType.Builder API changed in 1.21.8 - trying BlockEntityType.of() instead
    public static final Supplier<BlockEntityType<GrinderBlockEntity>> GRINDER_BE = BLOCK_ENTITIES.register("grinder_be",
            () -> BlockEntityType.of(GrinderBlockEntity::new, ModBlocks.GRINDER.get()));

    public static final Supplier<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR_BE = BLOCK_ENTITIES.register("extractor_be",
            () -> BlockEntityType.of(ExtractorBlockEntity::new, ModBlocks.EXTRACTOR.get()));

    public static final Supplier<BlockEntityType<LiquifierBlockEntity>> LIQUIFIER_BE = BLOCK_ENTITIES.register("liquifier_be",
            () -> BlockEntityType.of(LiquifierBlockEntity::new, ModBlocks.LIQUIFIER.get()));

    public static final Supplier<BlockEntityType<MutatorBlockEntity>> MUTATOR_BE = BLOCK_ENTITIES.register("mutator_be",
            () -> BlockEntityType.of(MutatorBlockEntity::new, ModBlocks.MUTATOR.get()));

    public static final Supplier<BlockEntityType<SynthesizerBlockEntity>> SYNTHESIZER_BE = BLOCK_ENTITIES.register("synthesizer_be",
            () -> BlockEntityType.of(SynthesizerBlockEntity::new, ModBlocks.SYNTHESIZER.get()));

    public static final Supplier<BlockEntityType<SequencerBlockEntity>> SEQUENCER_BE = BLOCK_ENTITIES.register("sequencer_be",
            () -> BlockEntityType.of(SequencerBlockEntity::new, ModBlocks.SEQUENCER.get()));

    public static final Supplier<BlockEntityType<DryerBlockEntity>> DRYER_BE = BLOCK_ENTITIES.register("dryer_be",
            () -> BlockEntityType.of(DryerBlockEntity::new, ModBlocks.DRYER.get()));

    public static final Supplier<BlockEntityType<DryingRackBlockEntity>> DRYING_RACK_BE = BLOCK_ENTITIES.register("drying_rack_be",
            () -> BlockEntityType.of(DryingRackBlockEntity::new, ModBlocks.DRYING_RACK.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ReflectorBlockEntity>> REFLECTOR = BLOCK_ENTITIES.register("reflector_be",
            () -> BlockEntityType.of(ReflectorBlockEntity::new, ModBlocks.REFLECTOR.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrowLightBlockEntity>> GROW_LIGHT = BLOCK_ENTITIES.register("grow_light_be",
            () -> BlockEntityType.of(GrowLightBlockEntity::new, ModBlocks.LED_LIGHT.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrowPotBlockEntity>> GROW_POT = BLOCK_ENTITIES.register("grow_pot_be",
            () -> BlockEntityType.of(GrowPotBlockEntity::new, ModBlocks.GROW_POT.get()));



    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
