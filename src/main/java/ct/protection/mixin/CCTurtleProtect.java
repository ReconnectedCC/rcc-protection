package ct.protection.mixin;

import com.mojang.logging.LogUtils;
import io.github.flemmli97.flan.api.ClaimHandler;
import io.github.flemmli97.flan.api.data.IPermissionContainer;
import io.github.flemmli97.flan.api.data.IPermissionStorage;
import io.github.flemmli97.flan.api.permission.BuiltinPermission;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftDedicatedServer.class)
public class CCTurtleProtect {
	@Inject(at = @At("HEAD"), method = "isSpawnProtected", cancellable = true)
	private void InjectSpawn(ServerWorld world, BlockPos pos, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		//bad way of doing it, this uses non-api methods
		MinecraftServer server = world.getServer();
		IPermissionStorage storage = ClaimHandler.getPermissionStorage(world);
		IPermissionContainer claim = storage.getForPermissionCheck(pos);
		LogUtils.getLogger().info(String.valueOf(player.getUuid()));
		cir.setReturnValue(!claim.canInteract((ServerPlayerEntity) player, BuiltinPermission.BREAK, pos));
		 /*
		try {
		cir.setReturnValue(!ClaimHandler.canInteract((ServerPlayerEntity) player, pos, BuiltinPermission.BREAK));
		} catch (NullPointerException e) {
			// Do nothing because this is likely just the claim not existing
			cir.setReturnValue(false);
		}

		  */
	}
}