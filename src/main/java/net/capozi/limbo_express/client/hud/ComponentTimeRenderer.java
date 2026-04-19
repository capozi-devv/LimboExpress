package net.capozi.limbo_express.client.hud;

import dev.doctor4t.wathe.client.gui.TimeRenderer;
import net.capozi.limbo_express.common.cca.CivilianInstinctComponent;
import net.capozi.limbo_express.common.cca.PlayerAnonymityComponent;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class ComponentTimeRenderer {
    public static TimeRenderer.TimeNumberRenderer view = new TimeRenderer.TimeNumberRenderer();
    public static TimeRenderer.TimeNumberRenderer view2 = new TimeRenderer.TimeNumberRenderer();
    public static float offsetDelta = 0f;
    public static float offsetDelta2 = 0f;

    public static void renderHud(TextRenderer renderer, @NotNull ClientPlayerEntity player, @NotNull DrawContext context, float delta) {
        PlayerAnonymityComponent anonymityComponent = PlayerAnonymityComponent.KEY.get(player);
        CivilianInstinctComponent instinctComponent = CivilianInstinctComponent.KEY.get(player);
        if (anonymityComponent.isAnonymous() || instinctComponent.hasCivilianInstinct()) {
            if (anonymityComponent.isAnonymous()) {
                int time = 3000 - PlayerAnonymityComponent.KEY.get(player).getActiveTicks();
                view.setTarget(time);
                if (Math.abs(view.getTarget() - time) > 10) offsetDelta = time > view.getTarget() ? .6f : -.6f;
                float r = offsetDelta > 0 ? 1f - offsetDelta : 1f;
                float g = offsetDelta < 0 ? 1f + offsetDelta : 1f;
                float b = 1f - Math.abs(offsetDelta);
                int colour = 0x960012;
                context.getMatrices().push();
                context.getMatrices().translate(24, 27, 0);
                view.render(renderer, context, 0, 0, colour, delta);
                context.getMatrices().pop();
            }
            if (instinctComponent.hasCivilianInstinct()) {
                int time2 = 1200 - CivilianInstinctComponent.KEY.get(player).getActiveTicks();
                view2.setTarget(time2);
                if (Math.abs(view2.getTarget() - time2) > 10) offsetDelta2 = time2 > view2.getTarget() ? .6f : -.6f;
                float r = offsetDelta2 > 0 ? 1f - offsetDelta2 : 1f;
                float g = offsetDelta2 < 0 ? 1f + offsetDelta2 : 1f;
                float b = 1f - Math.abs(offsetDelta2);
                int colour = 0xdead45;
                context.getMatrices().push();
                context.getMatrices().translate(24, anonymityComponent.isAnonymous() ? 38 : 27, 0);
                view2.render(renderer, context, 0, 0, colour, delta);
                context.getMatrices().pop();
            }
        }
    }

    public static void tick() {
        view.update();
        view2.update();
    }
}
