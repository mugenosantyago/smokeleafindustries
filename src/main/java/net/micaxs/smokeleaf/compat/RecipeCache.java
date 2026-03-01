package net.micaxs.smokeleaf.compat;

import net.micaxs.smokeleaf.SmokeleafIndustries;
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
    private static volatile boolean cachePopulated = false;
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
    
    public static boolean isCachePopulated() {
        return cachePopulated;
    }

    /** Clears the cache so it will be re-populated on the next login. */
    public static void invalidate() {
        cachePopulated = false;
        extractorRecipes = new ArrayList<>();
        generatorRecipes = new ArrayList<>();
        liquifierRecipes = new ArrayList<>();
        grinderRecipes = new ArrayList<>();
        dryingRecipes = new ArrayList<>();
        mutatorRecipes = new ArrayList<>();
        sequencerRecipes = new ArrayList<>();
        synthesizerRecipes = new ArrayList<>();
        manualGrinderRecipes = new ArrayList<>();
        jointRecipes = new ArrayList<>();
        bluntRecipes = new ArrayList<>();
    }

    public static void cacheRecipes(RecipeManager recipeManager) {
        // Get all recipes and filter by type
        var allRecipes = recipeManager.getRecipes();
        
        SmokeleafIndustries.LOGGER.info("Caching recipes for JEI...");
        SmokeleafIndustries.LOGGER.info("Total recipes found: {}", allRecipes.size());
        
        extractorRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.EXTRACTOR_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (ExtractorRecipe) r)
                .toList();
        SmokeleafIndustries.LOGGER.info("Extractor recipes: {}", extractorRecipes.size());
        
        generatorRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.GENERATOR_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (GeneratorRecipe) r)
                .toList();
        SmokeleafIndustries.LOGGER.info("Generator recipes: {}", generatorRecipes.size());
        
        liquifierRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.LIQUIFIER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (LiquifierRecipe) r)
                .toList();
        SmokeleafIndustries.LOGGER.info("Liquifier recipes: {}", liquifierRecipes.size());
        
        grinderRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.GRINDER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (GrinderRecipe) r)
                .toList();
        SmokeleafIndustries.LOGGER.info("Grinder recipes: {}", grinderRecipes.size());
        
        dryingRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.DRYING_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (DryingRecipe) r)
                .toList();
        SmokeleafIndustries.LOGGER.info("Drying recipes: {}", dryingRecipes.size());
        
        mutatorRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.MUTATOR_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (MutatorRecipe) r)
                .toList();
        SmokeleafIndustries.LOGGER.info("Mutator recipes: {}", mutatorRecipes.size());
        
        sequencerRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.SEQUENCER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (SequencerRecipe) r)
                .toList();
        SmokeleafIndustries.LOGGER.info("Sequencer recipes: {}", sequencerRecipes.size());
        
        synthesizerRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.SYNTHESIZER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (SynthesizerRecipe) r)
                .toList();
        SmokeleafIndustries.LOGGER.info("Synthesizer recipes: {}", synthesizerRecipes.size());
        
        manualGrinderRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == ModRecipes.MANUAL_GRINDER_TYPE.get())
                .map(RecipeHolder::value)
                .map(r -> (ManualGrinderRecipe) r)
                .toList();
        SmokeleafIndustries.LOGGER.info("Manual Grinder recipes: {}", manualGrinderRecipes.size());
        
        jointRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == RecipeType.CRAFTING)
                .map(RecipeHolder::value)
                .filter(r -> r.getSerializer() == ModRecipes.JOINT_SERIALIZER.get())
                .map(r -> (JointRecipe) r)
                .toList();
        SmokeleafIndustries.LOGGER.info("Joint recipes: {}", jointRecipes.size());
        
        bluntRecipes = allRecipes.stream()
                .filter(r -> r.value().getType() == RecipeType.CRAFTING)
                .map(RecipeHolder::value)
                .filter(BluntRecipe.class::isInstance)
                .map(BluntRecipe.class::cast)
                .toList();
        SmokeleafIndustries.LOGGER.info("Blunt recipes: {}", bluntRecipes.size());
        
        cachePopulated = true;
        SmokeleafIndustries.LOGGER.info("Recipe caching complete! Cache is now available for JEI.");
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
