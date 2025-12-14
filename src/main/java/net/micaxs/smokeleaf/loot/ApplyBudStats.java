package net.micaxs.smokeleaf.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.micaxs.smokeleaf.block.entity.BaseWeedCropBlockEntity;
import net.micaxs.smokeleaf.component.ModDataComponentTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public class ApplyBudStats extends LootItemConditionalFunction {

    public static final MapCodec<ApplyBudStats> CODEC = RecordCodecBuilder.mapCodec(instance ->
            LootItemConditionalFunction.commonFields(instance).apply(instance, ApplyBudStats::new)
    );

    protected ApplyBudStats(List<LootItemCondition> conditions) {
        super(conditions);
    }

    public static LootItemConditionalFunction.Builder<?> apply() {
        return simpleBuilder(ApplyBudStats::new);
    }

    @Override
    public LootItemFunctionType<ApplyBudStats> getType() {
        return ModLootItemFunctions.APPLY_BUD_STATS.get();
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext ctx) {
        // LootContext API changed in 1.21.8 - use getOptionalParam or check hasParam first
        if (!ctx.hasParam(LootContextParams.BLOCK_ENTITY)) {
            return stack;
        }
        BlockEntity be = ctx.getParam(LootContextParams.BLOCK_ENTITY);
        if (be == null) return stack;
        if (be instanceof BaseWeedCropBlockEntity crop) {
            int buds = Mth.clamp(crop.getBudCount(), 1, 3);
            stack.setCount(buds);
            stack.set(ModDataComponentTypes.THC.get(), crop.getThc());
            stack.set(ModDataComponentTypes.CBD.get(), crop.getCbd());
        }
        return stack;
    }
}
