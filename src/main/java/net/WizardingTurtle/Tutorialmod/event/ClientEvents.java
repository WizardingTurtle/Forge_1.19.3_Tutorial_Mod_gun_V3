package net.WizardingTurtle.Tutorialmod.event;

import net.WizardingTurtle.Tutorialmod.TutorialMod;
import net.WizardingTurtle.Tutorialmod.client.AmmoHudOverlay;
import net.WizardingTurtle.Tutorialmod.client.render.BulletRenderer;
import net.WizardingTurtle.Tutorialmod.entity.ModEntityTypes;
import net.WizardingTurtle.Tutorialmod.networking.packet.ReloadRevolverC2SPacket;
import net.WizardingTurtle.Tutorialmod.networking.packet.ShootRevolverC2SPacket;
import net.WizardingTurtle.Tutorialmod.util.KeyBinding;
import net.WizardingTurtle.Tutorialmod.networking.ModMessages;
import net.WizardingTurtle.Tutorialmod.networking.packet.ExampleC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.RELOADING_KEY.consumeClick()) {
                ModMessages.sendToServer(new ReloadRevolverC2SPacket());
            }
        }
        @SubscribeEvent
        public static void doSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.BULLET_ENTITY.get(), BulletRenderer::new);
        }

    }

    @Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.RELOADING_KEY);
        }
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("ammo", AmmoHudOverlay.HUD_BULLET);
        }
    }
}
