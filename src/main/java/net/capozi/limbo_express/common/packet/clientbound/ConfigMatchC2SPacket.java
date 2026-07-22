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

public record ConfigMatchC2SPacket(int configEncoding) implements CustomPayload {
    public static final CustomPayload.Id<ConfigMatchC2SPacket> ID = new CustomPayload.Id<>(Identifier.of(LimboExpress.MOD_ID, "config_match"));
    public static final PacketCodec<PacketByteBuf, ConfigMatchC2SPacket> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, ConfigMatchC2SPacket::configEncoding, ConfigMatchC2SPacket::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    private static final Text DISCONNECT_TEXT = Text.literal("The server you are attempting to connect to has ")
            .append(Text.literal("Limbo Express").formatted(Formatting.GOLD))
            .append(" installed, but your configuration file does not match the server's.\n\n")
            .append(Text.literal("Please make sure your configuration file matches the server's.\n").formatted(Formatting.RED))
            .append(Text.literal("Your configuration file is located at ").formatted(Formatting.RED))
            .append(Text.literal(PlatformFunctions.getConfigDirectory().resolve(LimboExpress.MOD_ID + ".json").toString()).formatted(Formatting.BLUE))
            .append(Text.literal(".\n\n").formatted(Formatting.RED))
            .append(Text.literal("This is not a bug, do not report it.").formatted(Formatting.DARK_RED, Formatting.BOLD));
    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<ConfigMatchC2SPacket> {
        @Override
        public void receive(ConfigMatchC2SPacket packet, ClientPlayNetworking.Context context) {
            if (packet.configEncoding != LimboExpressConfig.encode()) {
                context.player().networkHandler.getConnection().disconnect(DISCONNECT_TEXT);
            }
        }
    }
}
