package net.capozi.limbo_express;

import net.capozi.limbo_express.common.game.function.ModifiedGameFunctions;
import net.capozi.limbo_express.foundation.BlockInit;
import net.capozi.limbo_express.foundation.ItemInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.render.RenderLayer;

public class LimboExpressClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, tooltipList) -> {
            ModifiedGameFunctions.addCooldownText(ItemInit.SWAP, tooltipList, itemStack);
            ModifiedGameFunctions.addCooldownText(ItemInit.CIVILIAN_SIGHT, tooltipList, itemStack);
        });
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SILVER_ORNAMENT, RenderLayer.getCutout());
    }
}
