package cc.reconnected.protection;

import eu.pb4.common.protection.api.CommonProtection;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class RCCProtection implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("rcc-protection");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initializing RCC-Protection:tm:");
		try {
			Objects.requireNonNull(CommonProtection.getProviderIds()).forEach(providerId -> LOGGER.info("Found provider: {}", providerId));
		} catch (NullPointerException e) {
			LOGGER.error("There are no protection providers available! RCC-Protection will not work!");
		}
	}
}