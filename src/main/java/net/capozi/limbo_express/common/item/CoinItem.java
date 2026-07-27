package net.capozi.limbo_express.common.item;

import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.capozi.limbo_express.foundation.DataComponentTypeInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;

import java.util.List;

public class CoinItem extends Item {
    public CoinItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.getComponents().contains(DataComponentTypeInit.COIN_VALUE)) {
            tooltip.add(Text.literal("Value: [" + stack.get(DataComponentTypeInit.COIN_VALUE) + "]").setStyle(Style.EMPTY.withColor(0xFF8C00)));
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        PlayerShopComponent shop = PlayerShopComponent.KEY.get(player);
        if (clickType.equals(ClickType.RIGHT)) {
            stack.decrement(1);
            if (stack.getComponents().contains(DataComponentTypeInit.COIN_VALUE)) {
                shop.balance += stack.get(DataComponentTypeInit.COIN_VALUE);
            }
        }
        return super.onStackClicked(stack, slot, clickType, player);
    }
}
