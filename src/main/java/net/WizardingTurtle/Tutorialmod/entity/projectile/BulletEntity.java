package net.WizardingTurtle.Tutorialmod.entity.projectile;

import net.WizardingTurtle.Tutorialmod.item.ModItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class BulletEntity extends AbstractArrow {

    public BulletEntity(EntityType<BulletEntity> type, Level level) {
        super(type, level);
        this.setBaseDamage(10);
    }
    public BulletEntity(EntityType<BulletEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public BulletEntity(EntityType<BulletEntity> type, LivingEntity shooter, Level world) {
        super(type, shooter, world);
    }

    @Override
    public ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }


    @Override
    protected void tickDespawn() {
        if (this.inGroundTime > 60){
            this.discard();
        }
    }
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}