package net.capozi.limbo_express.common.cca;

import net.capozi.limbo_express.LimboExpress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PlayerSwapComponent implements AutoSyncedComponent, ClientTickingComponent, ServerTickingComponent {
    public static final ComponentKey<PlayerSwapComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(LimboExpress.MOD_ID, "playerSwap"), PlayerSwapComponent.class);
    private int cooldownTicks = 0;
    public final PlayerEntity player;
    public PlayerSwapComponent(PlayerEntity player) {
        reset();
        this.player = player;
    }
    public void sync() {
        KEY.sync(this.player);
    }
    public void reset() {
        this.cooldownTicks = 0;
    }
    public int getCooldown() {
        return cooldownTicks;
    }
    public int setCooldown(int cooldown) {
        return cooldownTicks = cooldown;
    }
    @Override
    public void clientTick() {
        sync();
    }
    @Override
    public void serverTick() {
        if (cooldownTicks > 0) {
            cooldownTicks--;
        }
        sync();
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.cooldownTicks = tag.contains("cooldownTicks", NbtElement.INT_TYPE) ? tag.getInt("cooldownTicks") : 0;
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putInt("cooldownTicks", cooldownTicks);
    }
}
