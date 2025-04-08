package cc.reconnected.protection.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dan200.computercraft.core.apis.HTTPAPI;
import dan200.computercraft.core.apis.IAPIEnvironment;
import io.netty.handler.codec.http.HttpHeaders;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(HTTPAPI.class)
public class HTTPCCHeaderAdd {
    @Shadow(remap = false) @Final private IAPIEnvironment apiEnvironment;

    @Inject(at = @At("RETURN"), method = "getHeaders", remap = false)
    private void InjectHeaders(Map<?, ?> headerTable, CallbackInfoReturnable<HttpHeaders> cir, @Local HttpHeaders headers) {
        headers.set("X-CC-ID", apiEnvironment.getComputerID());
        headers.set("X-CC-SRV", "play.reconnected.cc");
    }
}
