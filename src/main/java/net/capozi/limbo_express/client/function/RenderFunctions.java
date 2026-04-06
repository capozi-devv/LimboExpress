package net.capozi.limbo_express.client.function;

import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.entity.FirecrackerEntity;
import dev.doctor4t.wathe.entity.NoteEntity;
import dev.doctor4t.wathe.entity.PlayerBodyEntity;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import net.capozi.limbo_express.LimboExpress;
import net.capozi.limbo_express.common.cca.CivilianInstinctComponent;
import net.capozi.limbo_express.common.cca.PlayerAnonymityComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class RenderFunctions {
    public static final Identifier MOOD_HAPPY = LimboExpress.id("textures/gui/hud/mood_happy.png");
    public static final Identifier MOOD_MID = LimboExpress.id("textures/gui/hud/mood_mid.png");
    public static final Identifier MOOD_DEPRESSIVE = LimboExpress.id("textures/gui/hud/mood_depressive.png");
    public static int getInstinctHighlight(Entity target) {
        if (CivilianInstinctComponent.KEY.get(MinecraftClient.getInstance().player).hasCivilianInstinct()) {
            if (target instanceof PlayerBodyEntity) return 0x990000;
            if (target instanceof ItemEntity || target instanceof NoteEntity || target instanceof FirecrackerEntity) return 0xDB9D00;
        }
        return -1;
    }
}
