// src/main/java/net/micaxs/smokeleaf/block/custom/GrowPotBlock.java
package net.micaxs.smokeleaf.block.custom;

import com.mojang.serialization.MapCodec;
import net.micaxs.smokeleaf.block.entity.GrowPotBlockEntity;
import net.micaxs.smokeleaf.block.entity.ModBlockEntities;
import net.micaxs.smokeleaf.item.custom.FertilizerItem;
import net.micaxs.smokeleaf.item.custom.PlantAnalyzerItem;
import net.micaxs.smokeleaf.utils.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GrowPotBlock extends BaseEntityBlock {
    public static final net.minecraft.world.level.block.state.properties.Property<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<GrowPotBlock> CODEC = simpleCodec(GrowPotBlock::new);

    public GrowPotBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(1.0D, 0.0D, 1.0D, 14.0D, 8.0D, 14.0D);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GrowPotBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null :
                createTickerHelper(type, ModBlockEntities.GROW_POT.get(), GrowPotBlockEntity::tick);
    }
    
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable net.minecraft.world.entity.LivingEntity placer, net.minecraft.world.item.ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        // Ensure block entity is created and synced when block is placed
        if (!level.isClientSide && level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof GrowPotBlockEntity pot) {
                pot.setChangedAndSync();
            }
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity be = level.getBlockEntity(pos);
        // Ensure block entity exists - create it if it doesn't
        if (be == null || !(be instanceof GrowPotBlockEntity)) {
            if (!level.isClientSide) {
                be = newBlockEntity(pos, state);
                if (be != null) {
                    level.setBlockEntity(be);
                }
            }
            if (!(be instanceof GrowPotBlockEntity pot)) return InteractionResult.PASS;
        }
        GrowPotBlockEntity pot = (GrowPotBlockEntity) be;

        boolean holdingBoneMeal = !stack.isEmpty() && stack.is(Items.BONE_MEAL);
        boolean holdingMagnifyingGlass = !stack.isEmpty() && stack.getItem() instanceof PlantAnalyzerItem;
        boolean holdingFertilizer = !stack.isEmpty() && stack.getItem() instanceof FertilizerItem;

        boolean emptyHand = stack.isEmpty();
        boolean sneaking = player.isShiftKeyDown();

        if (holdingMagnifyingGlass) {
            if (level.isClientSide) {
                openAnalyzerScreen(pos);
            }
            return InteractionResult.SUCCESS;
        }

        // Client side: allow animation for potential interactions, server will validate
        if (level.isClientSide) {
            // Check if it looks like a valid interaction
            // Always allow if it's a BlockItem when pot has no soil (most common case)
            boolean looksLikeSoilInsert = !pot.hasSoil() && stack.getItem() instanceof BlockItem;
            boolean looksLikeSeedPlant = pot.hasSoil() && !pot.hasCrop() && !stack.isEmpty();
            boolean looksLikeFertilizer = pot.hasCrop() && holdingFertilizer;
            boolean looksLikeBonemeal = pot.hasCrop() && holdingBoneMeal;
            boolean looksLikeHarvest = pot.canHarvest();
            boolean looksLikeRemove = sneaking && emptyHand && (pot.hasCrop() || pot.hasSoil());
            
            if (looksLikeSoilInsert || looksLikeSeedPlant || looksLikeFertilizer 
                    || looksLikeBonemeal || looksLikeHarvest || looksLikeRemove) {
                return InteractionResult.SUCCESS;
            }
            // Also allow if it's a BlockItem (might be soil) - server will validate
            if (!pot.hasSoil() && stack.getItem() instanceof BlockItem) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        // Server side: actual validation
        // Check if it's a valid soil block - use direct block comparison first (more reliable)
        boolean canInsertSoil = false;
        if (!pot.hasSoil() && stack.getItem() instanceof BlockItem bi) {
            Block block = bi.getBlock();
            // Direct block comparison (most reliable)
            if (block == Blocks.DIRT || block == Blocks.FARMLAND || block == Blocks.PODZOL) {
                canInsertSoil = true;
            } else {
                // Fallback: try tag check
                try {
                    canInsertSoil = block.builtInRegistryHolder().is(ModTags.POT_SOILS);
                } catch (Exception e) {
                    // Tag check failed, canInsertSoil remains false
                }
            }
        }

        boolean canPlantCrop = pot.hasSoil()
                && !pot.hasCrop()
                && stack.is(ModTags.WEED_SEEDS)
                && GrowPotBlockEntity.resolveCropBySeed(stack.getItem()) != null;

        boolean canFertilize = pot.hasCrop() && holdingFertilizer && !pot.canHarvest();
        boolean tryingFertilizeFullyGrown = pot.hasCrop() && holdingFertilizer && pot.canHarvest();
        boolean canBonemeal = pot.hasCrop() && holdingBoneMeal;
        boolean canHarvest = pot.canHarvest();

        if (sneaking && emptyHand && level instanceof ServerLevel serverLevel) {
            if (pot.hasCrop()) {
                if (pot.removeCropAndGiveSeed(serverLevel, player)) {
                    return InteractionResult.SUCCESS;
                }
            } else if (pot.hasSoil()) {
                if (pot.removeSoilAndGiveBack(serverLevel, player)) {
                    return InteractionResult.SUCCESS;
                }
            }
        }

        if (tryingFertilizeFullyGrown) {
            if (player != null) {
                player.displayClientMessage(Component.translatable("tooltip.smokeleafindustries.add_fertilizer"), true);
            }
            return InteractionResult.SUCCESS;
        }

        if (canInsertSoil) {
            Block soilBlock = ((BlockItem) stack.getItem()).getBlock();
            pot.setSoil(soilBlock.defaultBlockState());
            if (!player.isCreative()) stack.shrink(1);
            pot.setChangedAndSync();
            // Force immediate sync to client - ensure block entity data is sent
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendBlockUpdated(pos, state, state, 3);
            }
            return InteractionResult.SUCCESS;
        }

        if (canPlantCrop) {
            BaseWeedCropBlock crop = GrowPotBlockEntity.resolveCropBySeed(stack.getItem());
            if (crop != null) {
                pot.initFromCrop(crop);
                pot.plantCrop(crop);
                if (!player.isCreative()) stack.shrink(1);
                pot.setChangedAndSync();
                // Force immediate sync to client - ensure block entity data is sent
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendBlockUpdated(pos, state, state, 3);
                }
                return InteractionResult.SUCCESS;
            }
        }

        if (canFertilize && stack.getItem() instanceof FertilizerItem fert) {
            pot.addNitrogen(fert.getN());
            pot.addPhosphorus(fert.getP());
            pot.addPotassium(fert.getK());
            if (!player.isCreative()) stack.shrink(1);
            pot.setChangedAndSync();
            return InteractionResult.SUCCESS;
        }

        if (canBonemeal && pot.applyBonemeal(level)) {
            if (!player.isCreative()) stack.shrink(1);
            level.levelEvent(1505, pos, 0);
            pot.setChangedAndSync();
            return InteractionResult.SUCCESS;
        }

        if (canHarvest && level instanceof ServerLevel serverLevel) {
            pot.harvest(serverLevel);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }


    public static void openAnalyzerScreen(BlockPos pos) {
        // Use reflection to avoid loading client-side classes on server
        try {
            Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft");
            Object minecraft = minecraftClass.getMethod("getInstance").invoke(null);
            Class<?> screenClass = Class.forName("net.micaxs.smokeleaf.screen.custom.MagnifyingGlassScreen");
            Object screen = screenClass.getConstructor(BlockPos.class).newInstance(pos);
            minecraftClass.getMethod("setScreen", Class.forName("net.minecraft.client.gui.screens.Screen")).invoke(minecraft, screen);
        } catch (Exception e) {
            // Silently fail on server side
        }
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        return InteractionResult.PASS;
    }

    // @Override removed - base Block class appendHoverText signature doesn't match in 1.21.8
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        // super.appendHoverText removed - base Block class signature doesn't match in 1.21.8
        tooltipComponents.add(Component.literal("Instructions:").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("tooltip.smokeleafindustries.grow_pot.soil").withStyle(ChatFormatting.DARK_GRAY));
        tooltipComponents.add(Component.translatable("tooltip.smokeleafindustries.grow_pot.plant").withStyle(ChatFormatting.DARK_GRAY));
        tooltipComponents.add(Component.translatable("tooltip.smokeleafindustries.grow_pot.info").withStyle(ChatFormatting.DARK_GRAY));
    }
}
