package net.capozi.limbo_express.common.item;

import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.util.AdventureUsable;
import net.capozi.limbo_express.common.cca.OverdoseComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class SanityPillsItem extends Item implements AdventureUsable {
    public SanityPillsItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        OverdoseComponent.KEY.get(user).timesPillsUsed++;
        PlayerMoodComponent.KEY.get(user).setMood(PlayerMoodComponent.KEY.get(user).getMood() + 0.5f);
        user.getWorld().playSound(user, user.getBlockPos(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 1f, 1f);
        user.getStackInHand(hand).decrement(1);
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(1, Text.of("§7Pills that can restore your sanity"));
        tooltip.add(2, Text.of("§7in a pinch. Take sparingly."));
    }
}
