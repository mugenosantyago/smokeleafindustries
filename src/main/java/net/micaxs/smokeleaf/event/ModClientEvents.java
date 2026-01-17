package net.micaxs.smokeleaf.event;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.entity.ModBlockEntities;
import net.micaxs.smokeleaf.block.entity.client.DryingRackRenderer;
import net.micaxs.smokeleaf.block.entity.render.GrowPotRenderer;
import net.micaxs.smokeleaf.client.ModParticleFactories;
import net.micaxs.smokeleaf.client.brainmelt.BrainMeltInputHandler;
import net.micaxs.smokeleaf.fluid.BaseFluidType;
import net.micaxs.smokeleaf.fluid.ModFluidTypes;
import net.micaxs.smokeleaf.screen.ModMenuTypes;
import net.micaxs.smokeleaf.screen.custom.*;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;

/**
 * MOD bus client events (lifecycle and registration)
 * These fire during mod initialization
 * Must be registered programmatically to MOD event bus
 */
public class ModClientEvents {
    
    public static void register(net.neoforged.bus.api.IEventBus modEventBus) {
        modEventBus.addListener(ModClientEvents::onClientSetup);
        modEventBus.addListener(ModClientEvents::registerBER);
        modEventBus.addListener(ModClientEvents::onClientExtensions);
        modEventBus.addListener(ModClientEvents::registerScreens);
        
        // Register particle factories (also MOD bus event)
        ModParticleFactories.register(modEventBus);
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, BrainMeltInputHandler::onInputUpdate);
        event.enqueueWork(() -> {
            // Render layer API changed in 1.21.8 - fluid rendering now handled via IClientFluidTypeExtensions
            // ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_HASH_OIL_FLUID.get(), RenderType.translucent());
            // ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_HASH_OIL_FLUID.get(), RenderType.translucent());
            // ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_HEMP_OIL_FLUID.get(), RenderType.translucent());
            // ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_HEMP_OIL_FLUID.get(), RenderType.translucent());
            // ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_HASH_OIL_SLUDGE_FLUID.get(), RenderType.translucent());
            // ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_HASH_OIL_SLUDGE_FLUID.get(), RenderType.translucent());
            // ItemProperties API changed in 1.21.8 - temporarily commented out
            // ItemModelPredicates.register(ModItems.DNA_STRAND.get(), ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "full"), (stack, level, entity, seed) -> DNAStrandItem.isFull(stack) ? 1.0F : 0.0F);
            // ItemModelPredicates.register(ModItems.MANUAL_GRINDER.get(), ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "filled"), (stack, level, entity, seed) -> stack.has(ModDataComponentTypes.MANUAL_GRINDER_CONTENTS.get()) ? 1.0F : 0.0F);

            // ItemBlockRenderTypes.setRenderLayer(ModBlocks.REFLECTOR.get(), RenderType.translucent());
        });
    }

    private static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.DRYING_RACK_BE.get(), DryingRackRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.GROW_POT.get(), GrowPotRenderer::new);
    }

    private static void onClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(((BaseFluidType) ModFluidTypes.HEMP_OIL_FLUID_TYPE.get()).getClientFluidTypeExtensions(), ModFluidTypes.HEMP_OIL_FLUID_TYPE.get());
        event.registerFluidType(((BaseFluidType) ModFluidTypes.HASH_OIL_FLUID_TYPE.get()).getClientFluidTypeExtensions(), ModFluidTypes.HASH_OIL_FLUID_TYPE.get());
        event.registerFluidType(((BaseFluidType) ModFluidTypes.HASH_OIL_SLUDGE_FLUID_TYPE.get()).getClientFluidTypeExtensions(), ModFluidTypes.HASH_OIL_SLUDGE_FLUID_TYPE.get());
    }

    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.GENERATOR_MENU.get(), GeneratorScreen::new);
        event.register(ModMenuTypes.GRINDER_MENU.get(), GrinderScreen::new);
        event.register(ModMenuTypes.EXTRACTOR_MENU.get(), ExtractorScreen::new);
        event.register(ModMenuTypes.LIQUIFIER_MENU.get(), LiquifierScreen::new);
        event.register(ModMenuTypes.MUTATOR_MENU.get(), MutatorScreen::new);
        event.register(ModMenuTypes.SYNTHESIZER_MENU.get(), SynthesizerScreen::new);
        event.register(ModMenuTypes.SEQUENCER_MENU.get(), SequencerScreen::new);
        event.register(ModMenuTypes.DRYER_MENU.get(), DryerScreen::new);
    }
}
