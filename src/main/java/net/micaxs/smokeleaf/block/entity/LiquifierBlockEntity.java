package net.micaxs.smokeleaf.block.entity;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.block.entity.energy.ModEnergyStorage;
import net.micaxs.smokeleaf.fluid.ModFluids;
import net.micaxs.smokeleaf.recipe.LiquifierRecipe;
import net.micaxs.smokeleaf.recipe.LiquifierRecipeInput;
import net.micaxs.smokeleaf.recipe.ModRecipes;
import net.micaxs.smokeleaf.screen.custom.LiquifierMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class LiquifierBlockEntity extends BlockEntity implements MenuProvider {

    private static final int INPUT_SLOT = 0;

    public final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot == INPUT_SLOT && isValidInput(stack);
        }
    };

    private boolean isValidInput(ItemStack stack) {
        if (stack.isEmpty()) return false;
        
        // Allow on client side - server will validate the recipe
        if (level == null || level.isClientSide()) return true;
        
        LiquifierRecipeInput input = new LiquifierRecipeInput(stack);
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            return serverLevel.getServer().getRecipeManager()
                    .getRecipeFor(ModRecipes.LIQUIFIER_TYPE.get(), input, level)
                    .isPresent();
        }
        return true;
    }

    public IItemHandler getItemHandler(@Nullable Direction direction) {
        return this.itemHandler;
    }

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(64000, 320) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 1);
        }
    };

    private final FluidTank FLUID_TANK = new FluidTank(8000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
            super.onContentsChanged();
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return true;
        }
    };

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 156;

    public LiquifierBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.LIQUIFIER_BE.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0;
                };
            }
            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                }
            }
            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public ItemStack getRenderStack() {
        return itemHandler.getStackInSlot(INPUT_SLOT);
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return this.ENERGY_STORAGE;
    }

    public FluidStack getFluid() {
        return FLUID_TANK.getFluid();
    }

    public IFluidHandler getTank(@Nullable Direction direction) {
        return FLUID_TANK;
    }

    private Optional<LiquifierRecipe> getCurrentRecipe() {
        if (level == null) return Optional.empty();
        ItemStack stack = itemHandler.getStackInSlot(INPUT_SLOT);
        if (stack.isEmpty()) return Optional.empty();
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            return serverLevel.getServer().getRecipeManager()
                .getRecipeFor(ModRecipes.LIQUIFIER_TYPE.get(), new LiquifierRecipeInput(stack), level)
                .map(RecipeHolder::value);
        }
        return Optional.empty();
    }

    private boolean hasSpaceFor(LiquifierRecipe recipe) {
        FluidStack out = recipe.output();
        if (out.isEmpty()) return false;
        if (FLUID_TANK.isEmpty()) return out.getAmount() <= FLUID_TANK.getCapacity();
        if (!FLUID_TANK.getFluid().is(out.getFluid())) return false;
        return FLUID_TANK.getFluidAmount() + out.getAmount() <= FLUID_TANK.getCapacity();
    }

    private void craftFluid(LiquifierRecipe recipe) {
        itemHandler.extractItem(INPUT_SLOT, 1, false);
        FLUID_TANK.fill(recipe.outputCopy(), IFluidHandler.FluidAction.EXECUTE);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        boolean hasEnergy = ENERGY_STORAGE.getEnergyStored() > 0;
        Optional<LiquifierRecipe> recipeOpt = getCurrentRecipe();

        // Debug logging - only log once per 5 seconds (every 100 ticks) to avoid spam
        if (level.getGameTime() % 100 == 0 && !itemHandler.getStackInSlot(INPUT_SLOT).isEmpty()) {
            ItemStack input = itemHandler.getStackInSlot(INPUT_SLOT);
            SmokeleafIndustries.LOGGER.info("[Liquifier] Input item: {} ({})", input.getItem(), input);
            SmokeleafIndustries.LOGGER.info("[Liquifier] Energy: {}/{}, hasEnergy: {}", ENERGY_STORAGE.getEnergyStored(), ENERGY_STORAGE.getMaxEnergyStored(), hasEnergy);
            SmokeleafIndustries.LOGGER.info("[Liquifier] Recipe found: {}", recipeOpt.isPresent() ? "YES" : "NONE");
        }

        if (hasEnergy && recipeOpt.isPresent() && hasSpaceFor(recipeOpt.get())) {
            progress++;
            ENERGY_STORAGE.extractEnergy(20, false);

            if (level.random.nextInt(2) == 0) {
                level.addParticle(ParticleTypes.SMOKE,
                        blockPos.getX() + 0.5,
                        blockPos.getY() + 1.0,
                        blockPos.getZ() + 0.5,
                        0.0, 0.0, 0.0);
            }

            if (progress >= maxProgress) {
                craftFluid(recipeOpt.get());
                progress = 0;
            }
            setChanged(level, blockPos, blockState);
        } else {
            if (progress != 0) {
                progress = 0;
                setChanged(level, blockPos, blockState);
            }
        }

        boolean shouldBePowered = (progress > 0) || (hasEnergy && recipeOpt.isPresent());
        if (getBlockState().getValue(BlockStateProperties.POWERED) != shouldBePowered) {
            level.setBlockAndUpdate(getBlockPos(),
                    getBlockState().setValue(BlockStateProperties.POWERED, shouldBePowered));
        }
    }

    // Existing fluid push (if still needed)
    private void pushFluidToAboveNeighbour() {
        FluidUtil.getFluidHandler(level, worldPosition.above(), null).ifPresent(handler ->
                FluidUtil.tryFluidTransfer(handler, this.FLUID_TANK, Integer.MAX_VALUE, true));
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Weed Liquifier");
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inv, Player player) {
        return new LiquifierMenu(i, inv, this, data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        inventory.setItem(0, itemHandler.getStackInSlot(0));
        Containers.dropContents(level, worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ValueOutput invOutput = output.child("liquifier.inventory");
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                invOutput.store("slot" + i, ItemStack.CODEC, stack);
            }
        }
        output.putInt("liquifier.progress", progress);
        output.putInt("liquifier.maxProgress", maxProgress);
        output.putInt("liquifier.energy", ENERGY_STORAGE.getEnergyStored());
        FluidStack fluid = FLUID_TANK.getFluid();
        if (!fluid.isEmpty()) {
            output.store("liquifier.fluid", FluidStack.CODEC, fluid);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ValueInput invInput = input.childOrEmpty("liquifier.inventory");
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack stack = invInput.read("slot" + i, ItemStack.CODEC).orElse(ItemStack.EMPTY);
            itemHandler.setStackInSlot(i, stack);
        }
        ENERGY_STORAGE.setEnergy(input.getIntOr("liquifier.energy", 0));
        progress = input.getIntOr("liquifier.progress", 0);
        maxProgress = input.getIntOr("liquifier.maxProgress", 78);
        FLUID_TANK.setFluid(input.read("liquifier.fluid", FluidStack.CODEC).orElse(FluidStack.EMPTY));
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider regs) {
        return saveWithoutMetadata(regs);
    }

    @Override
    public void handleUpdateTag(ValueInput input) {
        loadWithComponents(input);
    }
}