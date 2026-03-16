package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.item.RevolverItem;
import dev.doctor4t.wathe.util.GunShootPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RevolverItem.class)
public class RevolverItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private static void limboExpress$revolverUse(@NotNull World world, @NotNull PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (world.isClient) {
            if (user.isSneaking()) {
                Entity target = (Entity) user;
                ClientPlayNetworking.send(new GunShootPayload(target.getId()));
                user.setPitch(user.getPitch() - 4.0F);
                RevolverItem.spawnHandParticle();
                cir.setReturnValue(TypedActionResult.consume(user.getStackInHand(hand)));
                cir.cancel();
            }
        }
    }
}
