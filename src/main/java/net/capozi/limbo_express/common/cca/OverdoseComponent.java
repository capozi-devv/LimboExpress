package net.capozi.limbo_express.common.cca;

import dev.doctor4t.wathe.Wathe;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import net.capozi.limbo_express.LimboExpress;
import net.capozi.limbo_express.common.ModifiedGameConstants;
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

public class OverdoseComponent implements AutoSyncedComponent, ServerTickingComponent, ClientTickingComponent {
    public static final ComponentKey<OverdoseComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(LimboExpress.MOD_ID, "overdose"), OverdoseComponent.class);
    public Integer timesPillsUsed = 0;
    private final PlayerEntity player;
    private float moodDrainIncrease = 0;
    public float getMoodDrainIncrease() {
       return moodDrainIncrease;
    }
    public OverdoseComponent(PlayerEntity player) {
        this.player = player;
    }
    public void reset() {
        this.timesPillsUsed = 0;
        this.moodDrainIncrease = 0;
    }
    public void sync() {
        KEY.sync(this.player);
    }
    @Override
    public void clientTick() {
        sync();
    }
    @Override
    public void serverTick() {
        if (timesPillsUsed >= 15){
            moodDrainIncrease = ModifiedGameConstants.OVERDOSE_MOOD_DRAIN;
            return;
        }
        if (timesPillsUsed >= 10) {
            moodDrainIncrease = ModifiedGameConstants.OVERDOSE_MOOD_DRAIN * 0.66f;
            return;
        }
        if (timesPillsUsed >= 5) {
            moodDrainIncrease = ModifiedGameConstants.OVERDOSE_MOOD_DRAIN * 0.33f;
        }
        sync();
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.timesPillsUsed = tag.contains("pillsTaken", NbtElement.INT_TYPE) ? tag.getInt("pillsTaken") : 0;
        this.moodDrainIncrease = tag.contains("moodDrainIncrease", NbtElement.FLOAT_TYPE) ? tag.getFloat("moodDrainIncrease") : 0f;
    }
    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup wrapperLookup) {
        tag.putInt("pillsTaken", timesPillsUsed);
        tag.putFloat("moodDrainIncrease", moodDrainIncrease);
    }
}
