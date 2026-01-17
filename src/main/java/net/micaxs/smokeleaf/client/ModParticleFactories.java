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
    }
}
