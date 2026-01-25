package net.micaxs.smokeleaf.compat.jade;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.entity.GrowPotBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

/**
 * Server-side data provider for grow pots.
 * Separated from GrowPotProvider since Minecraft 1.21.6.
 */
public enum GrowPotDataProvider implements IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "grow_pot_data");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag tag, BlockAccessor accessor) {
        if (!(accessor.getBlockEntity() instanceof GrowPotBlockEntity pot)) return;

        // Current NPK
        tag.putInt("n", pot.getNitrogen());
        tag.putInt("p", pot.getPhosphorus());
        tag.putInt("k", pot.getPotassium());

        // Target/optimal NPK for color logic
        var target = pot.getOptimalNutrientsLevels();
        tag.putInt("tn", target.n);
        tag.putInt("tp", target.p);
        tag.putInt("tk", target.k);

        // Soil RL if present
        BlockState soil = pot.getSoilState();
        if (soil != null) {
            ResourceLocation soilId = BuiltInRegistries.BLOCK.getKey(soil.getBlock());
            if (soilId != null) tag.putString("soil", soilId.toString());
        }

        // Crop RL if present
        BlockState cropState = pot.getBottomCropStateForRender();
        if (cropState != null) {
            ResourceLocation cropId = BuiltInRegistries.BLOCK.getKey(cropState.getBlock());
            if (cropId != null) tag.putString("crop", cropId.toString());
        }

        // Growth data (age/maxAge)
        int age = pot.getCropAge();
        int maxAge = pot.getCropMaxAge();
        tag.putInt("age", age);
        tag.putInt("maxAge", maxAge);
    }
}
