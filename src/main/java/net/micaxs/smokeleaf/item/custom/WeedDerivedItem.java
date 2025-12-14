package net.micaxs.smokeleaf.item.custom;

import net.micaxs.smokeleaf.component.ModDataComponentTypes;
import net.micaxs.smokeleaf.effect.ModEffects;
import net.micaxs.smokeleaf.utils.WeedEffectHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.List;

public class WeedDerivedItem extends Item {
    private final float effectDurationMultiplier;
    private final float stonedChance;
    // private final UseAnim useAnimation; // Temporarily commented out
    private final int useDuration;

    public WeedDerivedItem(Properties pProperties, float effectDurationMultiplier, float stonedChance, Object useAnimation) { // UseAnim temporarily replaced with Object
        this(pProperties, effectDurationMultiplier, stonedChance, useAnimation, 20);
    }

    public WeedDerivedItem(Properties pProperties, float effectDurationMultiplier, float stonedChance, Object useAnimation, int useDuration) { // UseAnim temporarily replaced with Object
        super(pProperties);
        this.effectDurationMultiplier = effectDurationMultiplier;
        this.stonedChance = stonedChance;
        // this.useAnimation = useAnimation; // Temporarily commented out
        this.useDuration = useDuration;
    }

    // @Override - temporarily removed to check base class signature
    // public UseAnim getUseAnimation(ItemStack stack) {
    //     return this.useAnimation;
    // }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return this.useDuration;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        if (usedHand == InteractionHand.MAIN_HAND) {
            ItemStack itemstack = player.getItemInHand(usedHand);
            player.startUsingItem(usedHand);
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack mainHandItem = livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        spawnSmokeParticles(level, livingEntity);

        // Resolve stoned effect holder (handle both: already a Holder or a raw effect)
        Holder<MobEffect> stonedHolder = resolveModEffectHolder(level);

        if (level.random.nextDouble() <= this.stonedChance) {
            int previousStonedDuration = 0;
            if (livingEntity.hasEffect(stonedHolder)) {
                previousStonedDuration = livingEntity.getEffect(stonedHolder).getDuration();
            }
            livingEntity.addEffect(new MobEffectInstance(stonedHolder, previousStonedDuration + 200, 1));
        }

        // Gotta rewrite this part to use our DURATION_EFFECT data thingy.
//        Integer duration = mainHandItem.get(ModDataComponentTypes.EFFECT_DURATION);
//        if (duration != null) {
//            BaseWeedItem activeWeedIngredient = WeedEffectHelper.getActiveWeedIngredient(mainHandItem);
//        }


        CustomData custom = mainHandItem.get(DataComponents.CUSTOM_DATA);
        if (custom != null && !custom.isEmpty()) {

            CompoundTag tag = custom.copyTag();
            BaseWeedItem activeWeedIngredient = WeedEffectHelper.getActiveWeedIngredient(mainHandItem);

            if (tag.contains("duration") && activeWeedIngredient != null) {
                int duration = tag.getInt("duration").orElse(0);

                MobEffect rawEffect = activeWeedIngredient.getEffect();
                Holder<MobEffect> effectHolder = mobEffectToHolder(rawEffect, level);

                int previousEffectDuration = 0;
                if (livingEntity.hasEffect(effectHolder)) {
                    previousEffectDuration = livingEntity.getEffect(effectHolder).getDuration();
                }

                livingEntity.addEffect(new MobEffectInstance(
                        effectHolder,
                        previousEffectDuration + duration,
                        activeWeedIngredient.getEffectAmplifier()
                ));
            }
            mainHandItem.shrink(1);
        }

        return super.finishUsingItem(stack, level, livingEntity);
    }


    private static Holder<MobEffect> resolveModEffectHolder(Level level) {
        Holder<?> h = ModEffects.STONED;
        if (h.value() instanceof MobEffect) {
            @SuppressWarnings("unchecked")
            Holder<MobEffect> cast = (Holder<MobEffect>) h;
            return cast;
        }
        MobEffect effect = ModEffects.STONED.value();
        return mobEffectToHolder(effect, level);
    }


    private static Holder<MobEffect> mobEffectToHolder(MobEffect effect, Level level) {
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            var lookup = serverLevel.registryAccess().lookup(net.minecraft.core.registries.Registries.MOB_EFFECT);
            if (lookup.isPresent()) {
                var key = lookup.get().getResourceKey(effect);
                if (key.isPresent()) {
                    return lookup.get().getOrThrow(key.get());
                }
            }
        }
        return Holder.direct(effect);
    }


    private void spawnSmokeParticles(Level level, LivingEntity entity) {
        for (int i = 0; i < 10; i++) {
            double xOffset = level.random.nextGaussian() * 0.02D;
            double yOffset = level.random.nextGaussian() * 0.02D;
            double zOffset = level.random.nextGaussian() * 0.02D;
            level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    entity.getX() + entity.getBbWidth() * (level.random.nextDouble() - 0.5D),
                    entity.getEyeY(),
                    entity.getZ() + entity.getBbWidth() * (level.random.nextDouble() - 0.5D),
                    xOffset, yOffset, zOffset);
        }
    }


    // @Override removed - base Item class appendHoverText signature doesn't match in 1.21.8
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        // super.appendHoverText removed - base Item class signature doesn't match in 1.21.8
        CustomData custom = stack.get(DataComponents.CUSTOM_DATA);
        if (custom != null && !custom.isEmpty()) {
            CompoundTag tag = custom.copyTag();
            if (!tag.contains("duration")) {
                return;
            }
            BaseWeedItem activeIngredient = WeedEffectHelper.getActiveWeedIngredient(stack);
            if (activeIngredient == null) {
                return;
            }
            tooltip.add(WeedEffectHelper.getEffectTooltip(activeIngredient.getEffect(),
                    tag.getInt("duration").orElse(0), !activeIngredient.isVariableDuration()));
        }
    }

    public float getEffectFactor() {
        return this.effectDurationMultiplier;
    }
}
