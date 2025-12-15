package net.micaxs.smokeleaf.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

// TODO: Fix ModRecipeProvider for 1.21.8 - RecipeProvider API changed significantly
// Constructor signature changed, buildRecipes signature changed, and all helper methods have new signatures
// Entire class temporarily commented out to allow compilation
/*
public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }
    
    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // All recipe generation code temporarily removed
        // TODO: Rewrite all recipes for 1.21.8 API
    }
}
*/
