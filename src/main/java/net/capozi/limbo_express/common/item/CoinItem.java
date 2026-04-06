package net.capozi.limbo_express.common.item;

import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
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
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        PlayerShopComponent shop = PlayerShopComponent.KEY.get(player);
        if (clickType.equals(ClickType.RIGHT)) {
            stack.decrement(1);
            int tempAmount;
            Random rand = new Random();
            int chance = Math.abs(rand.nextInt()) % 100;
            if (chance < 1) { tempAmount = amounts[5]; } else if (chance < 10) { tempAmount = amounts[4]; }
            else if (chance < 25) { tempAmount = amounts[3]; } else if (chance < 50) { tempAmount = amounts[2]; }
            else { tempAmount = amounts[1]; }
            shop.balance += tempAmount;
            tempAmount = 0;
            return true;
        }
        return super.onStackClicked(stack, slot, clickType, player);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity) {
            PlayerShopComponent shop = PlayerShopComponent.KEY.get(entity);
            stack.decrement(1);
            int tempAmount;
            Random rand = new Random();
            int chance = Math.abs(rand.nextInt()) % 100;
            if (chance < 1) { tempAmount = amounts[5]; } else if (chance < 10) { tempAmount = amounts[4]; }
            else if (chance < 25) { tempAmount = amounts[3]; } else if (chance < 50) { tempAmount = amounts[2]; }
            else { tempAmount = amounts[1]; }
            shop.balance += tempAmount;
            tempAmount = 0;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
    }
}
