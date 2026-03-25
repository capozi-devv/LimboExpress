package net.capozi.limbo_express.foundation;

import dev.doctor4t.wathe.Wathe;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public class DataComponentTypeInit {
    private static <T> ComponentType<T> register(String name, @NotNull UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Wathe.id(name), builderOperator.apply(ComponentType.builder()).build());
    }
}
