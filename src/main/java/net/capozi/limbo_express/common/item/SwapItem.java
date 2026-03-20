package net.capozi.limbo_express.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class SwapItem extends Item {
    public SwapItem(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(1, Text.translatable("item.limbo_express.swap.tooltipLine1"));
        tooltip.add(2, Text.translatable("item.limbo_express.swap.tooltipLine2"));
        tooltip.add(3, Text.translatable("item.limbo_express.swap.tooltipLine3"));
    }
}
