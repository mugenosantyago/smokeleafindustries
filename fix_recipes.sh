#!/bin/bash

# Fix all shaped recipe ingredients - remove the {"item": "..."} wrapper
# Old format: "X": {"item": "namespace:id"}
# New format: "X": "namespace:id"

echo "Fixing shaped recipe ingredient format..."
find src/generated/resources/data/smokeleafindustries/recipe -name "*.json" -type f | while read file; do
    # Check if it's a shaped recipe
    if grep -q '"type": "minecraft:crafting_shaped"' "$file"; then
        # Use sed to convert {"item": "X"} to just "X" in the key section
        # This is a bit tricky, so we'll use perl for better regex support
        perl -i -0777 -pe 's/"([A-Z#])": \{\s*"item": "([^"]+)"\s*\}/"$1": "$2"/g' "$file"
        echo "Fixed shaped: $(basename $file)"
    fi
done

echo ""
echo "Fixing shapeless recipe ingredient format..."
find src/generated/resources/data/smokeleafindustries/recipe -name "*.json" -type f | while read file; do
    # Check if it's a shapeless recipe with ingredients array
    if grep -q '"type": "minecraft:crafting_shapeless"' "$file"; then
        # Convert [{"item": "X"}] to ["X"]
        perl -i -0777 -pe 's/\{\s*"item": "([^"]+)"\s*\}/"$1"/g' "$file"
        echo "Fixed shapeless: $(basename $file)"
    fi
done

echo ""
echo "Recipe format update complete!"
