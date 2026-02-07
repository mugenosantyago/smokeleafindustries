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
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeAccess;
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
        SmokeleafIndustries.LOGGER.info("JEI: Registering recipes...");
        
        // JEI loads after the game is running, so we can access the connection's recipe manager
        var connection = Minecraft.getInstance().getConnection();
        if (connection == null) {
            SmokeleafIndustries.LOGGER.error("JEI: No connection available! Cannot load recipes.");
            return;
        }
        
        RecipeManager recipeManager = connection.recipeManager();
        var allRecipes = recipeManager.getRecipes();
        SmokeleafIndustries.LOGGER.info("JEI: Found {} total recipes from connection", allRecipes.size());
        
        // Extract recipes by type directly from client recipe manager
        var extractorRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.EXTRACTOR_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (ExtractorRecipe) r)
                .toList();
        registration.addRecipes(ExtractorRecipeCategory.EXTRACTOR_RECIPE_RECIPE_TYPE, extractorRecipes);
        SmokeleafIndustries.LOGGER.info("JEI: Added {} extractor recipes", extractorRecipes.size());
        
        var generatorRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.GENERATOR_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (GeneratorRecipe) r)
                .toList();
        registration.addRecipes(GeneratorRecipeCategory.GENERATOR_RECIPE_TYPE, generatorRecipes);
        SmokeleafIndustries.LOGGER.info("JEI: Added {} generator recipes", generatorRecipes.size());
        
        var liquifierRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.LIQUIFIER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (LiquifierRecipe) r)
                .toList();
        registration.addRecipes(LiquifierRecipeCategory.LIQUIFIER_RECIPE_TYPE, liquifierRecipes);
        SmokeleafIndustries.LOGGER.info("JEI: Added {} liquifier recipes", liquifierRecipes.size());
        
        var grinderRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.GRINDER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (GrinderRecipe) r)
                .toList();
        registration.addRecipes(GrinderRecipeCategory.GRINDER_RECIPE_TYPE, grinderRecipes);
        SmokeleafIndustries.LOGGER.info("JEI: Added {} grinder recipes", grinderRecipes.size());
        
        var dryingRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.DRYING_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (DryingRecipe) r)
                .toList();
        registration.addRecipes(DryingRecipeCategory.DRYING_RECIPE_TYPE, dryingRecipes);
        SmokeleafIndustries.LOGGER.info("JEI: Added {} drying recipes", dryingRecipes.size());
        
        var mutatorRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.MUTATOR_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (MutatorRecipe) r)
                .toList();
        registration.addRecipes(MutatorRecipeCategory.MUTATOR_RECIPE_TYPE, mutatorRecipes);
        SmokeleafIndustries.LOGGER.info("JEI: Added {} mutator recipes", mutatorRecipes.size());
        
        var sequencerRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.SEQUENCER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (SequencerRecipe) r)
                .toList();
        registration.addRecipes(SequencerRecipeCategory.SEQUENCER_RECIPE_TYPE, sequencerRecipes);
        SmokeleafIndustries.LOGGER.info("JEI: Added {} sequencer recipes", sequencerRecipes.size());
        
        var synthesizerRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.SYNTHESIZER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (SynthesizerRecipe) r)
                .toList();
        // Build synthesizer displays from recipes
        var synthDisplays = synthesizerRecipes.stream()
                .flatMap(r -> SynthesizerRecipeCategory.buildValidStrainDisplays(r, sequencerRecipes).stream())
                .toList();
        registration.addRecipes(SynthesizerRecipeCategory.SYNTHESIZER_RECIPE_TYPE, synthDisplays);
        SmokeleafIndustries.LOGGER.info("JEI: Added {} synthesizer displays", synthDisplays.size());
        
        var manualGrinderRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.MANUAL_GRINDER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (ManualGrinderRecipe) r)
                .toList();
        registration.addRecipes(ManualGrinderRecipeCategory.RECIPE_TYPE, manualGrinderRecipes);
        SmokeleafIndustries.LOGGER.info("JEI: Added {} manual grinder recipes", manualGrinderRecipes.size());
        
        var jointRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == RecipeType.CRAFTING)
                .map(RecipeHolder::value)
                .filter(r -> r.getSerializer() == ModRecipes.JOINT_SERIALIZER.get())
                .map(r -> (JointRecipe) r)
                .toList();
        registration.addRecipes(JointRecipeCategory.JOINT_RECIPE_TYPE, jointRecipes);
        SmokeleafIndustries.LOGGER.info("JEI: Added {} joint recipes", jointRecipes.size());
        
        var bluntRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == RecipeType.CRAFTING)
                .map(RecipeHolder::value)
                .filter(BluntRecipe.class::isInstance)
                .map(BluntRecipe.class::cast)
                .toList();
        registration.addRecipes(BluntRecipeCategory.BLUNT_RECIPE_TYPE, bluntRecipes);
        SmokeleafIndustries.LOGGER.info("JEI: Added {} blunt recipes", bluntRecipes.size());
        
        SmokeleafIndustries.LOGGER.info("JEI: Recipe registration complete!");
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
