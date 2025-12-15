package net.micaxs.smokeleaf.item;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.ModBlocks;
import net.micaxs.smokeleaf.effect.ModEffects;
import net.micaxs.smokeleaf.item.custom.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SmokeleafIndustries.MODID);
    
    /**
     * Helper to create Item.Properties with the item ID set.
     * This is required in NeoForge 1.21.8 to avoid "Item id not set" errors.
     */
    public static Item.Properties itemProps(String name) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, name));
        return new Item.Properties().setId(itemKey);
    }

    // Tobacco
    public static final DeferredItem<Item> TOBACCO = ITEMS.register("tobacco",  () -> new Item(itemProps("tobacco")));
    public static final DeferredItem<Item>  TOBACCO_SEEDS = ITEMS.register("tobacco_seeds",
            () -> new BlockItem(ModBlocks.TOBACCO_CROP.get(), itemProps("tobacco_seeds")));
    public static final DeferredItem<Item> TOBACCO_LEAF = ITEMS.register("tobacco_leaves",  () -> new Item(itemProps("tobacco_leaves")));
    public static final DeferredItem<Item> DRIED_TOBACCO_LEAF = ITEMS.register("dried_tobacco_leaves",  () -> new Item(itemProps("dried_tobacco_leaves")));



    // Seeds
    public static final DeferredItem<Item>  HEMP_SEEDS = ITEMS.register("hemp_seeds",
            () -> new BlockItem(ModBlocks.HEMP_CROP.get(), itemProps("hemp_seeds")));
    public static final DeferredItem<Item>  WHITE_WIDOW_SEEDS = ITEMS.register("white_widow_seeds",
            () -> new BlockItem(ModBlocks.WHITE_WIDOW_CROP.get(), itemProps("white_widow_seeds")));
    public static final DeferredItem<Item>  BUBBLE_KUSH_SEEDS = ITEMS.register("bubble_kush_seeds",
            () -> new BlockItem(ModBlocks.BUBBLE_KUSH_CROP.get(), itemProps("bubble_kush_seeds")));
    public static final DeferredItem<Item> LEMON_HAZE_SEEDS = ITEMS.register("lemon_haze_seeds",
            () -> new BlockItem(ModBlocks.LEMON_HAZE_CROP.get(), itemProps("lemon_haze_seeds")));
    public static final DeferredItem<Item> SOUR_DIESEL_SEEDS = ITEMS.register("sour_diesel_seeds",
            () -> new BlockItem(ModBlocks.SOUR_DIESEL_CROP.get(), itemProps("sour_diesel_seeds")));
    public static final DeferredItem<Item> BLUE_ICE_SEEDS = ITEMS.register("blue_ice_seeds",
            () -> new BlockItem(ModBlocks.BLUE_ICE_CROP.get(), itemProps("blue_ice_seeds")));
    public static final DeferredItem<Item> BUBBLEGUM_SEEDS = ITEMS.register("bubblegum_seeds",
            () -> new BlockItem(ModBlocks.BUBBLEGUM_CROP.get(), itemProps("bubblegum_seeds")));
    public static final DeferredItem<Item> PURPLE_HAZE_SEEDS = ITEMS.register("purple_haze_seeds",
            () -> new BlockItem(ModBlocks.PURPLE_HAZE_CROP.get(), itemProps("purple_haze_seeds")));
    public static final DeferredItem<Item> OG_KUSH_SEEDS = ITEMS.register("og_kush_seeds",
            () -> new BlockItem(ModBlocks.OG_KUSH_CROP.get(), itemProps("og_kush_seeds")));
    public static final DeferredItem<Item> JACK_HERER_SEEDS = ITEMS.register("jack_herer_seeds",
            () -> new BlockItem(ModBlocks.JACK_HERER_CROP.get(), itemProps("jack_herer_seeds")));
    public static final DeferredItem<Item> GARY_PEYTON_SEEDS = ITEMS.register("gary_peyton_seeds",
            () -> new BlockItem(ModBlocks.GARY_PEYTON_CROP.get(), itemProps("gary_peyton_seeds")));
    public static final DeferredItem<Item> AMNESIA_HAZE_SEEDS = ITEMS.register("amnesia_haze_seeds",
            () -> new BlockItem(ModBlocks.AMNESIA_HAZE_CROP.get(), itemProps("amnesia_haze_seeds")));
    public static final DeferredItem<Item> AK47_SEEDS = ITEMS.register("ak47_seeds",
            () -> new BlockItem(ModBlocks.AK47_CROP.get(), itemProps("ak47_seeds")));
    public static final DeferredItem<Item> GHOST_TRAIN_SEEDS = ITEMS.register("ghost_train_seeds",
            () -> new BlockItem(ModBlocks.GHOST_TRAIN_CROP.get(), itemProps("ghost_train_seeds")));
    public static final DeferredItem<Item> GRAPE_APE_SEEDS = ITEMS.register("grape_ape_seeds",
            () -> new BlockItem(ModBlocks.GRAPE_APE_CROP.get(), itemProps("grape_ape_seeds")));
    public static final DeferredItem<Item> COTTON_CANDY_SEEDS = ITEMS.register("cotton_candy_seeds",
            () -> new BlockItem(ModBlocks.COTTON_CANDY_CROP.get(), itemProps("cotton_candy_seeds")));
    public static final DeferredItem<Item> BANANA_KUSH_SEEDS = ITEMS.register("banana_kush_seeds",
            () -> new BlockItem(ModBlocks.BANANA_KUSH_CROP.get(), itemProps("banana_kush_seeds")));
    public static final DeferredItem<Item> CARBON_FIBER_SEEDS = ITEMS.register("carbon_fiber_seeds",
            () -> new BlockItem(ModBlocks.CARBON_FIBER_CROP.get(), itemProps("carbon_fiber_seeds")));
    public static final DeferredItem<Item> BIRTHDAY_CAKE_SEEDS = ITEMS.register("birthday_cake_seeds",
            () -> new BlockItem(ModBlocks.BIRTHDAY_CAKE_CROP.get(), itemProps("birthday_cake_seeds")));
    public static final DeferredItem<Item> BLUE_COOKIES_SEEDS = ITEMS.register("blue_cookies_seeds",
            () -> new BlockItem(ModBlocks.BLUE_COOKIES_CROP.get(), itemProps("blue_cookies_seeds")));
    public static final DeferredItem<Item> AFGHANI_SEEDS = ITEMS.register("afghani_seeds",
            () -> new BlockItem(ModBlocks.AFGHANI_CROP.get(), itemProps("afghani_seeds")));
    public static final DeferredItem<Item> MOONBOW_SEEDS = ITEMS.register("moonbow_seeds",
            () -> new BlockItem(ModBlocks.MOONBOW_CROP.get(), itemProps("moonbow_seeds")));
    public static final DeferredItem<Item> LAVA_CAKE_SEEDS = ITEMS.register("lava_cake_seeds",
            () -> new BlockItem(ModBlocks.LAVA_CAKE_CROP.get(), itemProps("lava_cake_seeds")));
    public static final DeferredItem<Item> JELLY_RANCHER_SEEDS = ITEMS.register("jelly_rancher_seeds",
            () -> new BlockItem(ModBlocks.JELLY_RANCHER_CROP.get(), itemProps("jelly_rancher_seeds")));
    public static final DeferredItem<Item> STRAWBERRY_SHORTCAKE_SEEDS = ITEMS.register("strawberry_shortcake_seeds",
            () -> new BlockItem(ModBlocks.STRAWBERRY_SHORTCAKE_CROP.get(), itemProps("strawberry_shortcake_seeds")));
    public static final DeferredItem<Item> PINK_KUSH_SEEDS = ITEMS.register("pink_kush_seeds",
            () -> new BlockItem(ModBlocks.PINK_KUSH_CROP.get(), itemProps("pink_kush_seeds")));


    // Buds
    public static final DeferredItem<Item> WHITE_WIDOW_BUD = ITEMS.register("white_widow_bud",
            () -> new BaseBudItem(itemProps("white_widow_bud"), 0, 1200));
    public static final DeferredItem<Item> BUBBLE_KUSH_BUD = ITEMS.register("bubble_kush_bud",
            () -> new BaseBudItem(itemProps("bubble_kush_bud"), 0, 1000));
    public static final DeferredItem<Item> LEMON_HAZE_BUD = ITEMS.register("lemon_haze_bud",
            () -> new BaseBudItem(itemProps("lemon_haze_bud"), 0, 800));
    public static final DeferredItem<Item> SOUR_DIESEL_BUD = ITEMS.register("sour_diesel_bud",
            () -> new BaseBudItem(itemProps("sour_diesel_bud"), 0, 900));
    public static final DeferredItem<Item> BLUE_ICE_BUD = ITEMS.register("blue_ice_bud",
            () -> new BaseBudItem(itemProps("blue_ice_bud"), 0, 1100));
    public static final DeferredItem<Item> BUBBLEGUM_BUD = ITEMS.register("bubblegum_bud",
            () -> new BaseBudItem(itemProps("bubblegum_bud"), 0, 700));
    public static final DeferredItem<Item> PURPLE_HAZE_BUD = ITEMS.register("purple_haze_bud",
            () -> new BaseBudItem(itemProps("purple_haze_bud"), 0, 600));
    public static final DeferredItem<Item> OG_KUSH_BUD = ITEMS.register("og_kush_bud",
            () -> new BaseBudItem(itemProps("og_kush_bud"), 0, 1300));
    public static final DeferredItem<Item> JACK_HERER_BUD = ITEMS.register("jack_herer_bud",
            () -> new BaseBudItem(itemProps("jack_herer_bud"), 0, 1250));
    public static final DeferredItem<Item> GARY_PEYTON_BUD = ITEMS.register("gary_peyton_bud",
            () -> new BaseBudItem(itemProps("gary_peyton_bud"), 0, 1150));
    public static final DeferredItem<Item> AMNESIA_HAZE_BUD = ITEMS.register("amnesia_haze_bud",
            () -> new BaseBudItem(itemProps("amnesia_haze_bud"), 0, 1050));
    public static final DeferredItem<Item> AK47_BUD = ITEMS.register("ak47_bud",
            () -> new BaseBudItem(itemProps("ak47_bud"), 0, 1100));
    public static final DeferredItem<Item> GHOST_TRAIN_BUD = ITEMS.register("ghost_train_bud",
            () -> new BaseBudItem(itemProps("ghost_train_bud"), 0, 1400));
    public static final DeferredItem<Item> GRAPE_APE_BUD = ITEMS.register("grape_ape_bud",
            () -> new BaseBudItem(itemProps("grape_ape_bud"), 0, 950));
    public static final DeferredItem<Item> COTTON_CANDY_BUD = ITEMS.register("cotton_candy_bud",
            () -> new BaseBudItem(itemProps("cotton_candy_bud"), 0, 850));
    public static final DeferredItem<Item> BANANA_KUSH_BUD = ITEMS.register("banana_kush_bud",
            () -> new BaseBudItem(itemProps("banana_kush_bud"), 0, 1200));
    public static final DeferredItem<Item> CARBON_FIBER_BUD = ITEMS.register("carbon_fiber_bud",
            () -> new BaseBudItem(itemProps("carbon_fiber_bud"), 0, 1500));
    public static final DeferredItem<Item> BIRTHDAY_CAKE_BUD = ITEMS.register("birthday_cake_bud",
            () -> new BaseBudItem(itemProps("birthday_cake_bud"), 0, 900));
    public static final DeferredItem<Item> BLUE_COOKIES_BUD = ITEMS.register("blue_cookies_bud",
            () -> new BaseBudItem(itemProps("blue_cookies_bud"), 0, 1000));
    public static final DeferredItem<Item> AFGHANI_BUD = ITEMS.register("afghani_bud",
            () -> new BaseBudItem(itemProps("afghani_bud"), 0, 1600));
    public static final DeferredItem<Item> MOONBOW_BUD = ITEMS.register("moonbow_bud",
            () -> new BaseBudItem(itemProps("moonbow_bud"), 0, 1350));
    public static final DeferredItem<Item> LAVA_CAKE_BUD = ITEMS.register("lava_cake_bud",
            () -> new BaseBudItem(itemProps("lava_cake_bud"), 0, 950));
    public static final DeferredItem<Item> JELLY_RANCHER_BUD = ITEMS.register("jelly_rancher_bud",
            () -> new BaseBudItem(itemProps("jelly_rancher_bud"), 0, 850));
    public static final DeferredItem<Item> STRAWBERRY_SHORTCAKE_BUD = ITEMS.register("strawberry_shortcake_bud",
            () -> new BaseBudItem(itemProps("strawberry_shortcake_bud"), 0, 800));
    public static final DeferredItem<Item> PINK_KUSH_BUD = ITEMS.register("pink_kush_bud",
            () -> new BaseBudItem(itemProps("pink_kush_bud"), 0, 1250));




    // Weeds
    public static final DeferredItem<Item> WHITE_WIDOW_WEED = ITEMS.register("white_widow_weed",
            () -> new BaseWeedItem(itemProps("white_widow_weed"), MobEffects.WIND_CHARGED.value(), 200, 1, 15, 10,
                    "White", "Widow"));
    public static final DeferredItem<Item> BUBBLE_KUSH_WEED = ITEMS.register("bubble_kush_weed",
            () -> new BaseWeedItem(itemProps("bubble_kush_weed"), MobEffects.STRENGTH.value(), 180, 1, 20, 5,
                    "Bubble", "Kush"));
    public static final DeferredItem<Item> LEMON_HAZE_WEED = ITEMS.register("lemon_haze_weed",
            () -> new BaseWeedItem(itemProps("lemon_haze_weed"), MobEffects.SPEED.value(), 160, 1, 19, 6,
                    "Lemon", "Haze"));
    public static final DeferredItem<Item> SOUR_DIESEL_WEED = ITEMS.register("sour_diesel_weed",
            () -> new BaseWeedItem(itemProps("sour_diesel_weed"), MobEffects.HASTE.value(), 170, 1, 19, 6,
                    "Sour", "Diesel"));
    public static final DeferredItem<Item> BLUE_ICE_WEED = ITEMS.register("blue_ice_weed",
            () -> new BaseWeedItem(itemProps("blue_ice_weed"), MobEffects.NIGHT_VISION.value(), 190, 1, 20, 5,
                    "Blue", "Ice"));
    public static final DeferredItem<Item> BUBBLEGUM_WEED = ITEMS.register("bubblegum_weed",
            () -> new BaseWeedItem(itemProps("bubblegum_weed"), MobEffects.HEALTH_BOOST.value(), 150, 1, 17, 8,
                    "Bubble", "Gum"));
    public static final DeferredItem<Item> PURPLE_HAZE_WEED = ITEMS.register("purple_haze_weed",
            () -> new BaseWeedItem(itemProps("purple_haze_weed"), MobEffects.LUCK.value(), 140, 1, 16, 9,
                    "Purple", "Haze"));
    public static final DeferredItem<Item> OG_KUSH_WEED = ITEMS.register("og_kush_weed",
            () -> new BaseWeedItem(itemProps("og_kush_weed"), MobEffects.RESISTANCE.value(), 210, 1, 25, 10,
                    "OG", "Kush"));
    public static final DeferredItem<Item> JACK_HERER_WEED = ITEMS.register("jack_herer_weed",
            () -> new BaseWeedItem(itemProps("jack_herer_weed"), ModEffects.R_TREES.value(), 205, 1, 18, 7,
                    "Jack", "Herer"));
    public static final DeferredItem<Item> GARY_PEYTON_WEED = ITEMS.register("gary_peyton_weed",
            () -> new BaseWeedItem(itemProps("gary_peyton_weed"), ModEffects.UPLIFTED.value(), 195, 1, 22, 3,
                    "Gary", "Peyton"));
    public static final DeferredItem<Item> AMNESIA_HAZE_WEED = ITEMS.register("amnesia_haze_weed",
            () -> new BaseWeedItem(itemProps("amnesia_haze_weed"), ModEffects.ZOMBIFIED.value(), 185, 1, 19, 6,
                    "Amnesia", "Haze"));
    public static final DeferredItem<Item> AK47_WEED = ITEMS.register("ak47_weed",
            () -> new BaseWeedItem(itemProps("ak47_weed"), ModEffects.RELAXED.value(), 190, 1, 19, 6,
                    "AK", "47"));
    public static final DeferredItem<Item> GHOST_TRAIN_WEED = ITEMS.register("ghost_train_weed",
            () -> new BaseWeedItem(itemProps("ghost_train_weed"), ModEffects.SHY.value(), 220, 1, 19, 6,
                    "Ghost", "Train"));
    public static final DeferredItem<Item> GRAPE_APE_WEED = ITEMS.register("grape_ape_weed",
            () -> new BaseWeedItem(itemProps("grape_ape_weed"), ModEffects.AROUSED.value(), 175, 1, 18, 7,
                    "Grape", "Ape"));
    public static final DeferredItem<Item> COTTON_CANDY_WEED = ITEMS.register("cotton_candy_weed",
            () -> new BaseWeedItem(itemProps("cotton_candy_weed"), ModEffects.CHILLOUT.value(), 165, 1, 19, 6,
                    "Cotton", "Candy"));
    public static final DeferredItem<Item> BANANA_KUSH_WEED = ITEMS.register("banana_kush_weed",
            () -> new BaseWeedItem(itemProps("banana_kush_weed"), ModEffects.STICKY_ICKY.value(), 200, 1, 21, 4,
                    "Banana", "Kush"));
    public static final DeferredItem<Item> CARBON_FIBER_WEED = ITEMS.register("carbon_fiber_weed",
            () -> new BaseWeedItem(itemProps("carbon_fiber_weed"), ModEffects.VEIN_HIGH.value(), 230, 1, 24, 1,
                    "Carbon", "Fiber"));
    public static final DeferredItem<Item> BIRTHDAY_CAKE_WEED = ITEMS.register("birthday_cake_weed",
            () -> new BaseWeedItem(itemProps("birthday_cake_weed"), MobEffects.OOZING.value(), 170, 1, 23, 2,
                    "Birthday", "Cake"));
    public static final DeferredItem<Item> BLUE_COOKIES_WEED = ITEMS.register("blue_cookies_weed",
            () -> new BaseWeedItem(itemProps("blue_cookies_weed"), ModEffects.LINGUISTS_HIGH.value(), 180, 1, 17, 8,
                    "Blue", "Cookies"));
    public static final DeferredItem<Item> AFGHANI_WEED = ITEMS.register("afghani_weed",
            () -> new BaseWeedItem(itemProps("afghani_weed"), MobEffects.BAD_OMEN.value(), 240, 1, 18, 7,
                    "Afghani", "Kush"));
    public static final DeferredItem<Item> MOONBOW_WEED = ITEMS.register("moonbow_weed",
            () -> new BaseWeedItem(itemProps("moonbow_weed"), MobEffects.NIGHT_VISION.value(), 215, 1, 30, 13,
                    "Moonbow", "Kush"));
    public static final DeferredItem<Item> LAVA_CAKE_WEED = ITEMS.register("lava_cake_weed",
            () -> new BaseWeedItem(itemProps("lava_cake_weed"), MobEffects.GLOWING.value(), 175, 1, 22, 3,
                    "Lava", "Cake"));
    public static final DeferredItem<Item> JELLY_RANCHER_WEED = ITEMS.register("jelly_rancher_weed",
            () -> new BaseWeedItem(itemProps("jelly_rancher_weed"), MobEffects.DOLPHINS_GRACE.value(), 165, 1, 20, 5,
                    "Jelly", "Rancher"));
    public static final DeferredItem<Item> STRAWBERRY_SHORTCAKE_WEED = ITEMS.register("strawberry_shortcake_weed",
            () -> new BaseWeedItem(itemProps("strawberry_shortcake_weed"), ModEffects.HIGH_FLYER.value(), 160, 1, 16, 9,
                    "Strawberry", "Shortcake"));
    public static final DeferredItem<Item> PINK_KUSH_WEED = ITEMS.register("pink_kush_weed",
            () -> new BaseWeedItem(itemProps("pink_kush_weed"), MobEffects.REGENERATION.value(), 205, 1, 19, 6,
                    "Pink", "Kush"));


    // Extracts
    public static final DeferredItem<Item> BASE_EXTRACT = ITEMS.register("base_extract",  () -> new Item(itemProps("base_extract")));
    public static final DeferredItem<Item> WHITE_WIDOW_EXTRACT = ITEMS.register("white_widow_extract",
            () -> new BaseWeedItem(itemProps("white_widow_extract"), MobEffects.WIND_CHARGED.value(), 400, 2, 15, 10, false));
    public static final DeferredItem<Item> BUBBLE_KUSH_EXTRACT = ITEMS.register("bubble_kush_extract",
            () -> new BaseWeedItem(itemProps("bubble_kush_extract"), MobEffects.STRENGTH.value(), 360, 2, 20, 5, false));
    public static final DeferredItem<Item> LEMON_HAZE_EXTRACT = ITEMS.register("lemon_haze_extract",
            () -> new BaseWeedItem(itemProps("lemon_haze_extract"), MobEffects.SPEED.value(), 320, 2, 19, 6, false));
    public static final DeferredItem<Item> SOUR_DIESEL_EXTRACT = ITEMS.register("sour_diesel_extract",
            () -> new BaseWeedItem(itemProps("sour_diesel_extract"), MobEffects.HASTE.value(), 340, 2, 19, 6, false));
    public static final DeferredItem<Item> BLUE_ICE_EXTRACT = ITEMS.register("blue_ice_extract",
            () -> new BaseWeedItem(itemProps("blue_ice_extract"), MobEffects.NIGHT_VISION.value(), 380, 2, 20, 5, false));
    public static final DeferredItem<Item> BUBBLEGUM_EXTRACT = ITEMS.register("bubblegum_extract",
            () -> new BaseWeedItem(itemProps("bubblegum_extract"), MobEffects.HEALTH_BOOST.value(), 300, 2, 17, 8, false));
    public static final DeferredItem<Item> PURPLE_HAZE_EXTRACT = ITEMS.register("purple_haze_extract",
            () -> new BaseWeedItem(itemProps("purple_haze_extract"), MobEffects.LUCK.value(), 280, 2, 16, 9, false));
    public static final DeferredItem<Item> OG_KUSH_EXTRACT = ITEMS.register("og_kush_extract",
            () -> new BaseWeedItem(itemProps("og_kush_extract"), MobEffects.RESISTANCE.value(), 420, 2, 25, 0, false));
    public static final DeferredItem<Item> JACK_HERER_EXTRACT = ITEMS.register("jack_herer_extract",
            () -> new BaseWeedItem(itemProps("jack_herer_extract"), ModEffects.R_TREES.value(), 410, 2, 18, 7, false));
    public static final DeferredItem<Item> GARY_PEYTON_EXTRACT = ITEMS.register("gary_peyton_extract",
            () -> new BaseWeedItem(itemProps("gary_peyton_extract"), ModEffects.UPLIFTED.value(), 390, 2, 22, 3, false));
    public static final DeferredItem<Item> AMNESIA_HAZE_EXTRACT = ITEMS.register("amnesia_haze_extract",
            () -> new BaseWeedItem(itemProps("amnesia_haze_extract"), ModEffects.ZOMBIFIED.value(), 370, 2, 19, 6, false));
    public static final DeferredItem<Item> AK47_EXTRACT = ITEMS.register("ak47_extract",
            () -> new BaseWeedItem(itemProps("ak47_extract"), ModEffects.RELAXED.value(), 380, 2, 19, 6, false));
    public static final DeferredItem<Item> GHOST_TRAIN_EXTRACT = ITEMS.register("ghost_train_extract",
            () -> new BaseWeedItem(itemProps("ghost_train_extract"), ModEffects.SHY.value(), 440, 2, 19, 6, false));
    public static final DeferredItem<Item> GRAPE_APE_EXTRACT = ITEMS.register("grape_ape_extract",
            () -> new BaseWeedItem(itemProps("grape_ape_extract"), ModEffects.AROUSED.value(), 350, 2, 18, 7, false));
    public static final DeferredItem<Item> COTTON_CANDY_EXTRACT = ITEMS.register("cotton_candy_extract",
            () -> new BaseWeedItem(itemProps("cotton_candy_extract"), ModEffects.CHILLOUT.value(), 330, 2, 19, 6, false));
    public static final DeferredItem<Item> BANANA_KUSH_EXTRACT = ITEMS.register("banana_kush_extract",
            () -> new BaseWeedItem(itemProps("banana_kush_extract"), ModEffects.STICKY_ICKY.value(), 400, 2, 21, 4, false));
    public static final DeferredItem<Item> CARBON_FIBER_EXTRACT = ITEMS.register("carbon_fiber_extract",
            () -> new BaseWeedItem(itemProps("carbon_fiber_extract"), ModEffects.VEIN_HIGH.value(), 460, 2, 24, 1, false));
    public static final DeferredItem<Item> BIRTHDAY_CAKE_EXTRACT = ITEMS.register("birthday_cake_extract",
            () -> new BaseWeedItem(itemProps("birthday_cake_extract"), MobEffects.OOZING.value(), 340, 2, 23, 2, false));
    public static final DeferredItem<Item> BLUE_COOKIES_EXTRACT = ITEMS.register("blue_cookies_extract",
            () -> new BaseWeedItem(itemProps("blue_cookies_extract"), ModEffects.LINGUISTS_HIGH.value(), 360, 2, 17, 8, false));
    public static final DeferredItem<Item> AFGHANI_EXTRACT = ITEMS.register("afghani_extract",
            () -> new BaseWeedItem(itemProps("afghani_extract"), MobEffects.BAD_OMEN.value(), 480, 2, 18, 7, false));
    public static final DeferredItem<Item> MOONBOW_EXTRACT = ITEMS.register("moonbow_extract",
            () -> new BaseWeedItem(itemProps("moonbow_extract"), MobEffects.NIGHT_VISION.value(), 430, 2, 30, 13, false));
    public static final DeferredItem<Item> LAVA_CAKE_EXTRACT = ITEMS.register("lava_cake_extract",
            () -> new BaseWeedItem(itemProps("lava_cake_extract"), MobEffects.GLOWING.value(), 350, 2, 22, 3, false));
    public static final DeferredItem<Item> JELLY_RANCHER_EXTRACT = ITEMS.register("jelly_rancher_extract",
            () -> new BaseWeedItem(itemProps("jelly_rancher_extract"), MobEffects.DOLPHINS_GRACE.value(), 330, 2, 20, 5, false));
    public static final DeferredItem<Item> STRAWBERRY_SHORTCAKE_EXTRACT = ITEMS.register("strawberry_shortcake_extract",
            () -> new BaseWeedItem(itemProps("strawberry_shortcake_extract"), ModEffects.HIGH_FLYER.value(), 320, 2, 16, 9, false));
    public static final DeferredItem<Item> PINK_KUSH_EXTRACT = ITEMS.register("pink_kush_extract",
            () -> new BaseWeedItem(itemProps("pink_kush_extract"), MobEffects.REGENERATION.value(), 410, 2, 19, 6, false));


    // Bags
    public static final DeferredItem<Item> EMPTY_BAG = ITEMS.register("empty_bag",
            () -> new BaseBagItem(itemProps("empty_bag").stacksTo(64), "tooltip.smokeleafindustries.empty_bag"));
    public static final DeferredItem<Item> WHITE_WIDOW_BAG = ITEMS.register("white_widow_bag",
            () -> new BaseBagItem(itemProps("white_widow_bag").stacksTo(64), "tooltip.smokeleafindustries.white_widow_bag"));
    public static final DeferredItem<Item> BUBBLE_KUSH_BAG = ITEMS.register("bubble_kush_bag",
            () -> new BaseBagItem(itemProps("bubble_kush_bag").stacksTo(64), "tooltip.smokeleafindustries.bubble_kush_bag"));
    public static final DeferredItem<Item> LEMON_HAZE_BAG = ITEMS.register("lemon_haze_bag",
            () -> new BaseBagItem(itemProps("lemon_haze_bag").stacksTo(64), "tooltip.smokeleafindustries.lemon_haze_bag"));
    public static final DeferredItem<Item> SOUR_DIESEL_BAG = ITEMS.register("sour_diesel_bag",
            () -> new BaseBagItem(itemProps("sour_diesel_bag").stacksTo(64), "tooltip.smokeleafindustries.sour_diesel_bag"));
    public static final DeferredItem<Item> BLUE_ICE_BAG = ITEMS.register("blue_ice_bag",
            () -> new BaseBagItem(itemProps("blue_ice_bag").stacksTo(64), "tooltip.smokeleafindustries.blue_ice_bag"));
    public static final DeferredItem<Item> BUBBLEGUM_BAG = ITEMS.register("bubblegum_bag",
            () -> new BaseBagItem(itemProps("bubblegum_bag").stacksTo(64), "tooltip.smokeleafindustries.bubblegum_bag"));
    public static final DeferredItem<Item> PURPLE_HAZE_BAG = ITEMS.register("purple_haze_bag",
            () -> new BaseBagItem(itemProps("purple_haze_bag").stacksTo(64), "tooltip.smokeleafindustries.purple_haze_bag"));
    public static final DeferredItem<Item> OG_KUSH_BAG = ITEMS.register("og_kush_bag",
            () -> new BaseBagItem(itemProps("og_kush_bag").stacksTo(64), "tooltip.smokeleafindustries.og_kush_bag"));
    public static final DeferredItem<Item> JACK_HERER_BAG = ITEMS.register("jack_herer_bag",
            () -> new BaseBagItem(itemProps("jack_herer_bag").stacksTo(64), "tooltip.smokeleafindustries.jack_herer_bag"));
    public static final DeferredItem<Item> GARY_PEYTON_BAG = ITEMS.register("gary_peyton_bag",
            () -> new BaseBagItem(itemProps("gary_peyton_bag").stacksTo(64), "tooltip.smokeleafindustries.gary_peyton_bag"));
    public static final DeferredItem<Item> AMNESIA_HAZE_BAG = ITEMS.register("amnesia_haze_bag",
            () -> new BaseBagItem(itemProps("amnesia_haze_bag").stacksTo(64), "tooltip.smokeleafindustries.amnesia_haze_bag"));
    public static final DeferredItem<Item> AK47_BAG = ITEMS.register("ak47_bag",
            () -> new BaseBagItem(itemProps("ak47_bag").stacksTo(64), "tooltip.smokeleafindustries.ak47_bag"));
    public static final DeferredItem<Item> GHOST_TRAIN_BAG = ITEMS.register("ghost_train_bag",
            () -> new BaseBagItem(itemProps("ghost_train_bag").stacksTo(64), "tooltip.smokeleafindustries.ghost_train_bag"));
    public static final DeferredItem<Item> GRAPE_APE_BAG = ITEMS.register("grape_ape_bag",
            () -> new BaseBagItem(itemProps("grape_ape_bag").stacksTo(64), "tooltip.smokeleafindustries.grape_ape_bag"));
    public static final DeferredItem<Item> COTTON_CANDY_BAG = ITEMS.register("cotton_candy_bag",
            () -> new BaseBagItem(itemProps("cotton_candy_bag").stacksTo(64), "tooltip.smokeleafindustries.cotton_candy_bag"));
    public static final DeferredItem<Item> BANANA_KUSH_BAG = ITEMS.register("banana_kush_bag",
            () -> new BaseBagItem(itemProps("banana_kush_bag").stacksTo(64), "tooltip.smokeleafindustries.banana_kush_bag"));
    public static final DeferredItem<Item> CARBON_FIBER_BAG = ITEMS.register("carbon_fiber_bag",
            () -> new BaseBagItem(itemProps("carbon_fiber_bag").stacksTo(64), "tooltip.smokeleafindustries.carbon_fiber_bag"));
    public static final DeferredItem<Item> BIRTHDAY_CAKE_BAG = ITEMS.register("birthday_cake_bag",
            () -> new BaseBagItem(itemProps("birthday_cake_bag").stacksTo(64), "tooltip.smokeleafindustries.birthday_cake_bag"));
    public static final DeferredItem<Item> BLUE_COOKIES_BAG = ITEMS.register("blue_cookies_bag",
            () -> new BaseBagItem(itemProps("blue_cookies_bag").stacksTo(64), "tooltip.smokeleafindustries.blue_cookies_bag"));
    public static final DeferredItem<Item> AFGHANI_BAG = ITEMS.register("afghani_bag",
            () -> new BaseBagItem(itemProps("afghani_bag").stacksTo(64), "tooltip.smokeleafindustries.afghani_bag"));
    public static final DeferredItem<Item> MOONBOW_BAG = ITEMS.register("moonbow_bag",
            () -> new BaseBagItem(itemProps("moonbow_bag").stacksTo(64), "tooltip.smokeleafindustries.moonbow_bag"));
    public static final DeferredItem<Item> LAVA_CAKE_BAG = ITEMS.register("lava_cake_bag",
            () -> new BaseBagItem(itemProps("lava_cake_bag").stacksTo(64), "tooltip.smokeleafindustries.lava_cake_bag"));
    public static final DeferredItem<Item> JELLY_RANCHER_BAG = ITEMS.register("jelly_rancher_bag",
            () -> new BaseBagItem(itemProps("jelly_rancher_bag").stacksTo(64), "tooltip.smokeleafindustries.jelly_rancher_bag"));
    public static final DeferredItem<Item> STRAWBERRY_SHORTCAKE_BAG = ITEMS.register("strawberry_shortcake_bag",
            () -> new BaseBagItem(itemProps("strawberry_shortcake_bag").stacksTo(64), "tooltip.smokeleafindustries.strawberry_shortcake_bag"));
    public static final DeferredItem<Item> PINK_KUSH_BAG = ITEMS.register("pink_kush_bag",
            () -> new BaseBagItem(itemProps("pink_kush_bag").stacksTo(64), "tooltip.smokeleafindustries.pink_kush_bag"));


    // Gummies
    public static final DeferredItem<Item> WHITE_WIDOW_GUMMY = ITEMS.register("white_widow_gummy",
            () -> new WeedDerivedItem(itemProps("white_widow_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> BUBBLE_KUSH_GUMMY = ITEMS.register("bubble_kush_gummy",
            () -> new WeedDerivedItem(itemProps("bubble_kush_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> LEMON_HAZE_GUMMY = ITEMS.register("lemon_haze_gummy",
            () -> new WeedDerivedItem(itemProps("lemon_haze_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> SOUR_DIESEL_GUMMY = ITEMS.register("sour_diesel_gummy",
            () -> new WeedDerivedItem(itemProps("sour_diesel_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> BLUE_ICE_GUMMY = ITEMS.register("blue_ice_gummy",
            () -> new WeedDerivedItem(itemProps("blue_ice_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> BUBBLEGUM_GUMMY = ITEMS.register("bubblegum_gummy",
            () -> new WeedDerivedItem(itemProps("bubblegum_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> PURPLE_HAZE_GUMMY = ITEMS.register("purple_haze_gummy",
            () -> new WeedDerivedItem(itemProps("purple_haze_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> OG_KUSH_GUMMY = ITEMS.register("og_kush_gummy",
            () -> new WeedDerivedItem(itemProps("og_kush_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> JACK_HERER_GUMMY = ITEMS.register("jack_herer_gummy",
            () -> new WeedDerivedItem(itemProps("jack_herer_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> GARY_PEYTON_GUMMY = ITEMS.register("gary_peyton_gummy",
            () -> new WeedDerivedItem(itemProps("gary_peyton_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> AMNESIA_HAZE_GUMMY = ITEMS.register("amnesia_haze_gummy",
            () -> new WeedDerivedItem(itemProps("amnesia_haze_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> AK47_GUMMY = ITEMS.register("ak47_gummy",
            () -> new WeedDerivedItem(itemProps("ak47_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> GHOST_TRAIN_GUMMY = ITEMS.register("ghost_train_gummy",
            () -> new WeedDerivedItem(itemProps("ghost_train_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> GRAPE_APE_GUMMY = ITEMS.register("grape_ape_gummy",
            () -> new WeedDerivedItem(itemProps("grape_ape_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> COTTON_CANDY_GUMMY = ITEMS.register("cotton_candy_gummy",
            () -> new WeedDerivedItem(itemProps("cotton_candy_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> BANANA_KUSH_GUMMY = ITEMS.register("banana_kush_gummy",
            () -> new WeedDerivedItem(itemProps("banana_kush_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> CARBON_FIBER_GUMMY = ITEMS.register("carbon_fiber_gummy",
            () -> new WeedDerivedItem(itemProps("carbon_fiber_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> BIRTHDAY_CAKE_GUMMY = ITEMS.register("birthday_cake_gummy",
            () -> new WeedDerivedItem(itemProps("birthday_cake_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> BLUE_COOKIES_GUMMY = ITEMS.register("blue_cookies_gummy",
            () -> new WeedDerivedItem(itemProps("blue_cookies_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> AFGHANI_GUMMY = ITEMS.register("afghani_gummy",
            () -> new WeedDerivedItem(itemProps("afghani_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> MOONBOW_GUMMY = ITEMS.register("moonbow_gummy",
            () -> new WeedDerivedItem(itemProps("moonbow_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> LAVA_CAKE_GUMMY = ITEMS.register("lava_cake_gummy",
            () -> new WeedDerivedItem(itemProps("lava_cake_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> JELLY_RANCHER_GUMMY = ITEMS.register("jelly_rancher_gummy",
            () -> new WeedDerivedItem(itemProps("jelly_rancher_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> STRAWBERRY_SHORTCAKE_GUMMY = ITEMS.register("strawberry_shortcake_gummy",
            () -> new WeedDerivedItem(itemProps("strawberry_shortcake_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));
    public static final DeferredItem<Item> PINK_KUSH_GUMMY = ITEMS.register("pink_kush_gummy",
            () -> new WeedDerivedItem(itemProps("pink_kush_gummy").food(ModFoods.WEED_GUMMY), 1.5f, 1f, null, 40));


    // --- FERTILIZER ITEMS ---
    public static final DeferredItem<Item> WORM_CASTINGS = ITEMS.register("worm_castings", () -> new FertilizerItem(2, 2, 2, itemProps("worm_castings")));
    public static final DeferredItem<Item> COMPOST = ITEMS.register("compost", () -> new FertilizerItem(1, 1, 1, itemProps("compost")));
    public static final DeferredItem<Item> MYCORRHIZAE = ITEMS.register("mycorrhizae", () -> new FertilizerItem(0, 3, 1, itemProps("mycorrhizae")));
    public static final DeferredItem<Item> DOLOMITE_LIME = ITEMS.register("dolomite_lime", () -> new FertilizerItem(-2, 4, -2, itemProps("dolomite_lime")));
    public static final DeferredItem<Item> BLOOD_MEAL = ITEMS.register("blood_meal", () -> new FertilizerItem(4, 0, 0, itemProps("blood_meal")));
    public static final DeferredItem<Item> PHOSPHORUS_POWDER = ITEMS.register("phosphorus_feed", () -> new FertilizerItem(0, 5, 0, itemProps("phosphorus_feed")));
    public static final DeferredItem<Item> BAT_GUANO = ITEMS.register("bat_guano", () -> new FertilizerItem(-1, 4, 1, itemProps("bat_guano")));
    public static final DeferredItem<Item> KELP_MEAL = ITEMS.register("kelp_meal", () -> new FertilizerItem(0, 0, 4, itemProps("kelp_meal")));
    public static final DeferredItem<Item> WOOD_ASH = ITEMS.register("wood_ash", () -> new FertilizerItem(-1, -1, 5, itemProps("wood_ash")));
    public static final DeferredItem<Item> BLOOM_BOOSTER = ITEMS.register("bloom_booster", () -> new FertilizerItem(-2, 4, 2, itemProps("bloom_booster")));
    public static final DeferredItem<Item> FRUIT_FINISHER = ITEMS.register("fruit_finisher", () -> new FertilizerItem(0, -2, -2, itemProps("fruit_finisher")));
    public static final DeferredItem<Item> NITROGEN_BOOST = ITEMS.register("nitrogen_boost", () -> new FertilizerItem(3, -1, 0, itemProps("nitrogen_boost")));
    public static final DeferredItem<Item> POTASH_BOOST = ITEMS.register("potash_boost", () -> new FertilizerItem(0, -1, 3, itemProps("potash_boost")));
    public static final DeferredItem<Item> BALANCED_BOOST = ITEMS.register("balanced_boost", () -> new FertilizerItem(1, 1, 1, itemProps("balanced_boost")));
    public static final DeferredItem<Item> PHOSPHORUS_REDUCER = ITEMS.register("phosphorus_reducer", () -> new FertilizerItem(0, -3, 0, itemProps("phosphorus_reducer")));
    public static final DeferredItem<Item> POTASSIUM_REDUCER = ITEMS.register("potassium_reducer", () -> new FertilizerItem(0, 0, -3, itemProps("potassium_reducer")));
    public static final DeferredItem<Item> FISH_EMULSION = ITEMS.register("fish_emulsion", () -> new FertilizerItem(2, 2, -1, itemProps("fish_emulsion")));


    // Consumables
    public static final DeferredItem<Item> BLUNT = ITEMS.register("blunt",
            () -> new BluntItem(itemProps("blunt").stacksTo(64)));
    public static final DeferredItem<Item> JOINT = ITEMS.register("joint",
            () -> new JointItem(itemProps("joint").stacksTo(64)));

    public static final DeferredItem<Item> HERB_CAKE = ITEMS.register("herb_cake",
            () -> new WeedDerivedItem(itemProps("herb_cake").food(ModFoods.HERB_CAKE), 1.5f, 1f, null, 50));
    public static final DeferredItem<Item> HASH_BROWNIE = ITEMS.register("hash_brownie",
            () -> new WeedDerivedItem(itemProps("hash_brownie").food(ModFoods.HASH_BROWNIE), 1.5f, 1f, null));
    public static final DeferredItem<Item> WEED_COOKIE = ITEMS.register("weed_cookie",
            () -> new WeedDerivedItem(itemProps("weed_cookie").food(ModFoods.WEED_COOKIE).stacksTo(16), 0.125f, 0.25f, null, 15));


    // Tinctures
    public static final DeferredItem<Item> EMPTY_TINCTURE = ITEMS.register("empty_tincture",
            () -> new EmptyTinctureItem(itemProps("empty_tincture").stacksTo(16).fireResistant()));
    public static final DeferredItem<Item> HASH_OIL_TINCTURE = ITEMS.register("hash_oil_tincture",
            () -> new HashOilTinctureItem(itemProps("hash_oil_tincture")
                    .craftRemainder(ModItems.EMPTY_TINCTURE.get()).stacksTo(1)
                    .durability(3).fireResistant()));


    // Other Items
    public static final DeferredItem<Item> HEMP_HAMMER = ITEMS.register("hemp_hammer", () -> new HempHammer(itemProps("hemp_hammer").stacksTo(1).durability(12)));

    public static final DeferredItem<Item> MANUAL_GRINDER = ITEMS.register("manual_grinder", () -> new ManualGrinderItem(itemProps("manual_grinder").stacksTo(1).fireResistant()));

    public static final DeferredItem<Item> BONG = ITEMS.register("bong", () -> new BongItem(itemProps("bong").stacksTo(1).fireResistant()));
    public static final DeferredItem<Item> DAB_RIG = ITEMS.register("dab_rig", () -> new DabRigItem(itemProps("dab_rig").stacksTo(1).fireResistant()));

    public static final DeferredItem<Item> HEMP_CORE = ITEMS.register("hemp_core",  () -> new Item(itemProps("hemp_core")));
    public static final DeferredItem<Item> UNFINISHED_HEMP_CORE = ITEMS.register("unfinished_hemp_core",  () -> new Item(itemProps("unfinished_hemp_core")));
    public static final DeferredItem<Item> HEMP_LEAF = ITEMS.register("hemp_leaf",  () -> new Item(itemProps("hemp_leaf")));
    public static final DeferredItem<Item> HEMP_FIBERS = ITEMS.register("hemp_fibers",  () -> new Item(itemProps("hemp_fibers")));
    public static final DeferredItem<Item> HEMP_FABRIC = ITEMS.register("hemp_fabric",  () -> new Item(itemProps("hemp_fabric")));
    public static final DeferredItem<Item> HEMP_STICK = ITEMS.register("hemp_stick",  () -> new Item(itemProps("hemp_stick")));

    public static final DeferredItem<Item> DNA_STRAND = ITEMS.register("dna_strand",  () -> new DNAStrandItem(itemProps("dna_strand")));

    public static final DeferredItem<Item> BIO_COMPOSITE = ITEMS.register("bio_composite",  () -> new Item(itemProps("bio_composite")));
    public static final DeferredItem<Item> HEMP_PLASTIC = ITEMS.register("hemp_plastic",  () -> new Item(itemProps("hemp_plastic")));


    public static final DeferredItem<Item> CAT_URINE_BOTTLE = ITEMS.register("cat_urine_bottle",  () -> new Item(itemProps("cat_urine_bottle")));
    public static final DeferredItem<Item> BUTTER = ITEMS.register("butter",  () -> new Item(itemProps("butter")));
    public static final DeferredItem<Item> INFUSED_BUTTER = ITEMS.register("infused_butter",  () -> new Item(itemProps("infused_butter")));
    public static final DeferredItem<Item> HEMP_COAL = ITEMS.register("hemp_coal",  () -> new Item(itemProps("hemp_coal")));

    public static final DeferredItem<Item> EMPTY_VIAL = ITEMS.register("empty_vial",  () -> new Item(itemProps("empty_vial")));



    public static final DeferredItem<Item> HPS_LAMP = ITEMS.register("hps_lamp",  () -> new BaseLampItem(itemProps("hps_lamp").stacksTo(1).durability(18000))); // 15mins
    public static final DeferredItem<Item> DUAL_ARC_LAMP = ITEMS.register("dual_arc_lamp",  () -> new BaseLampItem(itemProps("dual_arc_lamp").stacksTo(1).durability(54000))); // 45mins

    public static final DeferredItem<Item> PLANT_ANALYZER = ITEMS.register("plant_analyzer",  () -> new PlantAnalyzerItem(itemProps("plant_analyzer").stacksTo(1).durability(512)));



//    public static final DeferredItem<Item> SMOKELEAF_GUIDE = ITEMS.register("smokeleaf_guide", () -> new SmokeleafGuideItem(new Item.Properties().stacksTo(1)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
