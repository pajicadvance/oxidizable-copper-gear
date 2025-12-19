package me.pajic.oxidizable_copper_gear.platform;

public interface Platform {
	boolean isModLoaded(String modId);

	boolean isDebug();

	ModLoader loader();

	String mcVersion();

	String packPath(VersionedPackType versionedPackType);

	enum ModLoader {
		FABRIC, NEOFORGE, FORGE
	}

	enum VersionedPackType {
		ASSETS("default_rp"),
		DATA("default_dp");

		private final String path;

		VersionedPackType(String path) {
			this.path = path;
		}

		public String getName() {
			return path;
		}
	}
}
