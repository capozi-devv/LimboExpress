package net.capozi.limbo_express.common.packet.clientbound;

import eu.midnightdust.lib.util.PlatformFunctions;
import net.capozi.limbo_express.LimboExpress;
import net.capozi.limbo_express.LimboExpressConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public record ConfigMatchS2CPacket(int configEncoding) implements CustomPayload {
    public static final CustomPayload.Id<ConfigMatchS2CPacket> ID = new CustomPayload.Id<>(Identifier.of(LimboExpress.MOD_ID, "config_match"));
    public static final PacketCodec<PacketByteBuf, ConfigMatchS2CPacket> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, ConfigMatchS2CPacket::configEncoding, ConfigMatchS2CPacket::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    private static final Text DISCONNECT_TEXT = Text.literal("LIMBO EXPRESS /// ERROR\n\n").formatted(Formatting.BOLD)
            .append(Text.literal("The server you are attempting to connect to has ")).formatted(Formatting.RESET)
            .append(Text.literal("LIMBO EXPRESS").formatted(Formatting.GOLD))
            .append(" installed, but your configuration file does not match the server's\n\n")
            .append(Text.literal("Your configuration file is located at ").formatted(Formatting.GREEN))
            .append(Text.literal(PlatformFunctions.getConfigDirectory().resolve(LimboExpress.MOD_ID + ".json").toString()).formatted(Formatting.BLUE))
            .append(Text.literal("\n\n"))
            .append(Text.literal("This is not a bug, do not report it").formatted(Formatting.DARK_RED, Formatting.BOLD));
    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<ConfigMatchS2CPacket> {
        @Override
        public void receive(ConfigMatchS2CPacket packet, ClientPlayNetworking.Context context) {
            if (LimboExpressConfig.encode() != packet.configEncoding()) {
                context.player().networkHandler.getConnection().disconnect(DISCONNECT_TEXT);
            }
        }
    }
}
