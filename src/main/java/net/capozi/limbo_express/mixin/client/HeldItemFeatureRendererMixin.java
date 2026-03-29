package net.capozi.limbo_express.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.doctor4t.wathe.client.WatheClient;
import net.capozi.limbo_express.foundation.ItemInit;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HeldItemFeatureRenderer.class)
public class HeldItemFeatureRendererMixin {
    @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getMainHandStack()Lnet/minecraft/item/ItemStack;"))
    public ItemStack wathe$hideNoteAndRenderPsychosisItems(LivingEntity instance, Operation<ItemStack> original) {
        ItemStack ret = original.call(instance);
        if (WatheClient.moodComponent != null && WatheClient.moodComponent.isLowerThanMid()) { // make sure it's only the main hand item that's being replaced
            if (ret.isOf(ItemInit.INSANITY_PILLS)) {
                ret = new ItemStack(ItemInit.SANITY_PILLS);
            }
        }
        return ret;
    }
}
