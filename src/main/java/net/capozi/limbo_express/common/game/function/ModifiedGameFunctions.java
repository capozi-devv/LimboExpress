package net.capozi.limbo_express.common.game.function;

import dev.doctor4t.wathe.game.GameFunctions;
import net.capozi.limbo_express.common.ModifiedGameConstants;
import net.capozi.limbo_express.common.cca.CivilianInstinctComponent;
import net.capozi.limbo_express.common.cca.PlayerAnonymityComponent;
import net.capozi.limbo_express.common.cca.PlayerSwapComponent;
import net.capozi.limbo_express.foundation.ItemInit;
import net.capozi.limbo_express.foundation.SoundInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static dev.doctor4t.wathe.client.util.WatheItemTooltips.COOLDOWN_COLOR;

public class ModifiedGameFunctions {
    public static boolean triggerSwap(World world, PlayerEntity killer) {
        List<? extends PlayerEntity> rawPlayers = world.getPlayers();
        Map<PlayerEntity, BlockPos> playerPositionMap = new HashMap<>();
        List<PlayerEntity> playerCheck = new ArrayList<>();
        List<BlockPos> blockCheck = new ArrayList<>();
        if (world != null) {
            for (PlayerEntity player : rawPlayers) {
                if (GameFunctions.isPlayerAliveAndSurvival(player)) {
                    playerPositionMap.put(player, player.getBlockPos());
                    playerCheck.add(player);
                    blockCheck.add(player.getBlockPos());
                }
            }
            Iterator<PlayerEntity> playerCheckIterator = playerCheck.iterator();
            while (playerCheckIterator.hasNext()) {
                int index = Random.create().nextBetween(0, playerCheck.size());
                int index2 = Random.create().nextBetween(0, playerCheck.size());
                PlayerEntity player = playerCheck.get(index);
                BlockPos tpPos = blockCheck.get(index2);
                if (tpPos == playerPositionMap.get(player)) {
                    if (playerCheck.size() <= 1) {
                        player.playSoundToPlayer(SoundInit.SWAP, SoundCategory.PLAYERS, 1f, 1f);
                        player.sendMessage(Text.translatable("message.limbo_express.player.swap_fail"));
                        break;
                    }
                    index2++;
                    tpPos = playerCheck.get(index2).getBlockPos();
                }
                player.teleport(tpPos.getX(), tpPos.getY(), tpPos.getZ(), false);
                player.playSound(SoundInit.SWAP, 1f, 1f);
                playerCheck.remove(index);
                playerPositionMap.remove(player);
                blockCheck.remove(index2);
            }
            PlayerSwapComponent.KEY.get(killer).setCooldown(ModifiedGameConstants.swapCooldown);
            killer.getItemCooldownManager().set(ItemInit.SWAP, ModifiedGameConstants.swapCooldown);
            return true;
        }
        return true;
    }
    public static boolean activateCivilianSight(PlayerEntity user) {
        CivilianInstinctComponent instinct = CivilianInstinctComponent.KEY.get(user);
        if (!instinct.hasCivilianInstinct()) {
            if (instinct.getCooldown() != 0) return false;
            instinct.setHasCivilianInstinct(true);
            instinct.setCooldown(ModifiedGameConstants.civilianSightCooldown);
            user.getItemCooldownManager().set(ItemInit.CIVILIAN_SIGHT, ModifiedGameConstants.civilianSightCooldown);
            user.playSoundToPlayer(SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.PLAYERS, 1f, 1f);
            return true;
        }
        return false;
    }
    @SuppressWarnings("all")
    public static void addCooldownText(Item item, List<Text> tooltipList, @NotNull ItemStack itemStack) {
        if (!itemStack.isOf(item)) return;
        ItemCooldownManager itemCooldownManager = MinecraftClient.getInstance().player.getItemCooldownManager();
        if (itemCooldownManager.isCoolingDown(item)) {
            ItemCooldownManager.Entry knifeEntry = itemCooldownManager.entries.get(item);
            int timeLeft = knifeEntry.endTick - itemCooldownManager.tick;
            if (timeLeft > 0) {
                int minutes = (int) Math.floor((double) timeLeft / 1200);
                int seconds = (timeLeft - (minutes * 1200)) / 20;
                String countdown = (minutes > 0 ? minutes + "m" : "") + (seconds > 0 ? seconds + "s" : "");
                tooltipList.add(Text.translatable("tip.cooldown", countdown).withColor(COOLDOWN_COLOR));
            }
        }
    }
    public static boolean anonymize(PlayerEntity player) {
        PlayerAnonymityComponent component = PlayerAnonymityComponent.KEY.get(player);
        if (!component.isAnonymous()) {
            if (component.getCooldown() != 0) return false;
            component.setAnonymous(true);
            component.setCooldown(ModifiedGameConstants.civilianSightCooldown);
            player.getItemCooldownManager().set(ItemInit.ANONYMITY, ModifiedGameConstants.civilianSightCooldown);
            return true;
        }
        return false;
    }
}
