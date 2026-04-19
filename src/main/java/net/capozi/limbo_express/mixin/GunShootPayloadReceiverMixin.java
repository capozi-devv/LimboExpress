package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import dev.doctor4t.wathe.index.WatheItems;
import dev.doctor4t.wathe.index.tag.WatheItemTags;
import dev.doctor4t.wathe.util.GunDropPayload;
import dev.doctor4t.wathe.util.GunShootPayload;
import dev.doctor4t.wathe.util.Scheduler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GunShootPayload.Receiver.class)
public class GunShootPayloadReceiverMixin {
    @Inject(method = "receive(Ldev/doctor4t/wathe/util/GunShootPayload;Lnet/fabricmc/fabric/api/networking/v1/ServerPlayNetworking$Context;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private void limboExpress$recieve(GunShootPayload payload, ServerPlayNetworking.Context context, CallbackInfo ci) {
        PlayerEntity player = context.player();
        System.out.println(context.player().getName().getString());
        GameWorldComponent game = GameWorldComponent.KEY.get(player.getWorld());
        if (player.getMainHandStack().isOf(WatheItems.DERRINGER)) {
            if (game.isInnocent(player)) {
                Scheduler.schedule(() -> {
                    if (player.getInventory().contains((s) -> s.isIn(WatheItemTags.GUNS))) {
                        PlayerMoodComponent.KEY.get(player).setMood(0.0F);
                        player.getMainHandStack().decrement(1);
                    }
                }, 4);
            }
        }
    }
}
