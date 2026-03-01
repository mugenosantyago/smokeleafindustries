package net.micaxs.smokeleaf.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.ModBlocks;
import net.micaxs.smokeleaf.compat.jei.*;
import net.micaxs.smokeleaf.item.ModItems;
import net.micaxs.smokeleaf.recipe.*;
import net.micaxs.smokeleaf.screen.custom.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

@JeiPlugin
public class JEISmokeleafInudstriesPlugin implements IModPlugin {

    private static IJeiRuntime jeiRuntime;

    /** Called from client events when recipes arrive from a dedicated server. */
    public static void populateFromClientAndRefresh() {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
        if (mc.level != null) {
            RecipeCache.cacheRecipes(mc.level.getRecipeManager());
            SmokeleafIndustries.LOGGER.info("JEI: RecipeCache populated from client recipe manager ({} total recipes)",
                    mc.level.getRecipeManager().getRecipes().size());
        }
        if (jeiRuntime != null && RecipeCache.isCachePopulated()) {
            addAllRecipesToRuntime(jeiRuntime);
        }
    }

    /** Adds all cached recipes to the given JEI runtime. */
    private static void addAllRecipesToRuntime(IJeiRuntime runtime) {
        runtime.getRecipeManager().addRecipes(ExtractorRecipeCategory.EXTRACTOR_RECIPE_RECIPE_TYPE, RecipeCache.getExtractorRecipes());
        runtime.getRecipeManager().addRecipes(GeneratorRecipeCategory.GENERATOR_RECIPE_TYPE, RecipeCache.getGeneratorRecipes());
        runtime.getRecipeManager().addRecipes(LiquifierRecipeCategory.LIQUIFIER_RECIPE_TYPE, RecipeCache.getLiquifierRecipes());
        runtime.getRecipeManager().addRecipes(GrinderRecipeCategory.GRINDER_RECIPE_TYPE, RecipeCache.getGrinderRecipes());
        runtime.getRecipeManager().addRecipes(DryingRecipeCategory.DRYING_RECIPE_TYPE, RecipeCache.getDryingRecipes());
        runtime.getRecipeManager().addRecipes(MutatorRecipeCategory.MUTATOR_RECIPE_TYPE, RecipeCache.getMutatorRecipes());
        runtime.getRecipeManager().addRecipes(SequencerRecipeCategory.SEQUENCER_RECIPE_TYPE, RecipeCache.getSequencerRecipes());
        var synthDisplays = RecipeCache.getSynthesizerRecipes().stream()
                .flatMap(r -> SynthesizerRecipeCategory.buildValidStrainDisplays(r, RecipeCache.getSequencerRecipes()).stream())
                .toList();
        runtime.getRecipeManager().addRecipes(SynthesizerRecipeCategory.SYNTHESIZER_RECIPE_TYPE, synthDisplays);
        runtime.getRecipeManager().addRecipes(ManualGrinderRecipeCategory.RECIPE_TYPE, RecipeCache.getManualGrinderRecipes());
        runtime.getRecipeManager().addRecipes(JointRecipeCategory.JOINT_RECIPE_TYPE, RecipeCache.getJointRecipes());
        runtime.getRecipeManager().addRecipes(BluntRecipeCategory.BLUNT_RECIPE_TYPE, RecipeCache.getBluntRecipes());
        SmokeleafIndustries.LOGGER.info("JEI: Recipes injected into runtime.");
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        SmokeleafIndustries.LOGGER.info("JEI: Registering recipe categories...");
        var guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new ExtractorRecipeCategory(guiHelper),
                new GeneratorRecipeCategory(guiHelper),
                new LiquifierRecipeCategory(guiHelper),
                new GrinderRecipeCategory(guiHelper),
                new DryingRecipeCategory(guiHelper),
                new MutatorRecipeCategory(guiHelper),
                new SequencerRecipeCategory(guiHelper),
                new SynthesizerRecipeCategory(guiHelper),
                new ManualGrinderRecipeCategory(guiHelper),
                new JointRecipeCategory(guiHelper),
                new BluntRecipeCategory(guiHelper)
        );
        SmokeleafIndustries.LOGGER.info("JEI: Registered 11 recipe categories");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // Recipes will be added later via onRuntimeAvailable after the server loads
        // This is necessary because recipes are only available after ServerStartingEvent
        SmokeleafIndustries.LOGGER.info("JEI: registerRecipes called (recipes will be added on runtime available)");
    }
    
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JEISmokeleafInudstriesPlugin.jeiRuntime = jeiRuntime;
        SmokeleafIndustries.LOGGER.info("JEI Runtime available - populating recipes...");

        // Single-player: RecipeCache was already populated by ServerStartingEvent on the
        // integrated server (same JVM). Dedicated server: RecipeCache is still empty here
        // because ServerStartingEvent only fired on the server JVM. Fall back to the
        // client-side recipe manager which has recipes synced from the server.
        if (!RecipeCache.isCachePopulated()) {
            SmokeleafIndustries.LOGGER.info("JEI: RecipeCache empty (dedicated server?) - populating from client recipe manager");
            populateFromClientAndRefresh();
        } else {
            addAllRecipesToRuntime(jeiRuntime);
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModItems.MANUAL_GRINDER.get()), ManualGrinderRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DRYING_RACK.get()), DryingRecipeCategory.DRYING_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DRYER.get()), DryingRecipeCategory.DRYING_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.EXTRACTOR.get()), ExtractorRecipeCategory.EXTRACTOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.GENERATOR.get()), GeneratorRecipeCategory.GENERATOR_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.LIQUIFIER.get()), LiquifierRecipeCategory.LIQUIFIER_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.GRINDER.get()), GrinderRecipeCategory.GRINDER_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.MUTATOR.get()), MutatorRecipeCategory.MUTATOR_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SEQUENCER.get()), SequencerRecipeCategory.SEQUENCER_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SYNTHESIZER.get()), SynthesizerRecipeCategory.SYNTHESIZER_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Items.CRAFTING_TABLE), JointRecipeCategory.JOINT_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(Items.CRAFTING_TABLE), BluntRecipeCategory.BLUNT_RECIPE_TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ExtractorScreen.class, 80, 30, 20, 30, ExtractorRecipeCategory.EXTRACTOR_RECIPE_RECIPE_TYPE);
        registration.addRecipeClickArea(GeneratorScreen.class, 80, 25, 20, 30, GeneratorRecipeCategory.GENERATOR_RECIPE_TYPE);
        registration.addRecipeClickArea(LiquifierScreen.class, 59, 35, 54, 16, LiquifierRecipeCategory.LIQUIFIER_RECIPE_TYPE);
        registration.addRecipeClickArea(GrinderScreen.class, 84, 30, 8, 26, GrinderRecipeCategory.GRINDER_RECIPE_TYPE);
        registration.addRecipeClickArea(MutatorScreen.class, 102, 37, 8, 18, MutatorRecipeCategory.MUTATOR_RECIPE_TYPE);
        registration.addRecipeClickArea(SequencerScreen.class, 62, 33, 37, 16, SequencerRecipeCategory.SEQUENCER_RECIPE_TYPE);
        registration.addRecipeClickArea(SynthesizerScreen.class, 130, 30, 8, 26, SynthesizerRecipeCategory.SYNTHESIZER_RECIPE_TYPE);
    }
}
