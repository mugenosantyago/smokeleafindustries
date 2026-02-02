package net.micaxs.smokeleaf.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
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
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

@JeiPlugin
public class JEISmokeleafInudstriesPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
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
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // Use cached recipes loaded from server
        // Note: Recipes are cached when the server starts in ServerEvents
        
        registration.addRecipes(ExtractorRecipeCategory.EXTRACTOR_RECIPE_RECIPE_TYPE, RecipeCache.getExtractorRecipes());
        registration.addRecipes(GeneratorRecipeCategory.GENERATOR_RECIPE_TYPE, RecipeCache.getGeneratorRecipes());
        registration.addRecipes(LiquifierRecipeCategory.LIQUIFIER_RECIPE_TYPE, RecipeCache.getLiquifierRecipes());
        registration.addRecipes(GrinderRecipeCategory.GRINDER_RECIPE_TYPE, RecipeCache.getGrinderRecipes());
        registration.addRecipes(DryingRecipeCategory.DRYING_RECIPE_TYPE, RecipeCache.getDryingRecipes());
        registration.addRecipes(MutatorRecipeCategory.MUTATOR_RECIPE_TYPE, RecipeCache.getMutatorRecipes());
        registration.addRecipes(SequencerRecipeCategory.SEQUENCER_RECIPE_TYPE, RecipeCache.getSequencerRecipes());
        
        // Build synthesizer displays from cached recipes
        var synthDisplays = RecipeCache.getSynthesizerRecipes().stream()
                .flatMap(r -> SynthesizerRecipeCategory.buildValidStrainDisplays(r, RecipeCache.getSequencerRecipes()).stream())
                .toList();
        registration.addRecipes(SynthesizerRecipeCategory.SYNTHESIZER_RECIPE_TYPE, synthDisplays);
        
        registration.addRecipes(ManualGrinderRecipeCategory.RECIPE_TYPE, RecipeCache.getManualGrinderRecipes());
        registration.addRecipes(JointRecipeCategory.JOINT_RECIPE_TYPE, RecipeCache.getJointRecipes());
        registration.addRecipes(BluntRecipeCategory.BLUNT_RECIPE_TYPE, RecipeCache.getBluntRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModItems.MANUAL_GRINDER.get()), ManualGrinderRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DRYING_RACK.get()), DryingRecipeCategory.DRYING_RECIPE_TYPE);
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
