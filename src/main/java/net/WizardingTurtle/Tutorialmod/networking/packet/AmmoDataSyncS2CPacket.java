package net.WizardingTurtle.Tutorialmod.networking.packet;

import net.WizardingTurtle.Tutorialmod.ammo.PlayerAmmoProvider;
import net.WizardingTurtle.Tutorialmod.client.ClientAmmoData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AmmoDataSyncS2CPacket {
    private final int ammo;
    public AmmoDataSyncS2CPacket(int ammo) {
        this.ammo = ammo;
    }
    public AmmoDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.ammo = buf.readInt();
    }
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(ammo);
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //OCCURS ON CLIENT
            ClientAmmoData.set(ammo);

        });
        return true;
    }

    private boolean hasRevolverAmmo() {
        // insert code that checks for ammo in inventory
        return true;
    }
}
