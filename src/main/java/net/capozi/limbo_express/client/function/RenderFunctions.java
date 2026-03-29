package net.capozi.limbo_express.client.function;

import dev.doctor4t.wathe.Wathe;
import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.entity.FirecrackerEntity;
import dev.doctor4t.wathe.entity.NoteEntity;
import dev.doctor4t.wathe.entity.PlayerBodyEntity;
import net.capozi.limbo_express.LimboExpress;
import net.capozi.limbo_express.common.cca.CivilianInstinctComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RenderFunctions {
    public static final Identifier ARROW_UP = Wathe.id("hud/arrow_up");
    public static final Identifier ARROW_DOWN = Wathe.id("hud/arrow_down");
    public static final Identifier MOOD_HAPPY = LimboExpress.id( "hud/mood_happy");
    public static final Identifier MOOD_MID = LimboExpress.id( "hud/mood_mid");
    public static final Identifier MOOD_DEPRESSIVE = LimboExpress.id("hud/mood_depressive");
    public static float arrowProgress = 1.0F;
    public static float moodRender = 0.0F;
    public static float moodOffset = 0.0F;
    public static float moodTextWidth = 0.0F;
    public static float moodAlpha = 0.0F;
    public static void renderCivilian(@NotNull TextRenderer textRenderer, @NotNull DrawContext context, float prevMood) {
        context.getMatrices().push();
        context.getMatrices().translate(0.0F, 3.0F * moodOffset, 0.0F);
        Identifier mood = MOOD_HAPPY;
        if (moodRender < 0.2F) {
            mood = MOOD_DEPRESSIVE;
        } else if (moodRender < 0.55F) {
            mood = MOOD_MID;
        }

        if (arrowProgress < 0.1F) {
            if (prevMood >= 0.2F && moodRender < 0.2F) {
                arrowProgress = -1.0F;
            } else if (prevMood >= 0.55F && moodRender < 0.55F) {
                arrowProgress = -1.0F;
            }
        }

        context.drawGuiTexture(mood, 5, 6, 14, 17);
        if (Math.abs(arrowProgress) > 0.01F) {
            boolean up = arrowProgress > 0.0F;
            Identifier arrow = up ? ARROW_UP : ARROW_DOWN;
            context.getMatrices().push();
            if (!up) {
                context.getMatrices().translate(0.0F, 4.0F, 0.0F);
            }

            context.getMatrices().translate(0.0F, arrowProgress * 4.0F, 0.0F);
            context.drawSprite(7, 6, 0, 10, 13, context.guiAtlasManager.getSprite(arrow), 1.0F, 1.0F, 1.0F, (float)Math.sin((double)Math.abs(arrowProgress) * Math.PI));
            context.getMatrices().pop();
        }

        context.getMatrices().pop();
        context.getMatrices().push();
        context.getMatrices().translate(0.0F, 10.0F * moodOffset, 0.0F);
        MatrixStack var10000 = context.getMatrices();
        Objects.requireNonNull(textRenderer);
        var10000.translate(26.0F, (float)(8 + 9), 0.0F);
        context.getMatrices().scale((moodTextWidth - 8.0F) * moodRender, 1.0F, 1.0F);
        context.fill(0, 0, 1, 1, MathHelper.hsvToRgb(moodRender / 3.0F, 1.0F, 1.0F) | (int)(moodAlpha * 255.0F) << 24);
        context.getMatrices().pop();
    }
    public static int getInstinctHighlight(Entity target) {
        if (CivilianInstinctComponent.KEY.get(MinecraftClient.getInstance().player).hasCivilianInstinct()) {
            if (target instanceof PlayerBodyEntity) return 0x990000;
            if (target instanceof ItemEntity || target instanceof NoteEntity || target instanceof FirecrackerEntity) return 0xDB9D00;
        }
        return -1;
    }
}
