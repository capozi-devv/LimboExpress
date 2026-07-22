package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.capozi.limbo_express.common.ModifiedGameConstants;
import net.capozi.limbo_express.common.cca.OverdoseComponent;
import net.capozi.limbo_express.foundation.MapEffectInit;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoodComponent.class)
public abstract class PlayerMoodComponentMixin {
    @Shadow public abstract float getMood();
    @Shadow @Final private PlayerEntity player;
    @Inject(method = "setMood", at = @At("HEAD"))
    void giveCoinsForMood(float mood, CallbackInfo ci) {
        GameWorldComponent gameWorldComponent = GameWorldComponent.KEY.get(player.getWorld());
        if (gameWorldComponent.getMapEffect().equals(MapEffectInit.LIMBO) || gameWorldComponent.getMapEffect().equals(MapEffectInit.LIMBO_LITE)) {
            if (mood > getMood()) {
                if (gameWorldComponent.getRole(player) != null) {
                    if (gameWorldComponent.getRole(player).getMoodType().equals(Role.MoodType.REAL)) {
                        PlayerShopComponent shopComponent = PlayerShopComponent.KEY.get(player);
                        shopComponent.addToBalance(50);
                    }
                }
            }
        }
    }
    @ModifyArg(method = "serverTick", at = @At(value = "INVOKE", target = "Ldev/doctor4t/wathe/cca/PlayerMoodComponent;setMood(F)V"))
    private float limboExpress$serverTick(float mood) {
        OverdoseComponent component = OverdoseComponent.KEY.get(player);
        if (mood < getMood()) {
            return mood - component.getMoodDrainIncrease();
        } else if (mood > getMood()) {
            return mood - ModifiedGameConstants.MOOD_GAIN;
        }
        return mood;
    }
    @ModifyArg(method = "clientTick", at = @At(value = "INVOKE", target = "Ldev/doctor4t/wathe/cca/PlayerMoodComponent;setMood(F)V"))
    private float limboExpress$clientTick(float mood) {
        OverdoseComponent component = OverdoseComponent.KEY.get(player);
        if (mood < getMood()) {
            return mood - component.getMoodDrainIncrease();
        } else if (mood > getMood()) {
            return mood - ModifiedGameConstants.MOOD_GAIN;
        }
        return mood;
    }
}
