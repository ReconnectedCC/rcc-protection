package cc.reconnected.protection.mixin;

import cc.reconnected.protection.LedgerExtensionCfg;
import cc.reconnected.protection.LedgerRCCProtectionExtension;
import com.github.quiltservertools.ledger.Ledger;
import com.github.quiltservertools.ledger.actions.ActionType;
import com.github.quiltservertools.ledger.actionutils.ActionFactory;
import eu.pb4.common.protection.api.CommonProtection;
import io.sc3.plethora.gameplay.modules.laser.LaserEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.github.quiltservertools.ledger.utility.Sources;

@Mixin(LaserEntity.class)
public abstract class CPAPIPlethoraProtect {
    @Shadow
    protected abstract PlayerEntity getShooterPlayer();

    @Inject(at = @At("HEAD"), method = "canDamageEntity", cancellable = true)
    private void InjectDamageEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(CommonProtection.canDamageEntity(getShooterPlayer().getWorld(), entity, getShooterPlayer().getGameProfile(), getShooterPlayer()));
    }

    @Inject(at = @At("HEAD"), method = "canBreakBlock", cancellable = true)
    private void InjectBreakBlock(World world, BlockPos pos, boolean drop, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        boolean perm = CommonProtection.canBreakBlock(world, pos, player.getGameProfile(), player);
        cir.setReturnValue(perm);
        if (!perm) {
            cir.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "tryBreakBlock", cancellable = true)
    private void InjectTryBreakBlock(World world, BlockPos pos, boolean drop, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        boolean perm = CommonProtection.canBreakBlock(world, pos, player.getGameProfile(), player);
        if (perm) {
            ActionType laserBreak = ActionFactory.INSTANCE.blockBreakAction(world, pos, world.getBlockState(pos), player, world.getBlockEntity(pos), Sources.PLAYER);
            if (world.breakBlock(pos, drop, player)) {
                if (Ledger.config.get(LedgerExtensionCfg.logLaser)) {
                    Ledger.getApi().logAction(laserBreak);
                }
            }
        }
        cir.cancel();
    }
}
