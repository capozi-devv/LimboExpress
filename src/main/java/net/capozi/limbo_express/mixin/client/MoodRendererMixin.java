package net.capozi.limbo_express.mixin.client;

import dev.doctor4t.wathe.client.gui.MoodRenderer;
import net.capozi.limbo_express.common.function.RenderFunctions;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MoodRenderer.class)
public class MoodRendererMixin {
    @Inject(method = "renderCivilian", at = @At("HEAD"), cancellable = true)
    private static void limboExpress$renderCivilian(TextRenderer textRenderer, DrawContext context, float prevMood, CallbackInfo ci) {
        RenderFunctions.renderCivilian(textRenderer, context, prevMood);
        ci.cancel();
    }
}
