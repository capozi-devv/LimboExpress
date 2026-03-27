package net.capozi.limbo_express.common.item;

import net.minecraft.block.SoulFireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MatchstickItem extends Item {

    private static final int DEFAULT_FIRE_SECONDS = 8;

    public MatchstickItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        if (entity.isFireImmune()) {
            return ActionResult.PASS;
        }
        World world = player.getWorld();
        if (!world.isClient) {
            entity.setOnFireFor(DEFAULT_FIRE_SECONDS);
            entity.damage(world.getDamageSources().inFire(), isOnSoulBase(world, entity) ? 2.0f : 1.0f);
            consumeOne(stack, player);
        }
        return ActionResult.success(world.isClient);
    }

    private static boolean isOnSoulBase(World world, LivingEntity entity) {
        return SoulFireBlock.isSoulBase(entity.getSteppingBlockState()) || SoulFireBlock.isSoulBase(world.getBlockState(entity.getBlockPos().down()));
    }

    private static void consumeOne(ItemStack stack, PlayerEntity player) {
        if (player == null || !player.isCreative()) {
            stack.decrement(1);
        }
    }
}
