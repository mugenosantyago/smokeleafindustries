# JEI Debug Guide - Smokeleaf Industries

## Current Status

✅ **Build**: SUCCESS  
✅ **Logging**: Added to RecipeCache and JEI plugin  
📊 **Recipe Files Found**:
- Grinder: 27 recipes
- Extractor: 28 recipes  
- Manual Grinder: 28 recipes
- Mutator: 51 recipes
- Sequencer: 25 recipes
- Generator: 4 recipes
- Drying: 2 recipes
- Liquifier: 2 recipes
- Synthesizer: 1 recipe
- Joint: 1 recipe
- Blunt: 1 recipe

**Total Custom Recipes**: 170

---

## How to Test

### 1. Launch the Game

```bash
cd /Users/otoyume/Documents/GitHub/smokeleafindustries
./gradlew runClient
```

### 2. Check the Logs

While the game is starting, watch for these log messages:

#### On Server Start:
```
[Server thread/INFO] [smokeleafindustries]: Caching recipes for JEI...
[Server thread/INFO] [smokeleafindustries]: Total recipes found: XXX
[Server thread/INFO] [smokeleafindustries]: Extractor recipes: XX
[Server thread/INFO] [smokeleafindustries]: Generator recipes: X
[Server thread/INFO] [smokeleafindustries]: Liquifier recipes: X
[Server thread/INFO] [smokeleafindustries]: Grinder recipes: XX
[Server thread/INFO] [smokeleafindustries]: Drying recipes: X
[Server thread/INFO] [smokeleafindustries]: Mutator recipes: XX
[Server thread/INFO] [smokeleafindustries]: Sequencer recipes: XX
[Server thread/INFO] [smokeleafindustries]: Synthesizer recipes: X
[Server thread/INFO] [smokeleafindustries]: Manual Grinder recipes: XX
[Server thread/INFO] [smokeleafindustries]: Joint recipes: X
[Server thread/INFO] [smokeleafindustries]: Blunt recipes: X
[Server thread/INFO] [smokeleafindustries]: Recipe caching complete!
```

#### On JEI Load (Client Side):
```
[Render thread/INFO] [smokeleafindustries]: JEI: Registering recipe categories...
[Render thread/INFO] [smokeleafindustries]: JEI: Registered 11 recipe categories
[Render thread/INFO] [smokeleafindustries]: JEI: Registering recipes from cache...
[Render thread/INFO] [smokeleafindustries]: JEI: Added XX extractor recipes
[Render thread/INFO] [smokeleafindustries]: JEI: Added X generator recipes
[Render thread/INFO] [smokeleafindustries]: JEI: Added X liquifier recipes
... (and so on for each type)
[Render thread/INFO] [smokeleafindustries]: JEI: Recipe registration complete!
```

### 3. In-Game Testing

Once in-game:

1. **Open Creative Menu** (Press 'E')
2. **Find Smokeleaf Items Tabs** - Should see 2 tabs
3. **Open JEI** - Press 'E' to open inventory
4. **Search** - Type `@smokeleafindustries` in JEI search
5. **Click on Items**:
   - Click on a bud item (e.g., White Widow Bud)
   - Should show grinder recipes
   - Click on a weed item (e.g., White Widow Weed)
   - Should show extractor recipes
6. **Click on Machines**:
   - Click on Grinder block
   - Should show all grinder recipes
   - Click on Extractor block
   - Should show all extractor recipes

---

## What to Look For

### ✅ Good Signs

If you see these, things are working:
- Recipe caching logs show counts matching the files (27 grinder, 28 extractor, etc.)
- JEI logs show recipes being added
- Items appear in creative tabs
- JEI shows recipes when clicking items/machines

### ❌ Problem Signs

#### If Recipe Cache Shows 0 Recipes:
- **Problem**: Recipes aren't being loaded from JSON files
- **Check**: 
  - Are recipe JSON files in the right location?
  - Are recipe types registered properly?
  - Check for errors in logs about recipe loading

#### If JEI Shows 0 Recipes Added:
- **Problem**: Recipe cache is empty when JEI tries to read it
- **Possible Causes**:
  1. **Client-side issue**: RecipeCache runs on server, JEI runs on client
  2. **Timing issue**: JEI loading before server caches recipes
  3. **Integration issue**: JEI can't access the cache

#### If Some Recipes Missing:
- Check which types are missing in the logs
- Compare log counts to file counts above
- Specific recipe type might have loading issue

---

## Common Issues & Solutions

### Issue 1: "JEI shows 0 recipes for all types"

**Likely Cause**: RecipeCache is being called on server, but JEI on client can't access it.

**Solution**: In Minecraft, single-player creates an integrated server. The RecipeCache runs on that server thread, but JEI runs on the client thread. **This is actually a known issue with the current implementation!**

**Fix Required**: The RecipeCache needs to be synchronized to the client, OR JEI needs to access recipes directly from the RecipeManager on the client side.

Let me check if there's a client-side recipe manager access...

### Issue 2: "Some recipe types show, others don't"

**Likely Cause**: Recipe type mismatch or serializer issue.

**Check**: Look for errors in logs like:
```
Failed to parse recipe: ...
Unknown recipe type: ...
```

### Issue 3: "Recipes exist but don't show in JEI"

**Likely Cause**: Recipe category not properly registered or recipe format issue.

**Check**: 
- Verify JEI logs show "Registered 11 recipe categories"
- Check for JEI-specific errors

---

## Next Steps Based on Log Output

### If Logs Show:
- ✅ "Caching recipes..." with correct counts
- ❌ "JEI: Added 0 recipes" for all types

**Then**: The problem is the RecipeCache can't be accessed from client-side JEI. This is the **client-server synchronization issue**.

### Fix for Client-Server Issue:

The JEI plugin should access recipes directly from the client's RecipeManager, not from a server-side cache. Let me update the code...

---

## Log File Location

After running the game, check:
```
logs/latest.log
```

Or while game is running:
```
tail -f logs/latest.log | grep "smokeleafindustries"
```

---

## Expected vs Actual

Based on the recipe files, you should see:

| Recipe Type | Expected Count | What to Check |
|-------------|---------------|---------------|
| Grinder | 27 | Bud → Weed conversions |
| Extractor | 28 | Weed → Extract conversions |
| Manual Grinder | 28 | Right-click grinding |
| Mutator | 51 | Seed mutations |
| Sequencer | 25 | DNA processing |
| Generator | 4 | Item → Energy |
| Drying | 2 | Bud/Leaf drying |
| Liquifier | 2 | Item → Fluid |
| Synthesizer | 1 | DNA synthesis |
| Joint | 1 | Joint crafting |
| Blunt | 1 | Blunt crafting |

---

## Quick Test Command

To quickly see what's in the logs after running:

```bash
cd /Users/otoyume/Documents/GitHub/smokeleafindustries
./gradlew runClient > /dev/null 2>&1 &
sleep 30
tail -100 logs/latest.log | grep -E "(Caching recipes|JEI:)"
```

---

## Report Back

After testing, please report:
1. ✅ or ❌ for "Recipe caching" log messages
2. ✅ or ❌ for "JEI:" log messages  
3. Counts shown in logs for each recipe type
4. What you see (or don't see) in JEI in-game

This will help identify the exact issue!
