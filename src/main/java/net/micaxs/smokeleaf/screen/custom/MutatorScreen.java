package net.micaxs.smokeleaf.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.screen.renderer.EnergyDisplayTooltipArea;
import net.micaxs.smokeleaf.screen.renderer.FluidTankRenderer;
import net.micaxs.smokeleaf.utils.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;
import java.util.Optional;

public class MutatorScreen extends AbstractContainerScreen<MutatorMenu> {

    public static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "textures/gui/mutator/mutator_gui.png");
    private EnergyDisplayTooltipArea energyInfoArea;
    private FluidTankRenderer fluidRenderer;

    private static final ResourceLocation INFO_ICON = ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "textures/gui/icons/info.png");
    private static final int ICON_SIZE = 8;
    private static final float TOOLTIP_SCALE = 0.5f;
    private static final int IMAGE_WIDTH = 176;
    private static final int IMAGE_HEIGHT = 166;

    public MutatorScreen(MutatorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = IMAGE_WIDTH;
        this.imageHeight = IMAGE_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 100000;
        this.titleLabelY = 100000;
    }

    @Override
    public void resize(net.minecraft.client.Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        assignEnergyInfoArea();
        assignFluidrenderer();
    }

    private void assignFluidrenderer() {
        fluidRenderer = new FluidTankRenderer(8000, true, 16, 61);
    }

    private void renderFluidTooltipArea(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y, FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if (isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer)) {
            // renderTooltip API changed in 1.21.8 - temporarily disabled
            // TODO: Fix renderTooltip signature for 1.21.8
            // guiGraphics.renderTooltip( this.font, renderer.getTooltip(stack, TooltipFlag.Default.NORMAL),
            //         Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        // RenderSystem.setShader() and setShaderColor() API changed in 1.21.8
        // GuiGraphics handles shader setup automatically
        // RenderSystem.setShader(GameRenderer::getPositionTexShader);
        // RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        // RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        
        // Get screen dimensions - use Minecraft window if width/height aren't set yet
        int screenWidth = this.width > 0 ? this.width : (this.minecraft != null ? this.minecraft.getWindow().getGuiScaledWidth() : 320);
        int screenHeight = this.height > 0 ? this.height : (this.minecraft != null ? this.minecraft.getWindow().getGuiScaledHeight() : 240);
        
        // Calculate centered position
        int x = (screenWidth - this.imageWidth) / 2;
        int y = (screenHeight - this.imageHeight) / 2;
        
        // Render the background texture
        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // Initialize energyInfoArea if not already done
        if (energyInfoArea == null) {
            assignEnergyInfoArea();
        }
        if (energyInfoArea != null) {
        energyInfoArea.render(guiGraphics);
        }
        fluidRenderer.render(guiGraphics, x + 55, y + 15, menu.blockEntity.getFluid());
        renderProgressArrow(guiGraphics, x, y);
        renderInfoIcon(guiGraphics, x, y);
    }


    private void renderInfoIcon(GuiGraphics g, int baseX, int baseY) {
        int ix = baseX + ICON_SIZE + 28;
        int iy = baseY + ICON_SIZE;
        // full 16x16 texture
        g.blit(INFO_ICON, ix, iy, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
    }

    private void renderInfoIconTooltip(GuiGraphics g, int mouseX, int mouseY, int baseX, int baseY) {
        if (isMouseAboveAreaEnergy(mouseX, mouseY, baseX + 28, baseY, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE)) {
            Component info = Component.translatable("gui.tooltip.mutator.info");
            List<FormattedCharSequence> wrapped = this.font.split(info, 300);
            // renderTooltip API changed in 1.21.8 - temporarily disabled
            // TODO: Fix renderTooltip signature for 1.21.8
            // g.renderTooltip(this.font, wrapped, mouseX, mouseY);
        }
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(GUI_TEXTURE, x + 103, y + 37, 176, 0, 8, menu.getScaledProgress(), 256, 256);
        }
    }

    private void assignEnergyInfoArea() {
        if (this.width > 0 && this.height > 0 && menu != null && menu.blockEntity != null) {
            energyInfoArea = new EnergyDisplayTooltipArea(((this.width - this.imageWidth) / 2) + 156, ((this.height - this.imageHeight) / 2) + 13, menu.blockEntity.getEnergyStorage(null), 8, 64);
        }
    }

    private void renderEnergyInfoArea(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if (isMouseAboveAreaEnergy(mouseX, mouseY, x, y, 156, 11, 8, 64)) {
            // renderTooltip API changed in 1.21.8 - temporarily disabled
            // TODO: Fix renderTooltip signature for 1.21.8
            // guiGraphics.renderTooltip(this.font, energyInfoArea.getTooltips(), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    private boolean isMouseAboveAreaEnergy(int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(mouseX, mouseY, x + offsetX, y + offsetY, width, height);
    }

    private boolean isMouseAboveArea(int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, FluidTankRenderer renderer) {
        return MouseUtil.isMouseOver(mouseX, mouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderEnergyInfoArea(guiGraphics, mouseX, mouseY, x, y);
        renderFluidTooltipArea(guiGraphics, mouseX, mouseY, x, y, menu.blockEntity.getFluid(), 55, 15, fluidRenderer);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderInfoIconTooltip(guiGraphics, mouseX, mouseY, x, y);

        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
