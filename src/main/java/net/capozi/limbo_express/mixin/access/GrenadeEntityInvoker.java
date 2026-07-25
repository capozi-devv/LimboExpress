package net.capozi.limbo_express.mixin.access;

import dev.doctor4t.wathe.entity.GrenadeEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GrenadeEntity.class)
public interface GrenadeEntityInvoker {
    @Invoker("getDefaultItem")
    Item limboExpress$invokeGetDefaultItem();
}