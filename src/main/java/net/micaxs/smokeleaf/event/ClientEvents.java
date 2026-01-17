package net.micaxs.smokeleaf.event;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.effect.ModEffects;
import net.micaxs.smokeleaf.effect.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.micaxs.smokeleaf.client.paranoia.HallucinationManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

import java.awt.*;
import java.util.*;
import java.util.WeakHashMap;

@EventBusSubscriber(modid = SmokeleafIndustries.MODID, value = Dist.CLIENT)
public class ClientEvents {


    // -------- Echo Location particles on sound --------
    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        LocalPlayer player = mc.player;
        if (level == null || player == null) return;
        if (!player.hasEffect(ModEffects.ECHO_LOCATION)) return;

        SoundInstance snd = event.getSound();
        if (snd == null) return;

        double sx, sy, sz;
        if (snd.isRelative()) {
            var cam = mc.gameRenderer.getMainCamera().getPosition();
            sx = cam.x + snd.getX();
            sy = cam.y + snd.getY();
            sz = cam.z + snd.getZ();
        } else {
            sx = snd.getX();
            sy = snd.getY();
            sz = snd.getZ();
        }
        if (!Double.isFinite(sx) || !Double.isFinite(sy) || !Double.isFinite(sz)) return;

        int count = 12;
        double base = 0.14 + (snd.getVolume() * 0.06);
        for (int i = 0; i < count; i++) {
            double ang = (Math.PI * 2.0) * i / count;
            double jitter = level.random.nextDouble() * 0.02;
            double vx = Math.cos(ang) * (base + jitter);
            double vz = Math.sin(ang) * (base + jitter);
            level.addParticle(ModParticles.ECHO_LOCATION_PARTICLE.get(), sx, sy, sz, vx, 0.0, vz);
        }
    }




    // -------- Friend or Foe spoofed name tags --------
    // TODO: Re-implement using concrete subclass of RenderNameTagEvent (e.g., RenderNameTagEvent.Entity)
    // The abstract RenderNameTagEvent cannot be subscribed to directly in NeoForge 1.21.8




    // -------- Linguist's High: client-side villager interact subtitle --------
    private static final Random RAND = new Random();

    @SubscribeEvent
    public static void onClientInteractEntity(PlayerInteractEvent.EntityInteract evt) {
        if (evt.getEntity().level().isClientSide
                && evt.getTarget() instanceof Villager
                && evt.getEntity().hasEffect(ModEffects.LINGUISTS_HIGH)) {
            showLinguistSubtitle();
        }
    }

    private static void showLinguistSubtitle() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || !mc.player.hasEffect(ModEffects.LINGUISTS_HIGH)) return;

        int variant = 1 + RAND.nextInt(11);
        var text = net.minecraft.network.chat.Component.translatable("linguist.smokeleafindustries.villager_text." + variant);
        mc.gui.setOverlayMessage(text, false);
    }




    // -------- Relaxed: camera sway --------
    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(ModEffects.RELAXED)) {
            float tick = (player.tickCount % 360);
            event.setYaw(event.getYaw() + (float) Math.sin(tick * 0.01) * 1.5F);
            event.setPitch(event.getPitch() + (float) Math.cos(tick * 0.01) * 1.5F);
        }
    }




    // -------- Zombified: render player as a zombie --------
    private static final Map<UUID, Zombie> CACHE = new WeakHashMap<>();

    @SubscribeEvent
    public static void onRenderLivingPre(RenderLivingEvent.Pre event) {
        // TODO: Fix RenderLivingEvent.Pre API for 1.21.8 - getEntity() method changed
        // LivingEntity le = event.getEntity();
        // if (!(le instanceof Player player)) return;
        // if (!player.hasEffect(ModEffects.ZOMBIFIED)) return;
        //
        // event.setCanceled(true);
        // Temporarily disabled - TODO: Fix RenderLivingEvent.Pre API for 1.21.8
        return;

        // float pt = event.getPartialTick();
        // PoseStack pose = event.getPoseStack();
        // MultiBufferSource buf = event.getMultiBufferSource();
        // int light = event.getPackedLight();
        //
        // Zombie fake = CACHE.computeIfAbsent(player.getUUID(), id -> new Zombie(player.level()));
        // fake.xo = player.xo;
        // fake.yo = player.yo;
        // fake.zo = player.zo;
        // fake.setPos(player.getX(), player.getY(), player.getZ());
        //
        // float bodyYaw = Mth.lerp(pt, player.yBodyRotO, player.yBodyRot);
        // float headYaw = Mth.lerp(pt, player.yHeadRotO, player.yHeadRot);
        // float entityYaw = bodyYaw;
        //
        // fake.yBodyRotO = bodyYaw;
        // fake.yBodyRot = bodyYaw;
        // fake.yHeadRotO = headYaw;
        // fake.yHeadRot = headYaw;
        // fake.yRotO = entityYaw;
        // fake.setYRot(entityYaw);
        //
        // float pitch = Mth.lerp(pt, player.xRotO, player.getXRot());
        // fake.xRotO = pitch;
        // fake.setXRot(pitch);
        //
        // fake.setAggressive(false);
        // fake.setNoAi(true);
        //
        // for (EquipmentSlot slot : EquipmentSlot.values()) {
        //     var stack = player.getItemBySlot(slot);
        //     fake.setItemSlot(slot, stack);
        // }
        //
        // // TODO: Fix LivingEntityRenderer type arguments for 1.21.8 - requires 3 type parameters
        // // Minecraft mc = Minecraft.getInstance();
        // // EntityRenderDispatcher disp = mc.getEntityRenderDispatcher();
        // // @SuppressWarnings("unchecked")
        // // LivingEntityRenderer<Zombie, ?, ?> renderer =
        // //         (LivingEntityRenderer<Zombie, ?, ?>) disp.getRenderer(fake);
        // // renderer.render(fake, entityYaw, pt, pose, buf, light);
    }

    // -------- Melted effect: screen wiggle --------
    private static final Set<ResourceLocation> WIGGLED = new HashSet<>();
    private record WiggleSpec(double sx, double ax, double axLvl, boolean ix,
                              double sy, double ay, double ayLvl, boolean iy) {}
    
    private static Map<ResourceLocation, WiggleSpec> WIGGLE_SPECS = null;
    
    private static Map<ResourceLocation, WiggleSpec> getWiggleSpecs() {
        if (WIGGLE_SPECS == null) {
            WIGGLE_SPECS = new HashMap<>();
            WIGGLE_SPECS.put(VanillaGuiLayers.HOTBAR,             new WiggleSpec(0.10, 3.0, 1.5, false, 0.18, 1.4, 0.6, false));
            WIGGLE_SPECS.put(VanillaGuiLayers.PLAYER_HEALTH,      new WiggleSpec(0.13, 3.0, 1.5, true,  0.22, 1.8, 0.7, false));
            WIGGLE_SPECS.put(VanillaGuiLayers.FOOD_LEVEL,         new WiggleSpec(0.17, 2.5, 1.2, false, 0.25, 1.2, 0.5, true));
            WIGGLE_SPECS.put(VanillaGuiLayers.CHAT,               new WiggleSpec(0.21, 4.0, 1.0, false, 0.07, 6.0, 1.5, false));
            WIGGLE_SPECS.put(VanillaGuiLayers.TAB_LIST,           new WiggleSpec(0.15, 5.0, 2.0, true,  0.11, 3.5, 1.2, true));
            WIGGLE_SPECS.put(VanillaGuiLayers.CROSSHAIR,          new WiggleSpec(0.11, 2.0, 1.0, false, 0.19, 2.2, 0.8, false));
            WIGGLE_SPECS.put(VanillaGuiLayers.EFFECTS,            new WiggleSpec(0.19, 2.0, 1.0, false, 0.16, 1.6, 0.6, false));
            WIGGLE_SPECS.put(VanillaGuiLayers.SELECTED_ITEM_NAME, new WiggleSpec(0.08, 2.0, 1.0, false, 0.24, 1.3, 0.5, true));
        }
        return WIGGLE_SPECS;
    }

    private static boolean hasMelted(Player p) {
        return p != null && p.getEffect(ModEffects.MELTED) != null;
    }

    private static float partial(RenderGuiLayerEvent event) {
        return event.getPartialTick().getGameTimeDeltaPartialTick(false);
    }

    private static double partial(ViewportEvent.ComputeFov event) {
        return event.getPartialTick();
    }

    @SubscribeEvent
    public static void onRenderGuiLayerPre(RenderGuiLayerEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || !hasMelted(player)) return;

        ResourceLocation id = event.getName();
        WiggleSpec spec = getWiggleSpecs().get(id);
        if (spec == null) return;

        MobEffectInstance melted = player.getEffect(ModEffects.MELTED);
        int ampLvl = melted != null ? melted.getAmplifier() : 0;

        double t = player.tickCount + partial(event);

        double phaseX = Math.sin(t * spec.sx());
        double phaseY = Math.sin(t * spec.sy() + Math.PI / 2.0);
        double xAmp = spec.ax() + ampLvl * spec.axLvl();
        double yAmp = spec.ay() + ampLvl * spec.ayLvl();

        double xOffset = phaseX * xAmp * (spec.ix() ? -1 : 1);
        double yOffset = phaseY * yAmp * (spec.iy() ? -1 : 1);

        // TODO: RenderGuiLayerEvent pose stack API changed in 1.21.8
        WIGGLED.add(id);
    }

    @SubscribeEvent
    public static void onRenderGuiLayerPost(RenderGuiLayerEvent.Post event) {
        if (!WIGGLED.remove(event.getName())) return;
        // TODO: RenderGuiLayerEvent pose stack API changed in 1.21.8
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        if (!player.hasEffect(ModEffects.RAINBOW)) return;

        GuiGraphics gg = event.getGuiGraphics();
        float t = (System.currentTimeMillis() % 5000L) / 5000F;
        int rgb = Color.HSBtoRGB(t, 1F, 1F);
        int color = (80 << 24) | (rgb & 0xFFFFFF);
        gg.fill(0, 0, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight(), color);
    }

    @SubscribeEvent
    public static void onComputeFov(ViewportEvent.ComputeFov event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        double p = partial(event);
        MobEffectInstance breathing = player.getEffect(ModEffects.BREATHING);
        MobEffectInstance bubbled = player.getEffect(ModEffects.BUBBLED);

        if (breathing != null) {
            float time = (float)(player.tickCount + p);
            int amp = breathing.getAmplifier();
            float amplitude = 0.015f + amp * 0.010f;
            float speed = 0.15f + amp * 0.05f;
            float wave = (float)Math.sin(time * speed) * amplitude;
            event.setFOV((float)(event.getFOV() * (1.0 + wave)));
        }

        if (bubbled != null) {
            int amp = bubbled.getAmplifier();
            double boost = -0.50 + amp * 0.12;
            double modified = event.getFOV() * (1.0 + boost);
            event.setFOV((float)Math.min(170.0, modified));
        }
    }

    // -------- Reset static state on logout to prevent second-launch crashes --------
    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        // Reset all static state that could hold stale references
        CACHE.clear();
        WIGGLED.clear();
        WIGGLE_SPECS = null;
        // Reset hallucination manager state
        HallucinationManager.reset();
    }
}
