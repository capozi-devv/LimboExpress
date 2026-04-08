package net.capozi.limbo_express;

import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.PlayerConnectedEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;
import de.maxhenkel.voicechat.api.opus.OpusEncoder;
import net.capozi.limbo_express.client.voicechat.DownpitchVoice;

import java.util.*;

public class LimboExpressVoiceChatPlugin implements VoicechatPlugin {
    private static Map<UUID, VoicechatConnection> connectedPlayers = new HashMap();
    private static List<UUID> tempMutedPlayers = new ArrayList<>();
    private OpusEncoder encoder;
    private OpusDecoder decoder;
    private static VoicechatServerApi serverApi;
    @Override
    public String getPluginId() {
        return LimboExpress.MOD_ID;
    }
    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted, 100);
        registration.registerEvent(MicrophonePacketEvent.class, this::onAudioPacket);
        registration.registerEvent(PlayerConnectedEvent.class, this::onPlayerConnected);
    }
    public void onAudioPacket(MicrophonePacketEvent event) {
        downpitchVoice(event);
    }
    public void onServerStarted(VoicechatServerStartedEvent event) {
        serverApi = event.getVoicechat();
    }
    private void onPlayerConnected(PlayerConnectedEvent event) {
        UUID uuid = event.getConnection().getPlayer().getUuid();
        connectedPlayers.put(uuid, event.getConnection());
    }
    private void downpitchVoice(MicrophonePacketEvent event) {
        VoicechatConnection connection = event.getSenderConnection();
        if (connection == null) return;
        UUID senderUUID = connection.getPlayer().getUuid();
        if (!LimboExpress.downpitchVoicePlayers.contains(senderUUID)) {
            return;
        }
//        byte[] opusData = event.getPacket().getOpusEncodedData();
//        byte[] processedOpusData = processOpusAudioForAnon(senderUUID, opusData);
//        event.getPacket().setOpusEncodedData(processedOpusData);
    }
//    private byte[] processOpusAudioForAnon(UUID uuid, byte[] opusData) {
//        try {
//            short[] pcmData = decoder.decode(opusData);
//            if (pcmData == null) {
//                System.out.println("Failed to decode Opus data");
//                return opusData;
//            }
//            short[] processedPcm = DownpitchVoice.applyEffect(uuid, pcmData);
//
//            return encoder.encode(processedPcm);
//        } catch (Exception e) {
//            System.out.println("Error processing Opus audio");
//            return opusData;
//        }
//    }
}
