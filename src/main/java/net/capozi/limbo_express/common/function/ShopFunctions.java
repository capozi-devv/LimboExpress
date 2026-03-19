package net.capozi.limbo_express.common.function;

import dev.doctor4t.wathe.game.GameFunctions;
import net.capozi.limbo_express.common.ModifiedGameConstants;
import net.capozi.limbo_express.common.cca.PlayerSwapComponent;
import net.capozi.limbo_express.foundation.SoundInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static dev.doctor4t.wathe.client.util.WatheItemTooltips.COOLDOWN_COLOR;

public class ShopFunctions {
    public static boolean triggerSwap(ServerWorld world, PlayerEntity killer) {
        List<ServerPlayerEntity> rawPlayers = world.getPlayers();
        List<BlockPos> positions = new ArrayList<>();
        List<ServerPlayerEntity> availablePlayers = new ArrayList<>();
        if (world != null) {
            if (rawPlayers.isEmpty()) return false;
            for (ServerPlayerEntity player : rawPlayers) {
                if (GameFunctions.isPlayerAliveAndSurvival(player)) {
                    availablePlayers.add(player);
                    positions.add(player.getBlockPos());
                }
            }
            if (availablePlayers.isEmpty()) return false;
            if (positions.isEmpty()) return false;
            for (ServerPlayerEntity player : availablePlayers) {
                int index = Random.create().nextBetween(0, positions.size());
                BlockPos tpPos = positions.get(index);
                player.teleport(world, tpPos.getX(), tpPos.getY(), tpPos.getZ(), player.headYaw, player.prevPitch);
                player.playSound(SoundInit.SWAP, 1f, 1f);
                availablePlayers.remove(player);
                positions.remove(index);
            }
            positions.clear();
            availablePlayers.clear();
            PlayerSwapComponent.KEY.get(killer).setCooldown(ModifiedGameConstants.swapCooldown);
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
