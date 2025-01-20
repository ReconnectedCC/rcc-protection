package cc.reconnected.protection;

import com.github.quiltservertools.ledger.api.LedgerExtension;
import com.uchuhimo.konf.ConfigSpec;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LedgerRCCProtectionExtension implements LedgerExtension {
    @Override
    public @NotNull Identifier getIdentifier() {
        return new Identifier("reconnectedcc", "protection");
    }

    @Override
    public @NotNull List<com.uchuhimo.konf.ConfigSpec> getConfigSpecs() {
        return List.of(LedgerExtensionCfg.spec);
    }
}
