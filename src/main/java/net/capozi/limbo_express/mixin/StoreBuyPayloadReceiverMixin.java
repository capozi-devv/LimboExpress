package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.util.StoreBuyPayload;
import net.capozi.limbo_express.common.function.ModifiedGameFunctions;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StoreBuyPayload.Receiver.class)
public class StoreBuyPayloadReceiverMixin {
    @Inject(method = "receive(Ldev/doctor4t/wathe/util/StoreBuyPayload;Lnet/fabricmc/fabric/api/networking/v1/ServerPlayNetworking$Context;)V", at = @At("HEAD"), cancellable = true)
    private void limboExpress$receive(StoreBuyPayload payload, ServerPlayNetworking.Context context, CallbackInfo ci) {
        if (GameWorldComponent.KEY.get(context.player().getWorld()).isInnocent(context.player())) {
            //PlayerShopComponent.KEY.get(context.player()).tryBuy(payload.index());
            ModifiedGameFunctions.civilianTryBuy(payload.index(), PlayerShopComponent.KEY.get(context.player()));
            ci.cancel();
        } else if (GameWorldComponent.KEY.get(context.player().getWorld()).canUseKillerFeatures(context.player())) {
            ModifiedGameFunctions.modifiedKillerTryBuy(payload.index(), PlayerShopComponent.KEY.get(context.player()));
            ci.cancel();
        }
    }
}
