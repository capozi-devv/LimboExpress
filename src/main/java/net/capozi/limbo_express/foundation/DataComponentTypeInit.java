package net.capozi.limbo_express.foundation;

import com.mojang.serialization.Codec;
import dev.doctor4t.wathe.Wathe;
import net.capozi.limbo_express.LimboExpress;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public interface DataComponentTypeInit {
    static void init() {}
    private static <T> ComponentType<T> register(String name, @NotNull UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, LimboExpress.id(name), builderOperator.apply(ComponentType.builder()).build());
    }
    ComponentType<Integer> COIN_VALUE = register("coin_value", integerBuilder -> integerBuilder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));
}
