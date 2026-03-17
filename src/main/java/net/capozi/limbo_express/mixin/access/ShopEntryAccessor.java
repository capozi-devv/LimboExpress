package net.capozi.limbo_express.mixin.access;

import dev.doctor4t.wathe.util.ShopEntry;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ShopEntry.class)
public interface ShopEntryAccessor {
    @Accessor("stack")
    ItemStack getStack();
}
