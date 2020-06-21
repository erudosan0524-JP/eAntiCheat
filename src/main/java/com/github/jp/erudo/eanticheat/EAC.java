package com.github.jp.erudo.eanticheat;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.jp.erudo.eanticheat.event.EventManager;
import com.github.jp.erudo.eanticheat.utils.UserManager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EAC extends JavaPlugin {

	@Getter
	public static EAC instance;
	private EventManager eventManager;

	private UserManager userManager;

	public static final String pluginName = "eAntiCheat";

	@Override
	public void onDisable() {
		getLogger().info("プラグインが停止しました。");
	}

	@Override
	public void onEnable() {
		getLogger().info("プラグインが起動しました。");

		userManager = new UserManager();

	}

}
