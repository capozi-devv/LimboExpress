package net.capozi.limbo_express.mixin.access;

import dev.doctor4t.ratatouille.client.util.ambience.BackgroundAmbience;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BackgroundAmbience.class)
public interface BackgroundAmbienceAccessor {
    @Invoker("<init>")
    static BackgroundAmbience backgroundAmbience(SoundEvent soundEvent, SoundCategory soundCategory, BackgroundAmbience.PlayPredicate predicate, int fadeIn, int fadeOut) {
        throw new AssertionError();
    }
}
