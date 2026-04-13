package net.capozi.limbo_express.foundation;

import dev.doctor4t.wathe.Wathe;
import devv.capozi.zip.common.index.Registrar;
import net.capozi.limbo_express.LimboExpress;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public class DataComponentTypeInit {
    private static Registrar<ComponentType<?>> dataComponentTypeRegistrar = new Registrar<ComponentType<?>>(LimboExpress.MOD_ID, Registries.DATA_COMPONENT_TYPE);
}
