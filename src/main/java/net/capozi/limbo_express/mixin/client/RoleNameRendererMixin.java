package net.capozi.limbo_express.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.doctor4t.wathe.cca.PlayerPsychoComponent;
import dev.doctor4t.wathe.client.gui.RoleNameRenderer;
import dev.doctor4t.wathe.game.GameFunctions;
import net.capozi.limbo_express.common.cca.PlayerAnonymityComponent;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RoleNameRenderer.class)
public class RoleNameRendererMixin {
    @Shadow
    private static Text nametag = Text.empty();
    @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Lorg/ladysnake/cca/api/v3/component/ComponentKey;get(Ljava/lang/Object;)Lorg/ladysnake/cca/api/v3/component/Component;"), cancellable = true)
    private static void limboExpress$renderHud(TextRenderer renderer, ClientPlayerEntity player, DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        float range = GameFunctions.isPlayerSpectatingOrCreative(player) ? 8f : 2f;
        if (ProjectileUtil.getCollision(player, entity -> entity instanceof PlayerEntity, range) instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof PlayerEntity target) {
            nametag = target.getDisplayName();
            boolean shouldObfuscate = PlayerAnonymityComponent.KEY.get(target).isAnonymous();
            if (shouldObfuscate) {
                ci.cancel();
            }
        }
    }
}
