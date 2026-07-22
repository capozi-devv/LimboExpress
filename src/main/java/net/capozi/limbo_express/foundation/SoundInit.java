package net.capozi.limbo_express.foundation;

import devv.capozi.zip.common.api.index.Registrar;
import net.capozi.limbo_express.LimboExpress;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public interface SoundInit {
    Registrar<SoundEvent> soundEventRegistrar = new Registrar<SoundEvent>((identifier, block) -> Registry.register(Registries.SOUND_EVENT, identifier, block));
    static void init() {
        soundEventRegistrar.setRegistries(soundEventRegistrar.entries, soundEventRegistrar.registry_consumer);
    }
    SoundEvent SWAP = soundEventRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "swap"), SoundEvent.of(Identifier.of(LimboExpress.MOD_ID, "swap")));
    SoundEvent INTERIOR_TRACK = soundEventRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "limbo_train_interior"), SoundEvent.of(Identifier.of(LimboExpress.MOD_ID, "limbo_train_interior")));
    SoundEvent EXTERIOR_TRACK = soundEventRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "limbo_train_exterior"), SoundEvent.of(Identifier.of(LimboExpress.MOD_ID, "limbo_train_exterior")));
}
