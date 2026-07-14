package net.capozi.limbo_express;

import net.capozi.limbo_express.common.cca.*;
import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class LimboExpressComponents implements EntityComponentInitializer, WorldComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, OverdoseComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(OverdoseComponent::new);
        registry.beginRegistration(PlayerEntity.class, PlayerSwapComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(PlayerSwapComponent::new);
        registry.beginRegistration(PlayerEntity.class, CivilianInstinctComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(CivilianInstinctComponent::new);
        registry.beginRegistration(PlayerEntity.class, PlayerAnonymityComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(PlayerAnonymityComponent::new);
    }

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(WorldBackgroundMusicManagerComponent.KEY, WorldBackgroundMusicManagerComponent::new);
    }
}
