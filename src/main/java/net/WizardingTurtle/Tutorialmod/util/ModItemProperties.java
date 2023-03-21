package net.WizardingTurtle.Tutorialmod.util;

import net.WizardingTurtle.Tutorialmod.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItemProperties {

    public static void addCustomItemProperties(){
        makeCrossbow(ModItems.REVOLVER.get());
        makeCrossbow(ModItems.GUNREVOLVER.get());
        makeBow(ModItems.KAUPENBOW.get());

    }

    private static void makeBow(Item item1) {
        ItemProperties.register(item1, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(item1, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });
    }
    private static void makeCrossbow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174610_, p_174611_, p_174612_, p_174613_) -> {
            if (p_174612_ == null) {
                return 0.0F;
            } else {
                return CrossbowItem.isCharged(p_174610_) ? 0.0F : (float)(p_174610_.getUseDuration() - p_174612_.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(p_174610_);
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174605_, p_174606_, p_174607_, p_174608_) -> {
            return p_174607_ != null && p_174607_.isUsingItem() && p_174607_.getUseItem() == p_174605_ && !CrossbowItem.isCharged(p_174605_) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, new ResourceLocation("charged"), (p_174600_, level, entity, p_174603_) -> {
            return entity != null && CrossbowItem.isCharged(p_174600_) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, new ResourceLocation("firework"), (itemStack, p_174596_, p_174597_, p_174598_) -> {
            return p_174597_ != null && CrossbowItem.isCharged(itemStack) && CrossbowItem.containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });
    }
}
