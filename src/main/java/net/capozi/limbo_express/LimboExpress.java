package net.capozi.limbo_express;

import net.capozi.limbo_express.foundation.BlockInit;
import net.capozi.limbo_express.foundation.ItemInit;
import net.capozi.limbo_express.foundation.MapEffectInit;
import net.capozi.limbo_express.foundation.SoundInit;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LimboExpress implements ModInitializer {
    public static final List<UUID> downpitchVoicePlayers = new ArrayList<>();
    public static final String MOD_ID = "limbo_express";
    public static @NotNull Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }
    @Override
    public void onInitialize() {
        ItemInit.init();
        BlockInit.init();
        SoundInit.init();
        MapEffectInit.init();
    }
}
