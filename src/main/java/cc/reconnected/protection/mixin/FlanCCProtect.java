package cc.reconnected.protection.mixin;

import io.github.flemmli97.flan.api.ClaimHandler;
import io.github.flemmli97.flan.api.permission.BuiltinPermission;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftDedicatedServer.class)
public class FlanCCProtect {
	@Inject(at = @At("HEAD"), method = "isSpawnProtected", cancellable = true)
	private void InjectSpawn(ServerWorld world, BlockPos pos, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(!ClaimHandler.canInteract((ServerPlayerEntity) player, pos, BuiltinPermission.BREAK));
	}
}