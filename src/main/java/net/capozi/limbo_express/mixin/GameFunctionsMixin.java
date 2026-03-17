package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.*;
import dev.doctor4t.wathe.game.GameFunctions;
import dev.doctor4t.wathe.index.WatheDataComponentTypes;
import dev.doctor4t.wathe.index.WatheItems;
import dev.doctor4t.wathe.index.WatheSounds;
import net.capozi.limbo_express.common.cca.OverdoseComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
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
    @Inject(method = "killPlayer(Lnet/minecraft/entity/player/PlayerEntity;ZLnet/minecraft/entity/player/PlayerEntity;)V", at = @At("TAIL"))
    private static void limboExpress$killPlayer(PlayerEntity victim, boolean spawnBody, PlayerEntity killer, CallbackInfo ci) {
        GameWorldComponent gameWorldComponent = GameWorldComponent.KEY.get(killer.getWorld());
        for (List<ItemStack> list : killer.getInventory().combinedInventory) {
            if (gameWorldComponent.getRole(killer).getMoodType().equals(Role.MoodType.REAL)) {
                for (ItemStack stack : list) {
                    Boolean used = stack.get(WatheDataComponentTypes.USED);
                    if (stack.isOf(WatheItems.DERRINGER) && used != null && used) {
                        stack.set(WatheDataComponentTypes.USED, true);
                        killer.playSoundToPlayer(WatheSounds.ITEM_DERRINGER_RELOAD, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    }
                }
            }
        }
    }
}
