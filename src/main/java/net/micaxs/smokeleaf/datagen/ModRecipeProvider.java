package net.micaxs.smokeleaf.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

public class ModRecipeProvider extends RecipeProvider {
    
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }
    
    @Override
    protected void buildRecipes() {
        // Recipe generation - recipes are defined in JSON files in src/main/resources/data/smokeleafindustries/recipe/
        // This provider is enabled but empty - all recipes are hand-written JSON
        // TODO: Migrate hand-written recipes to data generation if desired
    }
}
