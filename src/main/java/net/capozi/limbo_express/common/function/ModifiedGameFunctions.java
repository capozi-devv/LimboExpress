package net.capozi.limbo_express.common.function;

import dev.doctor4t.wathe.game.GameFunctions;
import net.capozi.limbo_express.common.ModifiedGameConstants;
import net.capozi.limbo_express.common.cca.CivilianInstinctComponent;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.doctor4t.wathe.client.util.WatheItemTooltips.COOLDOWN_COLOR;

public class ModifiedGameFunctions {
    public static boolean triggerSwap(ServerWorld world, PlayerEntity killer) {
        List<ServerPlayerEntity> rawPlayers = world.getPlayers();
        Map<ServerPlayerEntity, BlockPos> playerPositionMap = new HashMap<>();
        List<ServerPlayerEntity> playerCheck = new ArrayList<>();
        if (world != null) {
            if (rawPlayers.isEmpty()) return false;
            for (ServerPlayerEntity player : rawPlayers) {
                if (GameFunctions.isPlayerAliveAndSurvival(player)) {
                    playerPositionMap.put(player, player.getBlockPos());
                    playerCheck.add(player);
                }
            }
            if (playerPositionMap.isEmpty()) return false;
            for (ServerPlayerEntity player : playerCheck) {
                int index = Random.create().nextBetween(0, playerCheck.size());
                BlockPos tpPos = playerCheck.get(index).getBlockPos();
                if (tpPos == playerPositionMap.get(player)) {
                    if (playerCheck.size() <= 1) {
                        player.playSoundToPlayer(SoundInit.SWAP, SoundCategory.PLAYERS, 1f, 1f);
                        player.sendMessage(Text.translatable("message.limbo_express.player.swap_fail"));
                        return true;
                    }
                    index++;
                    tpPos = playerCheck.get(index).getBlockPos();
                }
                player.teleport(world, tpPos.getX(), tpPos.getY(), tpPos.getZ(), player.headYaw, player.prevPitch);
                player.playSoundToPlayer(SoundInit.SWAP, SoundCategory.PLAYERS, 1f, 1f);
                playerCheck.remove(index);
                playerPositionMap.remove(player);
            }
            PlayerSwapComponent.KEY.get(killer).setCooldown(ModifiedGameConstants.swapCooldown);
            return true;
        }
        return false;
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
}
