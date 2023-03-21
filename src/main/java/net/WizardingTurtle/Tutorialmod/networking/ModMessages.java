package net.WizardingTurtle.Tutorialmod.networking;

import net.WizardingTurtle.Tutorialmod.TutorialMod;
import net.WizardingTurtle.Tutorialmod.networking.packet.AmmoDataSyncS2CPacket;
import net.WizardingTurtle.Tutorialmod.networking.packet.ExampleC2SPacket;
import net.WizardingTurtle.Tutorialmod.networking.packet.ReloadRevolverC2SPacket;
import net.WizardingTurtle.Tutorialmod.networking.packet.ShootRevolverC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(TutorialMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ExampleC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ExampleC2SPacket::new)
                .encoder(ExampleC2SPacket::toBytes)
                .consumerMainThread(ExampleC2SPacket::handle)
                .add();

        net.messageBuilder(ReloadRevolverC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ReloadRevolverC2SPacket::new)
                .encoder(ReloadRevolverC2SPacket::toBytes)
                .consumerMainThread(ReloadRevolverC2SPacket::handle)
                .add();

        net.messageBuilder(ShootRevolverC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ShootRevolverC2SPacket::new)
                .encoder(ShootRevolverC2SPacket::toBytes)
                .consumerMainThread(ShootRevolverC2SPacket::handle)
                .add();

        net.messageBuilder(AmmoDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AmmoDataSyncS2CPacket::new)
                .encoder(AmmoDataSyncS2CPacket::toBytes)
                .consumerMainThread(AmmoDataSyncS2CPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
