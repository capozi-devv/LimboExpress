package net.capozi.limbo_express;

import net.capozi.limbo_express.common.function.ShopFunctions;
import net.capozi.limbo_express.foundation.ItemInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public class LimboExpressClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, tooltipList) -> {
            ShopFunctions.addCooldownText(ItemInit.SWAP, tooltipList, itemStack);
        });
    }
}
