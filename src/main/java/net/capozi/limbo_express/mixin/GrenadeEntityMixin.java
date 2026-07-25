package net.capozi.limbo_express.mixin;

import dev.doctor4t.wathe.entity.GrenadeEntity;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import dev.doctor4t.wathe.index.WatheParticles;
import dev.doctor4t.wathe.index.WatheSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GrenadeEntity.class)
public abstract class GrenadeEntityMixin {

    @Inject(method = "onCollision", at = @At("HEAD"), cancellable = true)
    private void limboiniscrazy$onCollisionWithWallCheck(HitResult hitResult, CallbackInfo ci) {
        GrenadeEntity self = (GrenadeEntity) (Object) this;
        if (!(self.getWorld() instanceof ServerWorld world)) {
            return;
        }

        ci.cancel();

        world.playSound(null, self.getBlockPos(),
                WatheSounds.ITEM_GRENADE_EXPLODE, SoundCategory.PLAYERS, 5f,
                1f + self.getRandom().nextFloat() * .1f - .05f);

        world.spawnParticles(WatheParticles.BIG_EXPLOSION, self.getX(), self.getY() + .1f, self.getZ(), 1, 0, 0, 0, 0);
        world.spawnParticles(ParticleTypes.SMOKE, self.getX(), self.getY() + .1f, self.getZ(), 100, 0, 0, 0, .2f);
        world.spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM,
                        ((GrenadeEntityInvoker) self).limboExpress$invokeGetDefaultItem().getDefaultStack()),
                self.getX(), self.getY() + .1f, self.getZ(), 100, 0, 0, 0, 1f);

        Vec3d explosionPos = self.getPos();
        Box searchBox = self.getBoundingBox().expand(3f);

        for (ServerPlayerEntity player : world.getPlayers(serverPlayerEntity ->
                searchBox.contains(serverPlayerEntity.getPos()) &&
                        GameFunctions.isPlayerAliveAndSurvival(serverPlayerEntity))) {
            if (!limboiniscrazy$hasLineOfSight(world, explosionPos, player)) {
                continue;
            }
            GameFunctions.killPlayer(player, true,
                    self.getOwner() instanceof PlayerEntity playerEntity ? playerEntity : null,
                    GameConstants.DeathReasons.GRENADE);
        }

        self.discard();
    }

    private static boolean limboiniscrazy$hasLineOfSight(ServerWorld world, Vec3d from, ServerPlayerEntity player) {
        Box box = player.getBoundingBox();
        Vec3d[] targets = new Vec3d[]{
                new Vec3d(box.minX, box.minY, box.minZ),
                new Vec3d(box.minX, box.minY, box.maxZ),
                new Vec3d(box.maxX, box.minY, box.minZ),
                new Vec3d(box.maxX, box.minY, box.maxZ),
                new Vec3d(box.minX, box.maxY, box.minZ),
                new Vec3d(box.minX, box.maxY, box.maxZ),
                new Vec3d(box.maxX, box.maxY, box.minZ),
                new Vec3d(box.maxX, box.maxY, box.maxZ),
                player.getEyePos(),
                player.getPos().add(0, (box.maxY - box.minY) * 0.5, 0)
        };

        for (Vec3d target : targets) {
            RaycastContext ctx = new RaycastContext(
                    from,
                    target,
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    player
            );
            HitResult result = world.raycast(ctx);
            if (result.getType() == HitResult.Type.MISS) {
                return true;
            }
        }
        return false;
    }
}