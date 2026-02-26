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
        // This is called after the game is fully loaded and running
        // At this point, the integrated server has started and recipes are available
        JEISmokeleafInudstriesPlugin.jeiRuntime = jeiRuntime;
        SmokeleafIndustries.LOGGER.info("JEI Runtime available - adding recipes now...");
        
        // Add recipes using the runtime API
        jeiRuntime.getRecipeManager().addRecipes(ExtractorRecipeCategory.EXTRACTOR_RECIPE_RECIPE_TYPE, RecipeCache.getExtractorRecipes());
        SmokeleafIndustries.LOGGER.info("JEI: Added {} extractor recipes", RecipeCache.getExtractorRecipes().size());
        
        jeiRuntime.getRecipeManager().addRecipes(GeneratorRecipeCategory.GENERATOR_RECIPE_TYPE, RecipeCache.getGeneratorRecipes());
        SmokeleafIndustries.LOGGER.info("JEI: Added {} generator recipes", RecipeCache.getGeneratorRecipes().size());
        
        jeiRuntime.getRecipeManager().addRecipes(LiquifierRecipeCategory.LIQUIFIER_RECIPE_TYPE, RecipeCache.getLiquifierRecipes());
        SmokeleafIndustries.LOGGER.info("JEI: Added {} liquifier recipes", RecipeCache.getLiquifierRecipes().size());
        
        jeiRuntime.getRecipeManager().addRecipes(GrinderRecipeCategory.GRINDER_RECIPE_TYPE, RecipeCache.getGrinderRecipes());
        SmokeleafIndustries.LOGGER.info("JEI: Added {} grinder recipes", RecipeCache.getGrinderRecipes().size());
        
        jeiRuntime.getRecipeManager().addRecipes(DryingRecipeCategory.DRYING_RECIPE_TYPE, RecipeCache.getDryingRecipes());
        SmokeleafIndustries.LOGGER.info("JEI: Added {} drying recipes", RecipeCache.getDryingRecipes().size());
        
        jeiRuntime.getRecipeManager().addRecipes(MutatorRecipeCategory.MUTATOR_RECIPE_TYPE, RecipeCache.getMutatorRecipes());
        SmokeleafIndustries.LOGGER.info("JEI: Added {} mutator recipes", RecipeCache.getMutatorRecipes().size());
        
        jeiRuntime.getRecipeManager().addRecipes(SequencerRecipeCategory.SEQUENCER_RECIPE_TYPE, RecipeCache.getSequencerRecipes());
        SmokeleafIndustries.LOGGER.info("JEI: Added {} sequencer recipes", RecipeCache.getSequencerRecipes().size());
        
        // Build synthesizer displays from cached recipes
        var synthDisplays = RecipeCache.getSynthesizerRecipes().stream()
                .flatMap(r -> SynthesizerRecipeCategory.buildValidStrainDisplays(r, RecipeCache.getSequencerRecipes()).stream())
                .toList();
        jeiRuntime.getRecipeManager().addRecipes(SynthesizerRecipeCategory.SYNTHESIZER_RECIPE_TYPE, synthDisplays);
        SmokeleafIndustries.LOGGER.info("JEI: Added {} synthesizer displays", synthDisplays.size());
        
        jeiRuntime.getRecipeManager().addRecipes(ManualGrinderRecipeCategory.RECIPE_TYPE, RecipeCache.getManualGrinderRecipes());
        SmokeleafIndustries.LOGGER.info("JEI: Added {} manual grinder recipes", RecipeCache.getManualGrinderRecipes().size());
        
        jeiRuntime.getRecipeManager().addRecipes(JointRecipeCategory.JOINT_RECIPE_TYPE, RecipeCache.getJointRecipes());
        SmokeleafIndustries.LOGGER.info("JEI: Added {} joint recipes", RecipeCache.getJointRecipes().size());
        
        jeiRuntime.getRecipeManager().addRecipes(BluntRecipeCategory.BLUNT_RECIPE_TYPE, RecipeCache.getBluntRecipes());
        SmokeleafIndustries.LOGGER.info("JEI: Added {} blunt recipes", RecipeCache.getBluntRecipes().size());
        
        SmokeleafIndustries.LOGGER.info("JEI: All recipes added via runtime API!");
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
