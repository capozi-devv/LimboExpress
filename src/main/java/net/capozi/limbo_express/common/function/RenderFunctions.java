package net.capozi.limbo_express.common.function;

import dev.doctor4t.wathe.Wathe;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.client.gui.MoodRenderer;
import dev.doctor4t.wathe.game.GameConstants;
import net.capozi.limbo_express.LimboExpress;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RenderFunctions {
    public static final Identifier ARROW_UP = Wathe.id("hud/arrow_up");
    public static final Identifier ARROW_DOWN = Wathe.id("hud/arrow_down");
    public static final Identifier MOOD_HAPPY = Identifier.of(LimboExpress.MOD_ID, "hud/mood_happy");
    public static final Identifier MOOD_MID = Identifier.of(LimboExpress.MOD_ID, "hud/mood_mid");
    public static final Identifier MOOD_DEPRESSIVE = Identifier.of(LimboExpress.MOD_ID, "hud/mood_depressive");
    public static float arrowProgress = 1f;
    public static float moodRender = 0f;
    public static float moodOffset = 0f;
    public static float moodTextWidth = 0f;
    public static float moodAlpha = 0f;
    public static void renderCivilian(@NotNull TextRenderer textRenderer, @NotNull DrawContext context, float prevMood) {
        context.getMatrices().push();
        context.getMatrices().translate(0, 3 * moodOffset, 0);
        Identifier mood = MOOD_HAPPY;
        if (moodRender < GameConstants.DEPRESSIVE_MOOD_THRESHOLD) {
            mood = MOOD_DEPRESSIVE;
        } else if (moodRender < GameConstants.MID_MOOD_THRESHOLD) {
            mood = MOOD_MID;
        }
        if (arrowProgress < 0.1f) {
            if (prevMood >= GameConstants.DEPRESSIVE_MOOD_THRESHOLD && moodRender < GameConstants.DEPRESSIVE_MOOD_THRESHOLD) {
                arrowProgress = -1f;
            } else if (prevMood >= GameConstants.MID_MOOD_THRESHOLD && moodRender < GameConstants.MID_MOOD_THRESHOLD) {
                arrowProgress = -1f;
            }
        }
        context.drawGuiTexture(mood, 5, 6, 14, 17);
        if (Math.abs(arrowProgress) > 0.01f) {
            boolean up = arrowProgress > 0;
            Identifier arrow = up ? ARROW_UP : ARROW_DOWN;
            context.getMatrices().push();
            if (!up) context.getMatrices().translate(0, 4, 0);
            context.getMatrices().translate(0, arrowProgress * 4, 0);
            context.drawSprite(7, 6, 0, 10, 13, context.guiAtlasManager.getSprite(arrow), 1f, 1f, 1f, (float) Math.sin(Math.abs(arrowProgress) * Math.PI));
            context.getMatrices().pop();
        }
        context.getMatrices().pop();
        context.getMatrices().push();
        context.getMatrices().translate(0, 10 * moodOffset, 0);
        context.getMatrices().translate(26, 8 + textRenderer.fontHeight, 0);
        context.getMatrices().scale((moodTextWidth - 8) * moodRender, 1, 1);
        context.fill(0, 0, 1, 1, MathHelper.hsvToRgb(moodRender / 3.0F, 1.0F, 1.0F) | ((int) (moodAlpha * 255) << 24));
        context.getMatrices().pop();
    }
}
