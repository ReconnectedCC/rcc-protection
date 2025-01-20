package cc.reconnected.protection;

import com.uchuhimo.konf.ConfigSpec;
import com.uchuhimo.konf.OptionalItem;

public class LedgerExtensionCfg{
    public static final ConfigSpec spec = new ConfigSpec("RCC-Protection");

    public static final OptionalItem<Boolean> logLaser =
            new OptionalItem<Boolean>(spec, "logLaser", true);
}
