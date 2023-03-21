package net.WizardingTurtle.Tutorialmod.event;

import net.WizardingTurtle.Tutorialmod.TutorialMod;
import net.WizardingTurtle.Tutorialmod.ammo.PlayerAmmo;
import net.WizardingTurtle.Tutorialmod.ammo.PlayerAmmoProvider;
import net.WizardingTurtle.Tutorialmod.networking.ModMessages;
import net.WizardingTurtle.Tutorialmod.networking.packet.AmmoDataSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    // applies reloading capability to the player
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerAmmoProvider.PLAYER_AMMO).isPresent()) {
                event.addCapability(new ResourceLocation(TutorialMod.MOD_ID, "properties"), new PlayerAmmoProvider());
            }
        }
    }

    @SubscribeEvent
    // resets ammo counter
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerAmmoProvider.PLAYER_AMMO).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerAmmoProvider.PLAYER_AMMO).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                    // keeps the original capability on player
                });
            });
        }
    }
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerAmmo.class);
    }
    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerAmmoProvider.PLAYER_AMMO).ifPresent(ammo -> {
                    ModMessages.sendToPlayer(new AmmoDataSyncS2CPacket(ammo.getPlayerAmmo()), player);
                });
            }
        }
    }

}
