package net.capozi.limbo_express.mixin.access;

import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerMoodComponent.class)
public interface PlayerMoodComponentAccessor {
    @Accessor("mood")
    float mood();
}
