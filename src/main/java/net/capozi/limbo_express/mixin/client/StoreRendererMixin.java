package net.capozi.limbo_express.mixin.client;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.client.gui.StoreRenderer;
import net.capozi.limbo_express.foundation.MapEffectInit;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StoreRenderer.class)
public class StoreRendererMixin {
    @Shadow
    public static StoreRenderer.MoneyNumberRenderer view;
    @Shadow
    public static float offsetDelta;
    @Inject(method = "renderHud", at = @At("HEAD"), cancellable = true)
    private static void limboExpress$renderHud(TextRenderer renderer, ClientPlayerEntity player, DrawContext context, float delta, CallbackInfo ci) {
        if (GameWorldComponent.KEY.get(player.getWorld()).getMapEffect().equals(MapEffectInit.LIMBO) || GameWorldComponent.KEY.get(player.getWorld()).getMapEffect().equals(MapEffectInit.LIMBO_LITE)) {
            if (GameWorldComponent.KEY.get(player.getWorld()).isInnocent(player)) {
                int balance = PlayerShopComponent.KEY.get(player).balance;
                if (view.getTarget() != balance) {
                    offsetDelta = balance > view.getTarget() ? .6f : -.6f;
                    view.setTarget(balance);
                }
                float r = offsetDelta > 0 ? 1f - offsetDelta : 1f;
                float g = offsetDelta < 0 ? 1f + offsetDelta : 1f;
                float b = 1f - Math.abs(offsetDelta);
                int colour = MathHelper.packRgb(r, g, b) | 0xFF000000;
                context.getMatrices().push();
                context.getMatrices().translate(context.getScaledWindowWidth() - 12, 6, 0);
                view.render(renderer, context, 0, 0, colour, delta);
                context.getMatrices().pop();
                offsetDelta = MathHelper.lerp(delta / 16, offsetDelta, 0f);
            }
        }
    }
}
