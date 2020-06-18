package com.github.jp.erudo.eanticheat;

import org.bukkit.plugin.java.JavaPlugin;

public class EAC extends JavaPlugin {

	public static final String pluginName = "eAntiCheat";

	@Override
	public void onDisable() {
		getLogger().info("プラグインが停止しました。");
	}

	@Override
	public void onEnable() {
		getLogger().info("プラグインが起動しました。");

		///////////////////////////
		///		Listener		///
		///////////////////////////

	}

}
