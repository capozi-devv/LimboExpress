package net.capozi.limbo_express.mixin.client;

import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.client.gui.*;
import net.capozi.limbo_express.client.hud.ComponentTimeRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;
    @Inject(method = "renderMainHud", at = @At("TAIL"))
    private void limboExpress$renderHud(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (WatheClient.trainComponent != null && WatheClient.trainComponent.hasHud()) {
            ClientPlayerEntity player = this.client.player;
            if (player == null) return;
            TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
            ComponentTimeRenderer.renderHud(renderer, player, context, tickCounter.getTickDelta(true));
        }
    }
}
