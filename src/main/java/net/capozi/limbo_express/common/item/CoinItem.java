package net.capozi.limbo_express.common.item;

import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.index.WatheSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class CoinItem extends Item {
    public final int[] amounts = { 25, 50, 75, 100, 150 };
    public CoinItem(Settings settings) {
        super(settings);
    }
    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        PlayerShopComponent shop = PlayerShopComponent.KEY.get(player);
        if (clickType.equals(ClickType.RIGHT)) {
            stack.decrement(1);
            int tempAmount;
            Random rand = new Random();
            int chance = Math.abs(rand.nextInt()) % 100;
            if (chance < 1) { tempAmount = amounts[4]; } else if (chance < 10) { tempAmount = amounts[3]; }
            else if (chance < 25) { tempAmount = amounts[2]; } else if (chance < 50) { tempAmount = amounts[1]; }
            else { tempAmount = amounts[0]; }
            shop.balance += tempAmount;
            if (player instanceof ServerPlayerEntity serverPlayer) {
                serverPlayer.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY_FAIL), SoundCategory.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 0.9F + player.getRandom().nextFloat() * 0.2F, player.getRandom().nextLong()));
            }
            tempAmount = 0;
            return true;
        }
        return super.onStackClicked(stack, slot, clickType, player);
    }
}
