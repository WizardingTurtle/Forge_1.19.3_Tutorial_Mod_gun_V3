package net.WizardingTurtle.Tutorialmod.item.Guns;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RevolverItem extends Item {
    public RevolverItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
            //shoot
            //set a cooldown
            player.sendSystemMessage(Component.literal("BANG"));
            player.getCooldowns().addCooldown(this,10);
        }
        return super.use(level, player, hand);
    }
}
