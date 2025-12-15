package net.micaxs.smokeleaf.client.brainmelt;

import net.micaxs.smokeleaf.effect.ModEffects;
import net.micaxs.smokeleaf.effect.harmful.BrainMeltEffect;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

public final class BrainMeltInputHandler {

    private BrainMeltInputHandler() {}

    public static void onInputUpdate(MovementInputUpdateEvent event) {
        if (!(event.getEntity() instanceof LocalPlayer player)) return;

        MobEffectInstance inst = player.getEffect(ModEffects.BRAIN_MELT);
        if (inst != null) {
            // TODO: Fix MovementInputUpdateEvent API for 1.21.8 - input field access changed
            // You can use inst.getAmplifier() if you want to scale the effect with potency
            //
            // MovementInputUpdateEvent API - use getInput()
            // var input = event.getInput();
            //
            // // Invert movement axes
            // input.forwardImpulse = -input.forwardImpulse;
            // input.leftImpulse = -input.leftImpulse;
            //
            // // Swap jump and crouch
            // boolean jump = input.jumping;
            // boolean crouch = input.shiftKeyDown;
            // input.jumping = crouch;
            // input.shiftKeyDown = jump;
        }

    }
}