package notjoe.pointersmod.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.common.container.ContainerInvPointer;

public class GuiInvPointer extends GuiContainer {
    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private static final ResourceLocation slot =
        new ResourceLocation(PointersMod.MODID, "textures/gui/components/slot.png");
    private static final ResourceLocation frame =
        new ResourceLocation(PointersMod.MODID, "textures/gui/components/frame.png");

    private ContainerInvPointer container;

    public GuiInvPointer(ContainerInvPointer container) {
        super(container);

        this.container = container;

        xSize = container.width;
        ySize = container.height;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(slot);
        for (Slot s : container.inventorySlots) {
            drawTexturedModalRect(guiLeft + s.xDisplayPosition - 1, guiTop + s.yDisplayPosition - 1,
                0, 0, 18, 18);
        }
    }
}
