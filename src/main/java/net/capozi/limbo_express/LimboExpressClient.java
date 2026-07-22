package net.capozi.limbo_express;

import dev.doctor4t.ratatouille.client.util.ambience.AmbienceUtil;
import dev.doctor4t.ratatouille.client.util.ambience.BackgroundAmbience;
import dev.doctor4t.ratatouille.util.TextUtils;
import dev.doctor4t.wathe.Wathe;
import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.index.WatheItems;
import eu.midnightdust.lib.config.MidnightConfig;
import net.capozi.limbo_express.client.hud.ComponentTimeRenderer;
import net.capozi.limbo_express.common.cca.WorldBackgroundMusicManagerComponent;
import net.capozi.limbo_express.common.game.function.ModifiedGameFunctions;
import net.capozi.limbo_express.foundation.BlockInit;
import net.capozi.limbo_express.foundation.ItemInit;
import net.capozi.limbo_express.foundation.MapEffectInit;
import net.capozi.limbo_express.foundation.SoundInit;
import net.capozi.limbo_express.mixin.access.BackgroundAmbienceAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

public class LimboExpressClient implements ClientModInitializer {
    public static WorldBackgroundMusicManagerComponent musicManager;
    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_WORLD_TICK.register((world) -> {
            musicManager = WorldBackgroundMusicManagerComponent.KEY.get(world);
        });
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, tooltipList) -> {
            ModifiedGameFunctions.addCooldownText(ItemInit.SWAP, tooltipList, itemStack);
            ModifiedGameFunctions.addCooldownText(ItemInit.CIVILIAN_SIGHT, tooltipList, itemStack);
            ModifiedGameFunctions.addCooldownText(ItemInit.ANONYMITY, tooltipList, itemStack);
            if (WatheClient.isTrainMoving() && (WatheClient.gameComponent.getMapEffect().equals(MapEffectInit.LIMBO) || WatheClient.gameComponent.getMapEffect().equals(MapEffectInit.LIMBO_LITE))) {
                if (itemStack.isOf(WatheItems.DERRINGER)) {
                    tooltipList.addAll(TextUtils.getWithLineBreaks(Text.translatable("tooltip.limbo_express.derringer")));
                }
            }
        });
        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> {
            ComponentTimeRenderer.tick();
        });
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SILVER_ORNAMENT, RenderLayer.getCutout());
        AmbienceUtil.registerBackgroundAmbience(BackgroundAmbienceAccessor.backgroundAmbience(SoundInit.INTERIOR_TRACK, SoundCategory.RECORDS, player -> {
            if (WatheClient.isTrainMoving() && !Wathe.isSkyVisibleAdjacent(player)) {
                if (musicManager.shouldMusicPlay && LimboExpressConfig.enableBackgroundMusic) return true;
            }
            return false;
        }, 20, 5));
        AmbienceUtil.registerBackgroundAmbience(BackgroundAmbienceAccessor.backgroundAmbience(SoundInit.EXTERIOR_TRACK, SoundCategory.RECORDS, player -> {
            if (WatheClient.isTrainMoving() && Wathe.isSkyVisibleAdjacent(player)) {
                if (musicManager.shouldMusicPlay && LimboExpressConfig.enableBackgroundMusic) return true;
            }
            return false;
        }, 20, 5));
    }
}
