package net.micaxs.smokeleaf.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.screen.renderer.EnergyDisplayTooltipArea;
import net.micaxs.smokeleaf.utils.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Optional;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorMenu> {
    public static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "textures/gui/generator/generator_gui.png");
    public static final ResourceLocation LIT_PROGRESS_TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "container/furnace/lit_progress");
    private EnergyDisplayTooltipArea energyInfoArea;

    private static final ResourceLocation INFO_ICON = ResourceLocation.fromNamespaceAndPath(SmokeleafIndustries.MODID, "textures/gui/icons/info.png");
    private static final int ICON_SIZE = 8;
    private static final float TOOLTIP_SCALE = 0.5f;
    private static final int IMAGE_WIDTH = 176;
    private static final int IMAGE_HEIGHT = 166;

    public GeneratorScreen(GeneratorMenu menu, Inventory playerInvetory, Component title) {
        super(menu, playerInvetory, title);
        this.imageWidth = IMAGE_WIDTH;
        this.imageHeight = IMAGE_HEIGHT;
    }


    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 100000;
        this.titleLabelY = 100000;
        assignEnergyInfoArea();
    }

    @Override
    public void resize(net.minecraft.client.Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        assignEnergyInfoArea();
    }


    private void renderEnergyInfoArea(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        // Tooltips temporarily disabled - GuiGraphics.renderTooltip API changed in 1.21.8
    }

    private void assignEnergyInfoArea() {
        if (this.width > 0 && this.height > 0 && menu != null && menu.blockEntity != null) {
            int x = this.leftPos;
            int y = this.topPos;
            energyInfoArea = new EnergyDisplayTooltipArea(x + 125, y + 21, menu.blockEntity.getEnergyStorage(null), 13, 38);
        }
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        // Use leftPos and topPos from AbstractContainerScreen for proper positioning
        int x = this.leftPos;
        int y = this.topPos;
        
        // Render the background texture
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);

        // Render energy bar if initialized
        if (energyInfoArea != null) {
            energyInfoArea.render(guiGraphics);
        }
        renderFuelProgress(guiGraphics, x, y);
        renderInfoIcon(guiGraphics, x, y);
    }


    private void renderInfoIcon(GuiGraphics g, int baseX, int baseY) {
        int ix = baseX + ICON_SIZE;
        int iy = baseY + ICON_SIZE;
        // full 16x16 texture
        g.blit(INFO_ICON, ix, iy, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
    }

    private void renderInfoIconTooltip(GuiGraphics g, int mouseX, int mouseY, int baseX, int baseY) {
        // Tooltips temporarily disabled - GuiGraphics.renderTooltip API changed in 1.21.8
    }

    private void renderFuelProgress(GuiGraphics guiGraphics, int x, int y) {
        int scaledHeight = menu.getFuelStoredScaled();
        if (scaledHeight > 0) {
            int adjustedY = y + 38 + (11 - scaledHeight);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x + 81, adjustedY, 176.0F, (float)(14 - scaledHeight), 14, scaledHeight, 256, 256);
        }
    }


//    private void renderFuelBurning(GuiGraphics guiGraphics, int x, int y) {
//        if (this.menu.isBurning()) {
//            int l = Mth.ceil(this.menu.getFuelProgress() * 13.0F) + 1;
//            guiGraphics.blitSprite(LIT_PROGRESS_TEXTURE, 14, 14, 0, 14 - l, x + 80, y + 18 + 14 -l, 14, l);
//        }
//    }

    private boolean isMouseAboveArea(int mouseX, int mouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(mouseX, mouseY, x + offsetX, y + offsetY, width, height);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        renderEnergyInfoArea(guiGraphics, mouseX, mouseY, x, y);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // Use leftPos and topPos for tooltip positioning
        int x = this.leftPos;
        int y = this.topPos;
        renderInfoIconTooltip(guiGraphics, mouseX, mouseY, x, y);

        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
