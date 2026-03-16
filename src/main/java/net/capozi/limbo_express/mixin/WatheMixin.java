package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.Wathe;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.doctor4t.wathe.Wathe.isSupporter;

@Mixin(Wathe.class)
public class WatheMixin {
    @Inject(method = "isSupporter", at = @At("TAIL"))
    private static void limboExpress$isSupporter(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
    @Inject(method = "executeSupporterCommand", at = @At("HEAD"), cancellable = true)
    private static void executeSupporterCommand(ServerCommandSource source, Runnable runnable, CallbackInfoReturnable<Integer> cir) {
        ServerPlayerEntity player = source.getPlayer();
        if (player != null && player.getClass().equals(ServerPlayerEntity.class)) {
            runnable.run();
            cir.setReturnValue(1);
            System.out.println("Command should execute");
        } else {
            cir.setReturnValue(0);
        }
    }
}
