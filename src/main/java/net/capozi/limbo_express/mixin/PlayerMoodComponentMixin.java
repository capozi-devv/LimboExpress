package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import dev.doctor4t.wathe.util.TaskCompletePayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
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
    @Overwrite
    public void serverTick() {
        GameWorldComponent gameWorldComponent = GameWorldComponent.KEY.get(this.player.getWorld());
        if (!gameWorldComponent.isRunning() || !GameFunctions.isPlayerAliveAndSurvival(this.player)) return;
        if (!this.tasks.isEmpty()) this.setMood(this.mood - this.tasks.size() * GameConstants.MOOD_DRAIN);
        boolean shouldSync = false;
        this.nextTaskTimer--;
        if (this.nextTaskTimer <= 0) {
            PlayerMoodComponent.TrainTask task = this.generateTask();
            if (task != null) {
                this.tasks.put(task.getType(), task);
                this.timesGotten.putIfAbsent(task.getType(), 1);
                this.timesGotten.put(task.getType(), this.timesGotten.get(task.getType()) + 1);
            }
            this.nextTaskTimer = (int) (this.player.getRandom().nextFloat() * (GameConstants.MAX_TASK_COOLDOWN - GameConstants.MIN_TASK_COOLDOWN) + GameConstants.MIN_TASK_COOLDOWN);
            this.nextTaskTimer = Math.max(this.nextTaskTimer, 2);
            shouldSync = true;
        }
        ArrayList<PlayerMoodComponent.Task> removals = new ArrayList<>();
        for (PlayerMoodComponent.TrainTask task : this.tasks.values()) {
            task.tick(this.player);
            if (task.isFulfilled(this.player)) {
                removals.add(task.getType());
                this.setMood(this.mood + GameConstants.MOOD_GAIN);
                if (this.player instanceof ServerPlayerEntity tempPlayer)
                    ServerPlayNetworking.send(tempPlayer, new TaskCompletePayload());
                shouldSync = true;
            }
        }
        for (PlayerMoodComponent.Task task : removals) this.tasks.remove(task);
        if (shouldSync) this.sync();
    }
}
