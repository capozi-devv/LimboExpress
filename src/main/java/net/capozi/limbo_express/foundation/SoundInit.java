package net.capozi.limbo_express.foundation;

import devv.capozi.zip.common.index.Registrar;
import net.capozi.limbo_express.LimboExpress;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundInit {
    private static Registrar<SoundEvent> soundEventRegistrar = new Registrar<SoundEvent>(LimboExpress.MOD_ID, Registries.SOUND_EVENT);
    public static void init() {
        soundEventRegistrar.setRegistries(soundEventRegistrar.entries, soundEventRegistrar.registry_consumer);
    }
    public static final SoundEvent SWAP = soundEventRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "swap"), SoundEvent.of(Identifier.of("swap")));
}
