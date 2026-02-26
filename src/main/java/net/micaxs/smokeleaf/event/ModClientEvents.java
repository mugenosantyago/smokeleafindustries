package net.micaxs.smokeleaf.event;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.entity.ModBlockEntities;
import net.micaxs.smokeleaf.block.entity.client.DryingRackRenderer;
import net.micaxs.smokeleaf.block.entity.render.GrowPotRenderer;
import net.micaxs.smokeleaf.client.ModParticleFactories;
import net.micaxs.smokeleaf.client.brainmelt.BrainMeltInputHandler;
import net.micaxs.smokeleaf.client.model.FilledItemModelProperty;
import net.micaxs.smokeleaf.client.model.FullItemModelProperty;
import net.micaxs.smokeleaf.fluid.BaseFluidType;
import net.micaxs.smokeleaf.fluid.ModFluidTypes;
import net.micaxs.smokeleaf.screen.ModMenuTypes;
import net.micaxs.smokeleaf.screen.custom.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent;
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
        modEventBus.addListener(ModClientEvents::registerConditionalItemModelProperties);

        // Register particle factories (also MOD bus event)
        ModParticleFactories.register(modEventBus);
    }

    private static boolean brainMeltHandlerRegistered = false;

    private static void onClientSetup(FMLClientSetupEvent event) {
        // Prevent double-registration of event handlers on second launch
        if (!brainMeltHandlerRegistered) {
            NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, BrainMeltInputHandler::onInputUpdate);
            brainMeltHandlerRegistered = true;
        }
    }

    private static void registerConditionalItemModelProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "filled"),
                FilledItemModelProperty.MAP_CODEC
        );
        event.register(
                ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "full"),
                FullItemModelProperty.MAP_CODEC
        );
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
