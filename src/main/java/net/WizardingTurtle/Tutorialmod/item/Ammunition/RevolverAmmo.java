package net.WizardingTurtle.Tutorialmod.item.Ammunition;

import net.WizardingTurtle.Tutorialmod.entity.ModEntityTypes;
import net.WizardingTurtle.Tutorialmod.entity.projectile.BulletEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RevolverAmmo extends ArrowItem {
    public final float damage;
    public RevolverAmmo(Properties properties, float damage) {
        super(properties);
        this.damage = damage;
    }
    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        Arrow arrow = new Arrow(level, shooter);
        arrow.setBaseDamage(this.damage);
        return new BulletEntity(ModEntityTypes.BULLET_ENTITY.get(), shooter, level);
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.world.entity.player.Player player) {
        int enchant = net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.world.item.enchantment.Enchantments.INFINITY_ARROWS, bow);
        return enchant <= 0 ? false : this.getClass() == RevolverAmmo.class;
    }

}
