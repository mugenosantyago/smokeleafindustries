package net.micaxs.smokeleaf.screen.renderer;

import com.google.common.base.Preconditions;
import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class FluidTankRenderer {
    private static final NumberFormat nf = NumberFormat.getIntegerInstance();
    private static final int TEXTURE_SIZE = 16;
    private static final int MIN_FLUID_HEIGHT = 1; // ensure tiny amounts of fluid are still visible

    private final long capacity;
    private final TooltipMode tooltipMode;
    private final int width;
    private final int height;

    enum TooltipMode {
        SHOW_AMOUNT,
        SHOW_AMOUNT_AND_CAPACITY,
        ITEM_LIST
    }

    public FluidTankRenderer(long capacity, boolean showCapacity, int width, int height) {
        this(capacity, showCapacity ? TooltipMode.SHOW_AMOUNT_AND_CAPACITY : TooltipMode.SHOW_AMOUNT, width, height);
    }

    private FluidTankRenderer(long capacity, TooltipMode tooltipMode, int width, int height) {
        Preconditions.checkArgument(capacity > 0, "capacity must be > 0");
        Preconditions.checkArgument(width > 0, "width must be > 0");
        Preconditions.checkArgument(height > 0, "height must be > 0");

        this.capacity = capacity;
        this.tooltipMode = tooltipMode;
        this.width = width;
        this.height = height;
    }

    public void render(GuiGraphics guiGraphics, int x, int y, FluidStack fluidStack) {
        // Render fluid directly without pose transformations - GuiGraphics handles positioning
        drawFluid(guiGraphics, x, y, width, height, fluidStack);
    }

    private void drawFluid(GuiGraphics guiGraphics, final int x, final int y, final int width, final int height, FluidStack fluidStack) {
        if (fluidStack == null || fluidStack.isEmpty()) {
            return;
        }
        
        Fluid fluid = fluidStack.getFluid();
        if (fluid == null || fluid.isSame(Fluids.EMPTY)) {
            return;
        }

        int fluidColor = getColorTint(fluidStack);

        long amount = fluidStack.getAmount();
        long scaledAmount = (amount * height) / capacity;

        if (amount > 0 && scaledAmount < MIN_FLUID_HEIGHT) {
            scaledAmount = MIN_FLUID_HEIGHT;
        }
        if (scaledAmount > height) {
            scaledAmount = height;
        }

        drawFluidRect(guiGraphics, x, y, width, height, fluidColor, scaledAmount);
    }

    private int getColorTint(FluidStack fluidStack) {
        try {
            Fluid fluid = fluidStack.getFluid();
            if (fluid == null) return 0xFFFFFFFF;
            IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
            if (renderProperties == null) return 0xFFFFFFFF;
            return renderProperties.getTintColor(fluidStack);
        } catch (Exception e) {
            // Fallback to white if there's any issue getting the color
            return 0xFFFFFFFF;
        }
    }

    private static void drawFluidRect(GuiGraphics guiGraphics, final int x, final int y, final int tiledWidth, final int tiledHeight, int color, long scaledAmount) {
        if (scaledAmount <= 0) return;
        
        // Extract color components for tinting
        int alpha = (color >> 24) & 0xFF;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        
        // Use alpha of 255 if the color has no alpha specified
        if (alpha <= 1) alpha = 255;

        // Calculate the position to draw the fluid (from bottom up)
        int yStart = y + tiledHeight;
        int drawY = yStart - (int)scaledAmount;
        
        // Draw a single colored rect representing the fluid level
        int argbColor = (alpha << 24) | (red << 16) | (green << 8) | blue;
        guiGraphics.fill(x, drawY, x + tiledWidth, yStart, argbColor);
    }

    public List<Component> getTooltip(FluidStack fluidStack, TooltipFlag tooltipFlag) {
        List<Component> tooltip = new ArrayList<>();

        Fluid fluidType = fluidStack.getFluid();
        try {
            if (fluidType.isSame(Fluids.EMPTY)) {
                tooltip.add(Component.literal("Empty"));
                tooltip.add(Component.translatable("smokeleafindusties.tooltip.liquid.amount.with.capacity", 0, nf.format(capacity)).withStyle(ChatFormatting.GRAY));
                return tooltip;
            }

            Component displayName = fluidStack.getHoverName();
            tooltip.add(displayName);

            long amount = fluidStack.getAmount();
            long milliBuckets = (amount * 1000) / FluidType.BUCKET_VOLUME;

            if (tooltipMode == TooltipMode.SHOW_AMOUNT_AND_CAPACITY) {
                MutableComponent amountString = Component.translatable("smokeleafindusties.tooltip.liquid.amount.with.capacity", nf.format(milliBuckets), nf.format(capacity));
                tooltip.add(amountString.withStyle(ChatFormatting.GRAY));
            } else if (tooltipMode == TooltipMode.SHOW_AMOUNT) {
                MutableComponent amountString = Component.translatable("smokeleafindusties.tooltip.liquid.amount", nf.format(milliBuckets));
                tooltip.add(amountString.withStyle(ChatFormatting.GRAY));
            }
        } catch (RuntimeException e) {
            SmokeleafIndustries.LOGGER.error("Failed to get tooltip for fluid: " + e);
        }

        return tooltip;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}