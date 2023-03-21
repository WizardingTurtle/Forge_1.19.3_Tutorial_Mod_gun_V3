package net.WizardingTurtle.Tutorialmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.WizardingTurtle.Tutorialmod.TutorialMod;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class AmmoHudOverlay {
    private static final ResourceLocation FILLED_BULLET = new ResourceLocation(TutorialMod.MOD_ID,
            "textures/ammo/filled_bullet.png");
    private static final ResourceLocation EMPTY_BULLET = new ResourceLocation(TutorialMod.MOD_ID,
            "textures/ammo/empty_bullet.png");

    public static final IGuiOverlay HUD_BULLET = ((gui, poseStack, partialTick, width, height) -> {
        // adjusts the gui based on screen resolution
        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_BULLET);
        for(int i = 0; i < 6; i++) {
            GuiComponent.blit(poseStack, x - 94 + (i * 5), y - 54, 0, 0,
                    12, 12, 12, 12);
        }
        RenderSystem.setShaderTexture(0, FILLED_BULLET);
        for(int i = 0; i < 6; i++) {
            if (ClientAmmoData.getPlayerAmmo() > i) {
                GuiComponent.blit(poseStack, x - 94 + (i * 5), y - 54, 0, 0,
                        12, 12, 12, 12);
            } else {
                break;
            }

        }
    });
}
