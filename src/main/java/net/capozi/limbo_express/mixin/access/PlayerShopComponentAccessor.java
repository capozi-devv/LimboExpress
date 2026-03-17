package net.capozi.limbo_express.mixin.access;

import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerShopComponent.class)
public interface PlayerShopComponentAccessor {
    @Accessor("player")
    PlayerEntity player();
}
