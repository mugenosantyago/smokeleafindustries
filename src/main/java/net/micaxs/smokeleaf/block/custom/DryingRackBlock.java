package net.micaxs.smokeleaf.block.custom;

import com.mojang.serialization.MapCodec;
import net.micaxs.smokeleaf.block.entity.DryingRackBlockEntity;
import net.micaxs.smokeleaf.block.entity.ModBlockEntities;
import net.micaxs.smokeleaf.component.ModDataComponentTypes;
import net.micaxs.smokeleaf.item.custom.BaseBudItem;
import net.micaxs.smokeleaf.recipe.DryingRecipe;
import net.micaxs.smokeleaf.recipe.DryingRecipeInput;
import net.micaxs.smokeleaf.recipe.ModRecipes;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
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

import java.util.Optional;

public class DryingRackBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    public static final MapCodec<DryingRackBlock> CODEC = simpleCodec(DryingRackBlock::new);
    public static final net.minecraft.world.level.block.state.properties.Property<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public DryingRackBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }



    // Right click -> Insert Item or Extract Item (if empty hand)
    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level,
                                              BlockPos pos, Player player, InteractionHand hand,
                                              BlockHitResult hitResult) {

        BlockEntity be = level.getBlockEntity(pos);
        // Ensure block entity exists - create it if it doesn't
        if (be == null || !(be instanceof DryingRackBlockEntity)) {
            if (!level.isClientSide) {
                be = newBlockEntity(pos, state);
                if (be != null) {
                    level.setBlockEntity(be);
                }
            }
            if (!(be instanceof DryingRackBlockEntity rack)) {
                return InteractionResult.PASS;
            }
        }
        DryingRackBlockEntity rack = (DryingRackBlockEntity) be;

        // If empty hand, try to extract all items from all layers
        if (stack.isEmpty()) {
            if (level.isClientSide) return InteractionResult.SUCCESS;
            
            // Try to extract from all layers, starting from the clicked layer
            int clickedLayer = rack.computeLayerFromHit(hitResult);
            boolean extracted = false;
            
            // First try the clicked layer
            if (clickedLayer >= 0) {
                ItemStack out = rack.removeLastInRow(clickedLayer);
                if (!out.isEmpty()) {
                    if (!player.addItem(out)) player.drop(out, false);
                    extracted = true;
                }
            }
            
            // Then try other layers if nothing was extracted
            if (!extracted) {
                for (int layer = 0; layer <= 2; layer++) {
                    if (layer == clickedLayer) continue; // Already tried
                    ItemStack out = rack.removeLastInRow(layer);
                    if (!out.isEmpty()) {
                        if (!player.addItem(out)) player.drop(out, false);
                        extracted = true;
                        break;
                    }
                }
            }
            
            return extracted ? InteractionResult.CONSUME : InteractionResult.PASS;
        }

        // Client side: allow animation if item looks like it could be dried
        // Server will do the actual validation
        if (level.isClientSide) {
            // Quick client-side check: if it's a bud and not dry, allow the animation
            if (stack.getItem() instanceof BaseBudItem) {
                Boolean dry = stack.get(ModDataComponentTypes.DRY);
                if (dry == null || !dry) {
                    // Looks like a fresh bud, allow animation - server will validate
                    return InteractionResult.SUCCESS;
                }
            }
            // For other items that might have drying recipes, allow animation - server will validate
            // (We can't check recipes on client, so we allow it and let server reject if needed)
            return InteractionResult.SUCCESS;
        }

        // Server side: Check if it's a fresh bud first (simplest check)
        if (stack.getItem() instanceof BaseBudItem) {
            Boolean dry = stack.get(ModDataComponentTypes.DRY);
            if (dry == null || !dry) {
                // Fresh bud - allow insertion, it will dry in-place
                if (rack.insertOne(stack)) {
                    if (!player.isCreative()) {
                        player.getInventory().setChanged();
                    }
                    return InteractionResult.CONSUME;
                }
            }
            // Already dry bud - reject
            return InteractionResult.PASS;
        }

        // For other items, try to find a drying recipe
        Optional<RecipeHolder<DryingRecipe>> recipeOpt = Optional.empty();
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            recipeOpt = serverLevel.getServer().getRecipeManager()
                    .getRecipeFor(ModRecipes.DRYING_TYPE.get(), new DryingRecipeInput(stack), level);
        }

        if (recipeOpt.isEmpty()) {
            // No recipe -> not insertable
            return InteractionResult.PASS;
        }

        var recipe = recipeOpt.get().value();

        // If recipe is a bud in-place drying recipe and bud already dry -> reject
        if (recipe.dryBud() && stack.getItem() instanceof BaseBudItem) {
            Boolean dry = stack.get(ModDataComponentTypes.DRY);
            if (dry != null && dry) {
                return InteractionResult.PASS;
            }
        }

        // Server side insertion
        if (rack.insertOne(stack)) {
            if (!player.isCreative()) {
                player.getInventory().setChanged();
            }
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    // Left click -> Extract Item
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        BlockEntity be = level.getBlockEntity(pos);
        // Ensure block entity exists
        if (be == null || !(be instanceof DryingRackBlockEntity)) {
            if (!level.isClientSide) {
                be = newBlockEntity(pos, state);
                if (be != null) {
                    level.setBlockEntity(be);
                }
            }
        if (!(be instanceof DryingRackBlockEntity rack)) return InteractionResult.PASS;
        }
        DryingRackBlockEntity rack = (DryingRackBlockEntity) be;

        // Try to extract from all layers, starting from the clicked layer
        int clickedLayer = rack.computeLayerFromHit(hit);
        boolean extracted = false;
        
        // First try the clicked layer
        if (clickedLayer >= 0) {
            ItemStack out = rack.removeLastInRow(clickedLayer);
            if (!out.isEmpty()) {
                if (!player.addItem(out)) player.drop(out, false);
                extracted = true;
            }
        }
        
        // Then try other layers if nothing was extracted
        if (!extracted) {
            for (int layer = 0; layer <= 2; layer++) {
                if (layer == clickedLayer) continue; // Already tried
                ItemStack out = rack.removeLastInRow(layer);
                if (!out.isEmpty()) {
                    if (!player.addItem(out)) player.drop(out, false);
                    extracted = true;
                    break;
                }
            }
        }
        
        return extracted ? InteractionResult.CONSUME : InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.DRYING_RACK_BE.get().create(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        } else {
            return createTickerHelper(blockEntityType, ModBlockEntities.DRYING_RACK_BE.get(), DryingRackBlockEntity::serverTick);
        }
    }


    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock() && !level.isClientSide) {
            // Get block entity before it's removed - must check before block is actually removed
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof DryingRackBlockEntity rack) {
                // Drop items before block entity is removed
                rack.dropContents();
            }
        }
        // Call super to ensure proper cleanup
        // super.onRemove removed - base Block method signature changed in 1.21.8
    }
    
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        // Ensure block entity exists and drops items when player breaks the block
        // This is called BEFORE the block is removed, so the block entity should still exist
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof DryingRackBlockEntity rack) {
                // Drop all items before the block is destroyed
                rack.dropContents();
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}