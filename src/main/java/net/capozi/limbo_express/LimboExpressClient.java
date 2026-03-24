package net.capozi.limbo_express;

import net.capozi.limbo_express.common.function.ModifiedGameFunctions;
import net.capozi.limbo_express.foundation.ItemInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class LimboExpressClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, tooltipList) -> {
            ModifiedGameFunctions.addCooldownText(ItemInit.SWAP, tooltipList, itemStack);
        });
    }
}
