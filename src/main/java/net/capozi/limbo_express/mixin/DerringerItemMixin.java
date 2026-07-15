package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.item.DerringerItem;
import net.capozi.limbo_express.foundation.MapEffectInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DerringerItem.class)
public class DerringerItemMixin {
    @ModifyArg(method = "appendTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;translatable(Ljava/lang/String;)Lnet/minecraft/text/MutableText;"))
    private String limboExpress$appendTooltip(String key) {
        if (WatheClient.isTrainMoving() && (WatheClient.gameComponent.getMapEffect().equals(MapEffectInit.LIMBO) || WatheClient.gameComponent.getMapEffect().equals(MapEffectInit.LIMBO_LITE))) {
            return "tooltip.limbo_express.derringer.used";
        }
        return key;
    }
}
