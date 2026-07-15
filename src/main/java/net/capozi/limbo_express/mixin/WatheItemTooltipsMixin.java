package net.capozi.limbo_express.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.client.util.WatheItemTooltips;
import dev.doctor4t.wathe.index.WatheItems;
import net.capozi.limbo_express.foundation.MapEffectInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(WatheItemTooltips.class)
public class WatheItemTooltipsMixin {
    @WrapMethod(method = "addTooltipForItem")
    private static void limboExpress$addTooltip(Item item, @NotNull ItemStack itemStack, List<Text> tooltipList, Operation<Void> original) {
        if (WatheClient.isTrainMoving() && (WatheClient.gameComponent.getMapEffect().equals(MapEffectInit.LIMBO) || WatheClient.gameComponent.getMapEffect().equals(MapEffectInit.LIMBO_LITE))) {
            if (item == WatheItems.DERRINGER) {
                return;
            }
        }
        original.call(item, itemStack, tooltipList);
    }
}
