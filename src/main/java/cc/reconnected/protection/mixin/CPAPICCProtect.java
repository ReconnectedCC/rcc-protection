package cc.reconnected.protection.mixin;

import dan200.computercraft.shared.turtle.core.TurtlePlayer;
import eu.pb4.common.protection.api.CommonProtection;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TurtlePlayer.class)
public class CPAPICCProtect {
	@Shadow @Final private ServerPlayerEntity player;

	@Inject(at = @At("HEAD"), method = "isBlockProtected", cancellable = true, remap = false)
	private void InjectBlockProtected(ServerWorld level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
			cir.setReturnValue(!CommonProtection.canBreakBlock(level, pos, player.getGameProfile(),player));
	}
}