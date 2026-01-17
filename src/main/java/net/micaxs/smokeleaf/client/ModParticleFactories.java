// Java
package net.micaxs.smokeleaf.client;

import net.micaxs.smokeleaf.client.particle.EchoLocationParticle;
import net.micaxs.smokeleaf.effect.ModParticles;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

/**
 * Particle factory registration - must be on MOD event bus.
 * Registered programmatically from ModClientEvents.register()
 */
public class ModParticleFactories {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModParticleFactories::registerFactories);
    }
    
    private static void registerFactories(RegisterParticleProvidersEvent evt) {
        evt.registerSpriteSet(ModParticles.ECHO_LOCATION_PARTICLE.get(), EchoLocationParticle.Provider::new);
        // Register missing particle providers to prevent crashes
        // Using FlameParticle.Provider for spark-like effects (compatible with SimpleParticleType)
        evt.registerSpriteSet(ModParticles.DRY_BUD_SPARK.get(), net.minecraft.client.particle.FlameParticle.Provider::new);
        // Using GlowParticle.GlowSquidProvider for xray effect (compatible with SimpleParticleType)
        evt.registerSpriteSet(ModParticles.XRAY_PARTICLE.get(), net.minecraft.client.particle.GlowParticle.GlowSquidProvider::new);
    }
}
