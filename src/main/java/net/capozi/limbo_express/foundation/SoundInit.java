package net.capozi.limbo_express.foundation;

import net.capozi.limbo_express.LimboExpress;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundInit {
    public static void init() {}
    private static SoundEvent sound(String name) {
        Identifier id = Identifier.of(LimboExpress.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
    public static final SoundEvent SWAP = sound("swap");
}
