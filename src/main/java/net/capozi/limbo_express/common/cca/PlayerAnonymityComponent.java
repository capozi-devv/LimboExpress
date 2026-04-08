package net.capozi.limbo_express.common.cca;

import net.capozi.limbo_express.LimboExpress;
import net.capozi.limbo_express.foundation.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PlayerAnonymityComponent implements AutoSyncedComponent, ClientTickingComponent, ServerTickingComponent {
    public static final ComponentKey<PlayerAnonymityComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(LimboExpress.MOD_ID, "anonymity"), PlayerAnonymityComponent.class);
    public final PlayerEntity player;
    private boolean isAnonymous = false;
    private int cooldownTicks = 0;
    private int activeTicks = 0;
    public PlayerAnonymityComponent(PlayerEntity player) {
        this.player = player;
    }
    public void sync() {
        KEY.sync(this.player);
    }
    public void reset() {
        this.cooldownTicks = 0;
        this.activeTicks = 0;
        this.isAnonymous = false;
        if (player != null) {
            player.getItemCooldownManager().set(ItemInit.CIVILIAN_SIGHT, 0);
        }
        sync();
    }
    public boolean isAnonymous() {
        return KEY.get(this.player).isAnonymous;
    }
    public boolean setAnonymous(boolean value) {
        return KEY.get(this.player).isAnonymous = value;
    }
    public int getCooldown() {
        return cooldownTicks;
    }
    public int setCooldown(int cooldown) {
        return cooldownTicks = cooldown;
    }

    public int getActiveTicks() {
        return activeTicks;
    }

    @Override
    public void clientTick() {
        sync();
    }
    @Override
    public void serverTick() {
        sync();
        if (cooldownTicks > 2400) activeTicks++;
        if (cooldownTicks > 0) cooldownTicks--;
        if (activeTicks >= 1200) {
            this.isAnonymous = false;
            activeTicks = 0;
        }
        sync();
    }
    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.isAnonymous = tag.contains("anonymous") ? tag.getBoolean("anonymous") : false;
    }
    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putBoolean("anonymous", isAnonymous);
    }
}
