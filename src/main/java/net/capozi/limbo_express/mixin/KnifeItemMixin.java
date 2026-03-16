package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.item.KnifeItem;
import dev.doctor4t.wathe.util.KnifeStabPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KnifeItem.class)
public class KnifeItemMixin {
    @Inject(method = "onStoppedUsing", at = @At("HEAD"), cancellable = true)
    private void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        if (!user.isSpectator()) {
            if (user.isSneaking()) {
                if (remainingUseTicks < ((KnifeItem)(Object)this).getMaxUseTime(stack, user) - 10 && user instanceof PlayerEntity) {
                    PlayerEntity attacker = (PlayerEntity)user;
                    if (world.isClient) {
                        Entity target = (Entity) user;
                        ClientPlayNetworking.send(new KnifeStabPayload(target.getId()));
                        ci.cancel();
                    }
                }
            }
        }
    }
}
