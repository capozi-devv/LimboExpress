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

public class CivilianInstinctComponent implements AutoSyncedComponent, ServerTickingComponent, ClientTickingComponent {
    public static final ComponentKey<CivilianInstinctComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(LimboExpress.MOD_ID, "civilian_instinct"), CivilianInstinctComponent.class);
    public final PlayerEntity player;
    private boolean hasCivilianInstinct = false;
    private int cooldownTicks = 0;
    private int activeTicks = 0;
    public CivilianInstinctComponent(PlayerEntity player) {
        this.player = player;
    }
    public void sync() {
        KEY.sync(this.player);
    }
    public void reset() {
        this.cooldownTicks = 0;
        this.activeTicks = 0;
        this.hasCivilianInstinct = false;
        if (player != null) {
            player.getItemCooldownManager().set(ItemInit.CIVILIAN_SIGHT, 0);
        }
        sync();
    }

    public boolean hasCivilianInstinct() {
        return KEY.get(this.player).hasCivilianInstinct;
    }
    public boolean setHasCivilianInstinct(boolean value) {
        return KEY.get(this.player).hasCivilianInstinct = value;
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
            this.hasCivilianInstinct = false;
            activeTicks = 0;
        }
        sync();
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.hasCivilianInstinct = tag.contains("civilian_instinct") ? tag.getBoolean("civilian_instinct") : false;
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putBoolean("civilian_instinct", hasCivilianInstinct);
    }
}
