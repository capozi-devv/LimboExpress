package net.capozi.limbo_express.common.item;

import net.capozi.limbo_express.common.cca.PlayerAnonymityComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class AnonymityItem extends Item {
    public AnonymityItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(1, Text.translatable("item.limbo_express.anonymity.tooltipLine1"));
        tooltip.add(2, Text.translatable("item.limbo_express.anonymity.tooltipLine2"));
        tooltip.add(3, Text.translatable("item.limbo_express.anonymity.tooltipLine3"));
        tooltip.add(4, Text.translatable("item.limbo_express.anonymity.tooltipLine4"));
    }
}
