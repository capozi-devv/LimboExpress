package net.capozi.limbo_express.client.function;

import dev.doctor4t.wathe.entity.FirecrackerEntity;
import dev.doctor4t.wathe.entity.NoteEntity;
import dev.doctor4t.wathe.entity.PlayerBodyEntity;
import net.capozi.limbo_express.LimboExpress;
import net.capozi.limbo_express.common.cca.CivilianInstinctComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.Identifier;

public class RenderFunctions {
    public static final Identifier MOOD_HAPPY = LimboExpress.id( "hud/mood_happy");
    public static final Identifier MOOD_MID = LimboExpress.id( "hud/mood_mid");
    public static final Identifier MOOD_DEPRESSIVE = LimboExpress.id("hud/mood_depressive");
    public static int getInstinctHighlight(Entity target) {
        if (CivilianInstinctComponent.KEY.get(MinecraftClient.getInstance().player).hasCivilianInstinct()) {
            if (target instanceof PlayerBodyEntity) return 0x990000;
            if (target instanceof ItemEntity || target instanceof NoteEntity || target instanceof FirecrackerEntity) return 0xDB9D00;
        }
        return -1;
    }
}
