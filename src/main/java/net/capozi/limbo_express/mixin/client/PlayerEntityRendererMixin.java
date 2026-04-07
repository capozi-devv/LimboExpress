package net.capozi.limbo_express.mixin.client;

import dev.doctor4t.wathe.Wathe;
import dev.doctor4t.wathe.cca.PlayerPsychoComponent;
import net.capozi.limbo_express.LimboExpress;
import net.capozi.limbo_express.common.cca.PlayerAnonymityComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(method = "getTexture(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    private void limboExpress$getTexture(AbstractClientPlayerEntity abstractClientPlayerEntity, CallbackInfoReturnable<Identifier> cir) {
        if (PlayerAnonymityComponent.KEY.get(abstractClientPlayerEntity).isAnonymous()) {
            SkinTextures.Model model = abstractClientPlayerEntity.getSkinTextures().model();
            String suffix = (model == SkinTextures.Model.SLIM) ? "_thin" : "";
            Identifier texture = LimboExpress.id("textures/entity/anonymous" + suffix + ".png");
            cir.setReturnValue(texture);
        }
    }
    @ModifyVariable(method = "renderArm", at = @At("STORE"), ordinal = 0)
    private Identifier limboExpress$renderArm(Identifier skinTexture) {
        if (PlayerAnonymityComponent.KEY.get(MinecraftClient.getInstance().player).isAnonymous()) {
            SkinTextures.Model model = MinecraftClient.getInstance().player.getSkinTextures().model();
            String suffix = model == SkinTextures.Model.SLIM ? "_thin" : "";
            return LimboExpress.id("textures/entity/anonymous" + suffix + ".png");
        }
        return skinTexture;
    }
}
