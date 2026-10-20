package me.pajic.oxidizable_copper_gear;

import me.pajic.oxidizable_copper_gear.platform.MultiLoaderUtil;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("LoggingSimilarMessage")
public class OCG {

	public static final String MOD_ID = /*$ mod_id*/ "oxidizable_copper_gear";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final String DEFAULT_RESOURCE_PACK = "default_rp";
	public static final String DEFAULT_DATA_PACK = "default_dp";

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static void debugLog(String message, Object ... args) {
		if (MultiLoaderUtil.INSTANCE.isDevEnv()) LOGGER.info(message, args);
	}
}
