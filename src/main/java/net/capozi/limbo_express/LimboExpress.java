package net.capozi.limbo_express;

import net.capozi.limbo_express.common.packet.clientbound.ConfigMatchS2CPacket;
import net.capozi.limbo_express.foundation.BlockInit;
import net.capozi.limbo_express.foundation.ItemInit;
import net.capozi.limbo_express.foundation.MapEffectInit;
import net.capozi.limbo_express.foundation.SoundInit;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class LimboExpress implements ModInitializer {
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
        LimboExpressConfig.init(LimboExpress.MOD_ID, LimboExpressConfig.class);
        PayloadTypeRegistry.playS2C().register(ConfigMatchS2CPacket.ID, ConfigMatchS2CPacket.CODEC);
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> ServerPlayNetworking.send(handler.player, new ConfigMatchS2CPacket(LimboExpressConfig.encode())));
    }
}
