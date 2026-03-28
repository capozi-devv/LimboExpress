package net.capozi.limbo_express;

import net.capozi.limbo_express.common.cca.CivilianInstinctComponent;
import net.capozi.limbo_express.common.cca.OverdoseComponent;
import net.capozi.limbo_express.common.cca.PlayerSwapComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class LimboExpressComponents implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, OverdoseComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(OverdoseComponent::new);
        registry.beginRegistration(PlayerEntity.class, PlayerSwapComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(PlayerSwapComponent::new);
        registry.beginRegistration(PlayerEntity.class, CivilianInstinctComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(CivilianInstinctComponent::new);
    }
}
