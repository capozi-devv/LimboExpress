package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.item.DerringerItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DerringerItem.class)
public class DerringerItemMixin {
    @Inject(method = "use", at = @At("TAIL"))
    private void limboExpress$derringerUse(@NotNull World world, @NotNull PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        GameWorldComponent gameWorldComponent = (GameWorldComponent)GameWorldComponent.KEY.get(user.getWorld());
        if (gameWorldComponent.getRole(user).getMoodType().equals(Role.MoodType.REAL)) {
            user.getStackInHand(hand).decrement(1);
            user.playSound(SoundEvents.ITEM_SHIELD_BREAK, 1f, 1f);
        }
    }
}
