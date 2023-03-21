package net.WizardingTurtle.Tutorialmod.networking.packet;

import net.WizardingTurtle.Tutorialmod.ammo.PlayerAmmo;
import net.WizardingTurtle.Tutorialmod.ammo.PlayerAmmoProvider;
import net.WizardingTurtle.Tutorialmod.client.ClientAmmoData;
import net.WizardingTurtle.Tutorialmod.item.ModItems;
import net.WizardingTurtle.Tutorialmod.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReloadRevolverC2SPacket {
    private static final String MESSAGE_RELOAD = "message.tutorialmod.reload_ammo";
    private static final String MESSAGE_FULL = "message.tutorialmod.full";
    private static final String MESSAGE_EMPTY = "message.tutorialmod.empty";
    public ReloadRevolverC2SPacket() {

    }
    public ReloadRevolverC2SPacket(FriendlyByteBuf buf) {

    }
    public void toBytes(FriendlyByteBuf buf) {

    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //OCCURS IN SERVER
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            //check if player has ammo
            if(hasAmmoBlockAroundThem(player, level)) {
                if (ClientAmmoData.getPlayerAmmo() == 6) {
                    // Notify player gun is fully loaded
                    player.sendSystemMessage(Component.translatable(MESSAGE_FULL).withStyle(ChatFormatting.DARK_GREEN));
                    player.sendSystemMessage(Component.literal("Current Ammo 6").withStyle(ChatFormatting.GOLD));
                } else {
                    // Notify player reloading is occuring
                    //player.sendSystemMessage(Component.translatable(MESSAGE_RELOAD).withStyle(ChatFormatting.DARK_GREEN));
                    // play reload sound
                    level.playSound(null, player.getOnPos(), SoundEvents.LEVER_CLICK, SoundSource.PLAYERS,
                            0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                    // increase ammo count
                    player.getCapability(PlayerAmmoProvider.PLAYER_AMMO).ifPresent(ammo -> {
                        ammo.addAmmo(ammo.getPlayerMaxAmmo() - ammo.getPlayerAmmo());
                        // output current ammo
                        //player.sendSystemMessage(Component.literal("Current Ammo " + ammo.getPlayerAmmo()).withStyle(ChatFormatting.GOLD));
                        ModMessages.sendToPlayer(new AmmoDataSyncS2CPacket(ammo.getPlayerAmmo()), player);
                    });
                }
            } else if (hasRevolverAmmo(player)) {
                if (ClientAmmoData.getPlayerAmmo() == 6) {
                    // Notify player gun is fully loaded
                    player.sendSystemMessage(Component.translatable(MESSAGE_FULL).withStyle(ChatFormatting.DARK_GREEN));
                    player.sendSystemMessage(Component.literal("Current Ammo 6").withStyle(ChatFormatting.GOLD));
                } else {
                    // Notify player reloading is occuring
                    //player.sendSystemMessage(Component.translatable(MESSAGE_RELOAD).withStyle(ChatFormatting.DARK_GREEN));
                    // play reload sound
                    level.playSound(null, player.getOnPos(), SoundEvents.LEVER_CLICK, SoundSource.PLAYERS,
                            0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                    // increase ammo count
                    player.getCapability(PlayerAmmoProvider.PLAYER_AMMO).ifPresent(ammo -> {
                        int x = ClientAmmoData.getPlayerMissingAmmo();
                        int h = ReturnCurrentAmmo(player);
                        SubRevolverAmmo(player, x);
                        ammo.addAmmo(h);
                        // output current ammo
                        //player.sendSystemMessage(Component.literal("Current Ammo " + ammo.getPlayerAmmo()).withStyle(ChatFormatting.GOLD));
                        ModMessages.sendToPlayer(new AmmoDataSyncS2CPacket(ammo.getPlayerAmmo()), player);
                    });
                }
            } else {
                // Notify player no ammo
                player.getCapability(PlayerAmmoProvider.PLAYER_AMMO).ifPresent(ammo -> {
                    player.sendSystemMessage(Component.translatable(MESSAGE_EMPTY).withStyle(ChatFormatting.DARK_RED));
                    // output current ammo
                    //player.sendSystemMessage(Component.literal("Current Ammo " + ammo.getPlayerAmmo()).withStyle(ChatFormatting.GOLD));
                    ModMessages.sendToPlayer(new AmmoDataSyncS2CPacket(ammo.getPlayerAmmo()), player);
                });

            }
        });
        return true;
    }

    private boolean hasRevolverAmmo(ServerPlayer player) {
        IItemHandler inventory = new PlayerMainInvWrapper(player.getInventory());
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).getItem() == Items.ARROW){
                return true;
            }
        }
        return false;
    }
    private void SubRevolverAmmo(ServerPlayer player, int x) {
        IItemHandler inventory = new PlayerMainInvWrapper(player.getInventory());
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).getItem() == Items.ARROW){
                inventory.getStackInSlot(i).shrink(x);
                return;
            }
        }
    }
    private int ReturnCurrentAmmo(ServerPlayer player) {
        IItemHandler inventory = new PlayerMainInvWrapper(player.getInventory());
        int arrowCount = 0; // initialize arrow count to 0
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == Items.ARROW) { // check if slot is not empty and contains an arrow
                arrowCount += stack.getCount(); // add the count of arrows in this slot to the total arrow count
            }
        }
        return arrowCount;
    }



    private boolean hasAmmoBlockAroundThem(ServerPlayer player, ServerLevel level) {
        return level.getBlockStates(player.getBoundingBox().inflate(2))
                .filter(state -> state.is(Blocks.IRON_BLOCK)).toArray().length > 0;
        //filter method returns only iron blocks detected
        //returning the array.length to be more than 0 checks that at least 1 or more blocks are near the player
    }
}
