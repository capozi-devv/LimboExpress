package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.util.TaskCompletePayload;
import net.capozi.limbo_express.common.ModifiedGameConstants;
import net.capozi.limbo_express.mixin.access.PlayerMoodComponentAccessor;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(PlayerMoodComponent.class)
public abstract class PlayerMoodComponentMixin {
    @Shadow
    public abstract float getMood();
    @Shadow @Final
    private PlayerEntity player;

    @Inject(method = "setMood", at = @At("HEAD"))
    void giveCoinsForMood(float mood, CallbackInfo ci) {
        GameWorldComponent gameWorldComponent = (GameWorldComponent)GameWorldComponent.KEY.get(player.getWorld());
        if (mood > getMood()) {
            if (gameWorldComponent.getRole(player) != null) {
                if (gameWorldComponent.getRole(player).getMoodType().equals(Role.MoodType.REAL)) {
                    PlayerShopComponent shopComponent = PlayerShopComponent.KEY.get(player);
                    shopComponent.addToBalance(Random.create().nextBetween(25, 50));
                }
            }
        }
    }
    @Inject(method = "serverTick", at = @At("TAIL"))
    private void limboExpress$serverTick(CallbackInfo ci) {
        PlayerMoodComponent mood = ((PlayerMoodComponent)(Object)this);
        PlayerMoodComponentAccessor accessor = ((PlayerMoodComponentAccessor)this);
        boolean shouldSyncb = false;
        if (!mood.tasks.isEmpty()) {
            mood.setMood(accessor.mood() - (float)mood.tasks.size() * ModifiedGameConstants.OVERDOSE_MOOD_DRAIN);
        }
        ArrayList<PlayerMoodComponent.Task> removals = new ArrayList<>();
        for (PlayerMoodComponent.TrainTask task : mood.tasks.values()) {
            task.tick(this.player);
            if (task.isFulfilled(this.player)) {
                removals.add(task.getType());
                mood.setMood(accessor.mood() - ModifiedGameConstants.MOOD_GAIN);
                if (this.player instanceof ServerPlayerEntity tempPlayer) {
                    ServerPlayNetworking.send(tempPlayer, new TaskCompletePayload());
                    shouldSyncb = true;
                }
            }
        }
        for (PlayerMoodComponent.Task task : removals) mood.tasks.remove(task);
        if (shouldSyncb) mood.sync();
    }
}
