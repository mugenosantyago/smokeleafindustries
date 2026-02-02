package net.micaxs.smokeleaf.compat;

import net.micaxs.smokeleaf.recipe.*;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Caches recipes server-side for JEI integration
 */
public class RecipeCache {
    private static List<ExtractorRecipe> extractorRecipes = new ArrayList<>();
    private static List<GeneratorRecipe> generatorRecipes = new ArrayList<>();
    private static List<LiquifierRecipe> liquifierRecipes = new ArrayList<>();
    private static List<GrinderRecipe> grinderRecipes = new ArrayList<>();
    private static List<DryingRecipe> dryingRecipes = new ArrayList<>();
    private static List<MutatorRecipe> mutatorRecipes = new ArrayList<>();
    private static List<SequencerRecipe> sequencerRecipes = new ArrayList<>();
    private static List<SynthesizerRecipe> synthesizerRecipes = new ArrayList<>();
    private static List<ManualGrinderRecipe> manualGrinderRecipes = new ArrayList<>();
    private static List<JointRecipe> jointRecipes = new ArrayList<>();
    private static List<BluntRecipe> bluntRecipes = new ArrayList<>();

    public static void cacheRecipes(RecipeManager recipeManager) {
        // Get all recipes and filter by type
        var allRecipes = recipeManager.getRecipes();
        
        extractorRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.EXTRACTOR_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (ExtractorRecipe) r)
                .toList();
        
        generatorRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.GENERATOR_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (GeneratorRecipe) r)
                .toList();
        
        liquifierRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.LIQUIFIER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (LiquifierRecipe) r)
                .toList();
        
        grinderRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.GRINDER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (GrinderRecipe) r)
                .toList();
        
        dryingRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.DRYING_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (DryingRecipe) r)
                .toList();
        
        mutatorRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.MUTATOR_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (MutatorRecipe) r)
                .toList();
        
        sequencerRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.SEQUENCER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (SequencerRecipe) r)
                .toList();
        
        synthesizerRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.SYNTHESIZER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (SynthesizerRecipe) r)
                .toList();
        
        manualGrinderRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.MANUAL_GRINDER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (ManualGrinderRecipe) r)
                .toList();
        
        jointRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == RecipeType.CRAFTING)
                .map(RecipeHolder::value)
                .filter(r -> r.getSerializer() == ModRecipes.JOINT_SERIALIZER.get())
                .map(r -> (JointRecipe) r)
                .toList();
        
        bluntRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == RecipeType.CRAFTING)
                .map(RecipeHolder::value)
                .filter(BluntRecipe.class::isInstance)
                .map(BluntRecipe.class::cast)
                .toList();
    }

    public static List<ExtractorRecipe> getExtractorRecipes() { return extractorRecipes; }
    public static List<GeneratorRecipe> getGeneratorRecipes() { return generatorRecipes; }
    public static List<LiquifierRecipe> getLiquifierRecipes() { return liquifierRecipes; }
    public static List<GrinderRecipe> getGrinderRecipes() { return grinderRecipes; }
    public static List<DryingRecipe> getDryingRecipes() { return dryingRecipes; }
    public static List<MutatorRecipe> getMutatorRecipes() { return mutatorRecipes; }
    public static List<SequencerRecipe> getSequencerRecipes() { return sequencerRecipes; }
    public static List<SynthesizerRecipe> getSynthesizerRecipes() { return synthesizerRecipes; }
    public static List<ManualGrinderRecipe> getManualGrinderRecipes() { return manualGrinderRecipes; }
    public static List<JointRecipe> getJointRecipes() { return jointRecipes; }
    public static List<BluntRecipe> getBluntRecipes() { return bluntRecipes; }
}
