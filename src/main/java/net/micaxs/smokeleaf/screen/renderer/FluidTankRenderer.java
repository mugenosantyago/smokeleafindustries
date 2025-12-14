package net.micaxs.smokeleaf.screen.renderer;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Matrix4f;

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
        Fluid fluid = fluidStack.getFluid();
        if (fluid.isSame(Fluids.EMPTY)) {
            return;
        }

        TextureAtlasSprite fluidStillSprite = getStillFluidSprite(fluidStack);
        int fluidColor = getColorTint(fluidStack);

        long amount = fluidStack.getAmount();
        long scaledAmount = (amount * height) / capacity;

        if (amount > 0 && scaledAmount < MIN_FLUID_HEIGHT) {
            scaledAmount = MIN_FLUID_HEIGHT;
        }
        if (scaledAmount > height) {
            scaledAmount = height;
        }

        drawTiledSprite(guiGraphics, x, y, width, height, fluidColor, scaledAmount, fluidStillSprite);
    }

    private TextureAtlasSprite getStillFluidSprite(FluidStack fluidStack) {
        Fluid fluid = fluidStack.getFluid();
        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
        ResourceLocation fluidStill = renderProperties.getStillTexture(fluidStack);

        Minecraft minecraft = Minecraft.getInstance();
        return minecraft.getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(fluidStill);
    }

    private int getColorTint(FluidStack ingredient) {
        Fluid fluid = ingredient.getFluid();
        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
        return renderProperties.getTintColor(ingredient);
    }

    private static void drawTiledSprite(GuiGraphics guiGraphics, final int x, final int y, final int tiledWidth, final int tiledHeight, int color, long scaledAmount, TextureAtlasSprite sprite) {
        // Use GuiGraphics methods directly - manual matrix access changed in 1.21.8
        // Extract color components for tinting
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        float alpha = ((color >> 24) & 0xFF) / 255F;

        final int xTileCount = tiledWidth / TEXTURE_SIZE;
        final int xRemainder = tiledWidth - (xTileCount * TEXTURE_SIZE);
        final long yTileCount = scaledAmount / TEXTURE_SIZE;
        final long yRemainder = scaledAmount - (yTileCount * TEXTURE_SIZE);

        final int yStart = y + tiledHeight;

        for (int xTile = 0; xTile <= xTileCount; xTile++) {
            for (int yTile = 0; yTile <= yTileCount; yTile++) {
                int currentWidth = (xTile == xTileCount) ? xRemainder : TEXTURE_SIZE;
                long currentHeight = (yTile == yTileCount) ? yRemainder : TEXTURE_SIZE;
                int drawX = x + (xTile * TEXTURE_SIZE);
                int drawY = yStart - ((int)((yTile + 1) * TEXTURE_SIZE));

                if (currentWidth > 0 && currentHeight > 0) {
                    int maskTop = (int)(TEXTURE_SIZE - currentHeight);
                    int maskRight = TEXTURE_SIZE - currentWidth;
                    
                    // Use GuiGraphics.blit with color tinting - API changed in 1.21.8
                    // Calculate UV coordinates with masking
                    float u0 = sprite.getU0();
                    float u1 = sprite.getU1() - (maskRight / 16F * (sprite.getU1() - sprite.getU0()));
                    float v0 = sprite.getV0() + (maskTop / 16F * (sprite.getV1() - sprite.getV0()));
                    float v1 = sprite.getV1();
                    
                    // Render using GuiGraphics with color tint
                    guiGraphics.setColor(red, green, blue, alpha);
                    guiGraphics.blit(sprite.atlasLocation(), drawX, drawY + maskTop, 0, 
                            (int)(u0 * sprite.contents().width()), (int)(v0 * sprite.contents().height()),
                            currentWidth, (int)currentHeight, 
                            sprite.contents().width(), sprite.contents().height());
                    guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f); // Reset color
                }
            }
        }
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