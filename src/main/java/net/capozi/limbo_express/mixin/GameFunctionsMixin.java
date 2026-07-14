package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.cca.*;
import dev.doctor4t.wathe.game.GameFunctions;
import dev.doctor4t.wathe.index.WatheDataComponentTypes;
import dev.doctor4t.wathe.index.WatheItems;
import dev.doctor4t.wathe.index.WatheSounds;
import net.capozi.limbo_express.common.cca.CivilianInstinctComponent;
import net.capozi.limbo_express.common.cca.OverdoseComponent;
import net.capozi.limbo_express.common.cca.PlayerAnonymityComponent;
import net.capozi.limbo_express.common.cca.PlayerSwapComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.List;

@Mixin(GameFunctions.class)
public class GameFunctionsMixin {
    @Inject(method = "baseInitialize", at = @At("TAIL"))
    private static void limboExpress$baseInitialize(ServerWorld serverWorld, GameWorldComponent gameComponent, List<ServerPlayerEntity> players, CallbackInfo ci) {
        for (ServerPlayerEntity serverPlayerEntity : players) {
            serverPlayerEntity.getInventory().clear();
            OverdoseComponent.KEY.get(serverPlayerEntity).reset();
            CivilianInstinctComponent.KEY.get(serverPlayerEntity).reset();
            PlayerSwapComponent.KEY.get(serverPlayerEntity).reset();
            PlayerAnonymityComponent.KEY.get(serverPlayerEntity).reset();
            // remove item cooldowns
            HashSet<Item> copy = new HashSet<>(serverPlayerEntity.getItemCooldownManager().entries.keySet());
            for (Item item : copy) serverPlayerEntity.getItemCooldownManager().remove(item);
        }
        gameComponent.clearRoleMap();
        GameTimeComponent.KEY.get(serverWorld).reset();

        // reset map
        gameComponent.queueMapReset();

        // map effect initialize
        gameComponent.getMapEffect().initializeMapEffects(serverWorld, players);

        gameComponent.setGameStatus(GameWorldComponent.GameStatus.ACTIVE);
        gameComponent.sync();
    }
    @Inject(method = "finalizeGame", at = @At("TAIL"))
    private static void limboExpress$finalizeGame(ServerWorld world, CallbackInfo ci) {
        List<ServerPlayerEntity> players = world.getPlayers();
        for (PlayerEntity player : players) {
            if (GameFunctions.isPlayerAliveAndSurvival(player)) {
                player.getInventory().clear();
                OverdoseComponent.KEY.get(player).reset();
                CivilianInstinctComponent.KEY.get(player).reset();
                PlayerSwapComponent.KEY.get(player).reset();
                PlayerAnonymityComponent.KEY.get(player).reset();
                HashSet<Item> copy = new HashSet<>(player.getItemCooldownManager().entries.keySet());
                for (Item item : copy) player.getItemCooldownManager().remove(item);
            }
        }
    }
    @Inject(method = "killPlayer(Lnet/minecraft/entity/player/PlayerEntity;ZLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;set(Lnet/minecraft/component/ComponentType;Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.AFTER))
    private static void limboExpress$killPlayer(PlayerEntity victim, boolean spawnBody, PlayerEntity killer, Identifier deathReason, CallbackInfo ci) {
        if (!GameWorldComponent.KEY.get(killer.getWorld()).canUseKillerFeatures(killer)) {
            for(List<ItemStack> list : killer.getInventory().combinedInventory) {
                for(ItemStack stack : list) {
                    if (stack.isOf(WatheItems.DERRINGER) && stack == killer.getMainHandStack()) {
                        stack.set(WatheDataComponentTypes.USED, true);
                    }
                }
            }
        }
    }
}
