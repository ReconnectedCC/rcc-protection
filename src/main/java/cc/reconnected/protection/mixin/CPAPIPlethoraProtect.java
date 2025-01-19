package cc.reconnected.protection.mixin;

import eu.pb4.common.protection.api.CommonProtection;
import io.sc3.plethora.gameplay.modules.laser.LaserEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LaserEntity.class)
public abstract class CPAPIPlethoraProtect {
    @Shadow protected abstract PlayerEntity getShooterPlayer();

    @Inject(at = @At("HEAD"), method = "canDamageEntity", cancellable = true)
    private void InjectDamageEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(CommonProtection.canDamageEntity(getShooterPlayer().getWorld(), entity,getShooterPlayer().getGameProfile(),getShooterPlayer()));
    }
    @Inject(at = @At("HEAD"), method = "canBreakBlock", cancellable = true)
    private void InjectBreakBlock(World world, BlockPos pos, boolean drop, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(CommonProtection.canBreakBlock(getShooterPlayer().getWorld(),pos,getShooterPlayer().getGameProfile(),getShooterPlayer()));
    }
    @Inject(at = @At("HEAD"), method = "tryBreakBlock", cancellable = true)
    private void InjectTryBreakBlock(World world, BlockPos pos, boolean drop, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(CommonProtection.canBreakBlock(getShooterPlayer().getWorld(),pos,getShooterPlayer().getGameProfile(),getShooterPlayer()));
    }
}
