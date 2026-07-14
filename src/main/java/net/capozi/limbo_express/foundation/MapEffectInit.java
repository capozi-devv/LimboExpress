package net.capozi.limbo_express.foundation;

import dev.doctor4t.wathe.api.MapEffect;
import dev.doctor4t.wathe.api.WatheMapEffects;
import dev.doctor4t.wathe.game.mapeffect.HarpyExpressNightTrainMapEffect;
import net.capozi.limbo_express.LimboExpress;
import net.capozi.limbo_express.common.game.mapeffect.LimboMapEffect;
import net.minecraft.util.Identifier;

public interface MapEffectInit {
    static void init() {}
    MapEffect LIMBO = WatheMapEffects.registerMapEffect(Identifier.of(LimboExpress.MOD_ID, "limbo_express"), new LimboMapEffect(Identifier.of(LimboExpress.MOD_ID, "limbo_express")));
    MapEffect LIMBO_LITE = WatheMapEffects.registerMapEffect(Identifier.of(LimboExpress.MOD_ID, "limbo_express_lite"), new HarpyExpressNightTrainMapEffect(Identifier.of(LimboExpress.MOD_ID, "limbo_express_lite")));
}
