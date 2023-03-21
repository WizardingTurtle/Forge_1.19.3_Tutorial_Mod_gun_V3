package net.WizardingTurtle.Tutorialmod.client.render;

import net.WizardingTurtle.Tutorialmod.TutorialMod;
import net.WizardingTurtle.Tutorialmod.entity.projectile.BulletEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BulletRenderer extends ArrowRenderer<BulletEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MOD_ID, "textures/entity/projectiles/bullet_entity.png");

    public BulletRenderer(EntityRendererProvider.Context manager) {
        super(manager);
    }

    public ResourceLocation getTextureLocation(BulletEntity arrow) {
        return TEXTURE;
    }
}
