package net.WizardingTurtle.Tutorialmod.item.Guns;

import net.WizardingTurtle.Tutorialmod.networking.ModMessages;
import net.WizardingTurtle.Tutorialmod.networking.packet.ShootRevolverC2SPacket;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SixShooter extends Item {
    public SixShooter(Properties properties) {
        super(properties);
    }

    public boolean onUpdate(ItemStack itemstack, Level level, Player player, int i, boolean flag)
    {

        return flag;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
            //decreases ammo counter
            ModMessages.sendToServer(new ShootRevolverC2SPacket());
            //cooldown
            player.getCooldowns().addCooldown(this,1);
        }
        return super.use(level, player, hand);
    }
}
