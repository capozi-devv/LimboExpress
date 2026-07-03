package net.capozi.limbo_express.foundation;

import devv.capozi.zip.common.index.Registrar;
import net.capozi.limbo_express.LimboExpress;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public interface SoundInit {
    Registrar<SoundEvent> soundEventRegistrar = new Registrar<SoundEvent>(LimboExpress.MOD_ID, Registries.SOUND_EVENT);
    static void init() {
        soundEventRegistrar.setRegistries(soundEventRegistrar.entries, soundEventRegistrar.registry_consumer);
    }
    SoundEvent SWAP = soundEventRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "swap"), SoundEvent.of(Identifier.of("swap")));
}
