package cc.reconnected.protection.mixin;

import io.github.flemmli97.flan.api.ClaimHandler;
import io.github.flemmli97.flan.api.permission.BuiltinPermission;
import io.sc3.plethora.gameplay.modules.laser.LaserEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LaserEntity.class)
public abstract class FlanPlethoraProtect {
    @Shadow protected abstract PlayerEntity getShooterPlayer();

    @Inject(at = @At("HEAD"), method = "canDamageEntity", cancellable = true)
    private void InjectDamageEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity.isPlayer()) cir.setReturnValue(!ClaimHandler.canInteract((ServerPlayerEntity) getShooterPlayer(), entity.getBlockPos(), BuiltinPermission.HURTPLAYER));
        else cir.setReturnValue(!ClaimHandler.canInteract((ServerPlayerEntity) getShooterPlayer(), entity.getBlockPos(), BuiltinPermission.HURTANIMAL));
    }
}
