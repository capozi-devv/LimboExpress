package net.capozi.limbo_express.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.doctor4t.wathe.api.WatheRoles;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.client.gui.MoodRenderer;
import net.capozi.limbo_express.client.function.RenderFunctions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MoodRenderer.class)
public class MoodRendererMixin {
    @Shadow
    public static float moodRender = 0f;
    @Shadow
    public static float moodOffset = 0f;
    @Shadow
    public static float moodTextWidth = 0f;
    @Shadow
    public static float moodAlpha = 0f;
    @Redirect(method = "renderHud", at = @At(value = "INVOKE", target = "Ldev/doctor4t/wathe/cca/PlayerMoodComponent;getMood()F", ordinal = 0))
    private static float limboExpress$getMood(PlayerMoodComponent instance, @Local(name = "gameWorldComponent") GameWorldComponent game) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && (game.isRole(player, WatheRoles.VIGILANTE) || game.isRole(player, WatheRoles.CIVILIAN))) return instance.getMood();
        return instance.getMood();
    }

    @Redirect(method = "renderHud", at = @At(value = "INVOKE", target = "Ldev/doctor4t/wathe/client/gui/MoodRenderer;renderCivilian(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/client/gui/DrawContext;F)V"))
    private static void limboExpress$renderHud(TextRenderer textRenderer, DrawContext context, float prevMood, @Local(name = "gameWorldComponent") GameWorldComponent game, @Local(name = "player") PlayerEntity player) {
        if ((game.isRole(player, WatheRoles.VIGILANTE) || game.isRole(player, WatheRoles.CIVILIAN))) {
            Identifier mood = RenderFunctions.MOOD_HAPPY;
            if (moodRender < 0.2F) {
                mood = RenderFunctions.MOOD_DEPRESSIVE;
            } else if (moodRender < 0.55F) {
                mood = RenderFunctions.MOOD_MID;
            }
            context.getMatrices().push();
            context.getMatrices().translate(0, 3 * moodOffset, 0);
            context.drawTexture(mood, 5, 6, 0, 0, 14, 17, 14, 17);
            context.getMatrices().pop();
            context.getMatrices().push();
            context.getMatrices().translate(0, 10 * moodOffset, 0);
            context.getMatrices().translate(26, 8 + textRenderer.fontHeight, 0);
            context.getMatrices().scale((moodTextWidth - 8) * moodRender, 1, 1);
            context.fill(0, 0, 1, 1, MathHelper.hsvToRgb(moodRender / 3.0F, 1.0F, 1.0F) | (int)(moodAlpha * 255.0F) << 24);
            context.getMatrices().pop();
        } else {
            throw new AssertionError();
        }
    }
}
