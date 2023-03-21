package net.WizardingTurtle.Tutorialmod.networking.packet;

import net.WizardingTurtle.Tutorialmod.ammo.PlayerAmmoProvider;
import net.WizardingTurtle.Tutorialmod.client.ClientAmmoData;
import net.WizardingTurtle.Tutorialmod.entity.ModEntityTypes;
import net.WizardingTurtle.Tutorialmod.entity.projectile.BulletEntity;
import net.WizardingTurtle.Tutorialmod.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShootRevolverC2SPacket {
    private static final String MESSAGE_EMPTY_REVOLVER = "message.tutorialmod.emptyrevolver";
    public ShootRevolverC2SPacket() {

    }
    public ShootRevolverC2SPacket(FriendlyByteBuf buf) {

    }
    public void toBytes(FriendlyByteBuf buf) {

    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //OCCURS IN SERVER
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            if (hasRevolverAmmoInGun(player)) {
                // play shoot sound
                level.playSound(null, player.getOnPos(), SoundEvents.IRON_GOLEM_REPAIR, SoundSource.PLAYERS,
                        0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                // spawn entity test
                EntityType.SNOWBALL.spawn(level, (ItemStack) null, null, player.blockPosition(),
                        MobSpawnType.COMMAND, true, false);
                // shoot bullet entity
                if (!player.level.isClientSide()){
                    SHOOT(player);
                }
                // decrease ammo count
                player.getCapability(PlayerAmmoProvider.PLAYER_AMMO).ifPresent(ammo -> {
                    ammo.subAmmo(1);
                    // output current ammo
                    /*
                    player.sendSystemMessage(Component.literal("BANG"));
                    player.sendSystemMessage(Component.literal("Current Ammo " + ammo.getPlayerAmmo())
                            .withStyle(ChatFormatting.GOLD));
                     */
                    ModMessages.sendToPlayer(new AmmoDataSyncS2CPacket(ammo.getPlayerAmmo()), player);
                });
            } else {
                // Notify player no ammo
                player.getCapability(PlayerAmmoProvider.PLAYER_AMMO).ifPresent(ammo -> {
                    player.sendSystemMessage(Component.translatable(MESSAGE_EMPTY_REVOLVER).withStyle(ChatFormatting.DARK_RED));
                    // output current ammo
                    //player.sendSystemMessage(Component.literal("Current Ammo " + ammo.getPlayerAmmo()).withStyle(ChatFormatting.GOLD));
                    ModMessages.sendToPlayer(new AmmoDataSyncS2CPacket(ammo.getPlayerAmmo()), player);
                });

            }

        });
        return true;
    }
    private boolean hasRevolverAmmoInGun(ServerPlayer player) {
        // checks if gun has ammo
        player.getCapability(PlayerAmmoProvider.PLAYER_AMMO).ifPresent(ammo -> {
             ammo.getPlayerAmmo();
        });
        return (ClientAmmoData.getPlayerAmmo() > 0);
    }
    private void SHOOT(ServerPlayer player) {
        Arrow arrow = new Arrow(player.level, player);
        arrow.setBaseDamage(10);
        arrow.setShotFromCrossbow(true);

        // Calculate arrow velocity based on player's look direction
        float pitch = player.getXRot();
        float yaw = player.getYRot();

        double x = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
        double y = -Math.sin(Math.toRadians(pitch));
        double z = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));

        // projectile acceleration multiplier + movement
        for (double VM = 0; VM < 16; VM++) {
            arrow.setDeltaMovement(x * VM, y* VM, z* VM);
            VM++;
        }
        // spawn Arrow
        player.level.addFreshEntity(arrow);
    }
}

