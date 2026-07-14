package net.capozi.limbo_express.common.cca;

import net.capozi.limbo_express.LimboExpress;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class WorldBackgroundMusicManagerComponent implements AutoSyncedComponent, ClientTickingComponent, ServerTickingComponent {
    public static final ComponentKey<WorldBackgroundMusicManagerComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(LimboExpress.MOD_ID, "music_manager"), WorldBackgroundMusicManagerComponent.class);
    public final World world;
    public boolean shouldMusicPlay = true;
    private int musicCooldownTicks = 0;
    public WorldBackgroundMusicManagerComponent(World world) {
        this.world = world;
    }
    public void sync() {
        KEY.sync(world);
    }
    @Override
    public void clientTick() {
        sync();
    }

    @Override
    public void serverTick() {
        if (!shouldMusicPlay) {
            musicCooldownTicks++;
            if (musicCooldownTicks >= 100) {
                shouldMusicPlay = true;
                musicCooldownTicks = 0;
            }
        }
        sync();
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        musicCooldownTicks = tag.getInt("musicCooldownTicks");
        shouldMusicPlay = tag.getBoolean("shouldMusicPlay");
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putInt("musicCooldownTicks", musicCooldownTicks);
        tag.putBoolean("shouldMusicPlay", shouldMusicPlay);
    }
}
