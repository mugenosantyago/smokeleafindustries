package net.micaxs.smokeleaf.compat.jade;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.custom.BaseWeedCropBlock;
import net.micaxs.smokeleaf.block.entity.BaseWeedCropBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public enum WeedCropDataProvider implements IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "weed_crop_data");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        BlockState state = accessor.getBlockState();
        BlockPos pos = accessor.getPosition();

        if (state.hasProperty(BaseWeedCropBlock.TOP) && state.getValue(BaseWeedCropBlock.TOP)) {
            pos = pos.below();
        }

        BlockEntity be = accessor.getLevel().getBlockEntity(pos);
        if (be instanceof BaseWeedCropBlockEntity crop) {
            tag.putInt("n", crop.getNitrogen());
            tag.putInt("p", crop.getPhosphorus());
            tag.putInt("k", crop.getPotassium());

            var target = crop.getOptimalNutrientsLevels();
            tag.putInt("tn", target.n);
            tag.putInt("tp", target.p);
            tag.putInt("tk", target.k);
        }
    }
}
