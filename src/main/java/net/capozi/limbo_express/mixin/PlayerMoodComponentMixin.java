package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameConstants;
import net.capozi.limbo_express.common.ModifiedGameConstants;
import net.capozi.limbo_express.foundation.MapEffectInit;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoodComponent.class)
public abstract class PlayerMoodComponentMixin {
    @Shadow public abstract float getMood();
    @Shadow @Final private PlayerEntity player;
    @Inject(method = "setMood", at = @At("HEAD"))
    void giveCoinsForMood(float mood, CallbackInfo ci) {
        GameWorldComponent gameWorldComponent = (GameWorldComponent)GameWorldComponent.KEY.get(player.getWorld());
        if (gameWorldComponent.getMapEffect().equals(MapEffectInit.LIMBO)) {
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
}
