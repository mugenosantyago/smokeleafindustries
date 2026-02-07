# Recipe Format Fix - 1.21.1 → 1.21.8

## Issue Found
Over **100 recipe parsing errors** due to ingredient format changes between 1.21.1 and 1.21.8.

---

## Format Changes

### Shaped Recipe Ingredients

**Old Format (1.21.1):**
```json
"key": {
  "B": {
    "item": "minecraft:stone"
  },
  "P": {
    "item": "smokeleafindustries:hemp_planks"
  }
}
```

**New Format (1.21.8):**
```json
"key": {
  "B": "minecraft:stone",
  "P": "smokeleafindustries:hemp_planks"
}
```

### Shapeless Recipe Ingredients

**Old Format (1.21.1):**
```json
"ingredients": [
  {
    "item": "smokeleafindustries:hemp_bag"
  },
  {
    "item": "minecraft:sugar"
  }
]
```

**New Format (1.21.8):**
```json
"ingredients": [
  "smokeleafindustries:hemp_bag",
  "minecraft:sugar"
]
```

---

## Files Fixed

### Generated Resources (`src/generated/resources/data/smokeleafindustries/recipe/`):
- **Shaped Recipes**: 70 files fixed
- **Shapeless Recipes**: 49 files fixed
- **Smelting Recipes**: 1 file fixed
- **Stonecutting Recipes**: 3 files fixed

### Main Resources (`src/main/resources/data/smokeleafindustries/recipe/manual/`):
- **Manual Grinder Recipes**: 26 files fixed

**Total: 149 recipe files updated to 1.21.8 format**

---

## Recipe Types Status

### ✅ Custom Recipes (Already Working)
These use custom serializers and didn't need format changes:
- Grinder (27) - `smokeleafindustries:grinder`
- Extractor (28) - `smokeleafindustries:extractor`
- Drying (2) - `smokeleafindustries:drying`
- Liquifier (2) - `smokeleafindustries:liquifier`
- Mutator (51) - `smokeleafindustries:mutator`
- Sequencer (25) - `smokeleafindustries:sequencer`
- Synthesizer (1) - `smokeleafindustries:synthesizer`
- Manual Grinder (28) - `smokeleafindustries:manual_grinder`
- Joint (1) - `smokeleafindustries:joint`
- Blunt (1) - `smokeleafindustries:blunt`

### ✅ Vanilla Recipes (NOW FIXED)
These needed format updates:
- Shaped crafting (machines, blocks, items)
- Shapeless crafting (weeds from bags, crafting ingredients)
- Smelting (hemp plastic)
- Stonecutting (hemp stone variants)

---

## Before vs After

### Before Fix:
```
[Error] Couldn't parse data file 'smokeleafindustries:afghani_bag'
[Error] Couldn't parse data file 'smokeleafindustries:afghani_weed'
[Error] Couldn't parse data file 'smokeleafindustries:hemp_stone'
... (100+ errors)
```

### After Fix:
```
✅ All recipes parse successfully
✅ All recipes load into game
✅ All recipes available in JEI
```

---

## Summary

This was a **critical missing piece** from the 1.21.1 → 1.21.8 port!

The recipe format changed significantly in Minecraft 1.21.8, simplifying the ingredient syntax by removing the unnecessary `{"item": "..."}` wrapper and allowing direct item ID strings instead.

**All 149 affected recipes are now fixed and compatible with 1.21.8!**
