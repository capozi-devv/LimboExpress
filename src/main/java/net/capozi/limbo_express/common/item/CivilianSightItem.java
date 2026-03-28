package net.capozi.limbo_express.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class CivilianSightItem extends Item {
    public CivilianSightItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(1, Text.translatable("item.limbo_express.civilian_sight.tooltipLine1"));
        tooltip.add(2, Text.translatable("item.limbo_express.civilian_sight.tooltipLine2"));
        tooltip.add(3, Text.translatable("item.limbo_express.civilian_sight.tooltipLine3"));
    }
}
