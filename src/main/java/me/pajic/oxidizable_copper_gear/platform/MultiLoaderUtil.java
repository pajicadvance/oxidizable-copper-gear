package me.pajic.oxidizable_copper_gear.platform;

//$ loader_util_import
import me.pajic.oxidizable_copper_gear.platform.fabric.FabricLoaderUtil;

public interface MultiLoaderUtil {
    MultiLoaderUtil INSTANCE = /*$ loader_util_inst*/ new FabricLoaderUtil();

    boolean isModLoaded(String modId);
    boolean isDevEnv();
}
