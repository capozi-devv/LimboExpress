package net.capozi.limbo_express.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.capozi.limbo_express.common.cca.PlayerAnonymityComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(WatheClient.class)
public class WatheClientMixin {
    @WrapOperation(method = "getInstinctHighlight", at = @At(value = "INVOKE", target = "Ldev/doctor4t/wathe/cca/GameWorldComponent;isInnocent(Lnet/minecraft/entity/player/PlayerEntity;)Z"))
    private static boolean limboExpress$getInstinctHighlightCivilian(GameWorldComponent instance, PlayerEntity player, Operation<Boolean> original) {
        PlayerAnonymityComponent component = PlayerAnonymityComponent.KEY.get(player);
        return !component.isAnonymous() && original.call(instance, player);
    }
    @WrapOperation(method = "getInstinctHighlight", at = @At(value = "INVOKE", target = "Ldev/doctor4t/wathe/cca/GameWorldComponent;canUseKillerFeatures(Lnet/minecraft/entity/player/PlayerEntity;)Z"))
    private static boolean limboExpress$getInstinctHighlightKiller(GameWorldComponent instance, PlayerEntity player, Operation<Boolean> original) {
        PlayerAnonymityComponent component = PlayerAnonymityComponent.KEY.get(player);
        return !component.isAnonymous() && original.call(instance, player);
    }
    @ModifyArgs(method = "onInitializeClient", at = @At(value = "INVOKE", target = "Ldev/doctor4t/ratatouille/client/util/OptionLocker;overrideSoundCategoryVolume(Ljava/lang/String;D)V"))
    private void limboExpress$overrideSoundCategoryVolume(Args args) {
        if (args.get(0).equals("ambient")) {
            args.set(1, 0.15);
        }
        if (args.get(0).equals("record")) {
            args.set(1, 0.2);
        }
    }
}
