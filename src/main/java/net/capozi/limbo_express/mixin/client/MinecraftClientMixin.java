package net.capozi.limbo_express.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.capozi.limbo_express.client.function.RenderFunctions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @ModifyReturnValue(method = "hasOutline", at = @At("RETURN"))
    public boolean wathe$hasInstinctOutline(boolean original, @Local(argsOnly = true) Entity entity) {
        if (RenderFunctions.getInstinctHighlight(entity) != -1) return true;
        return original;
    }
}
