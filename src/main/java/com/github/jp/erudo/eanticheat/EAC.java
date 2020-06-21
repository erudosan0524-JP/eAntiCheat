package com.github.jp.erudo.eanticheat;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.jp.erudo.eanticheat.event.EventManager;
import com.github.jp.erudo.eanticheat.listener.BukkitEvents;
import com.github.jp.erudo.eanticheat.utils.user.UserManager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EAC extends JavaPlugin {

	//checkの内容はすべてパケットによるイベントで行う。

	@Getter
	public static EAC instance;
	public static UserManager userManager;
	private EventManager eventManager;



	public static final String pluginName = "eAntiCheat";

	@Override
	public void onDisable() {
		getLogger().info("プラグインが停止しました。");
	}

	@Override
	public void onEnable() {
		getLogger().info("プラグインが起動しました。");

		instance = this;
		userManager = new UserManager();

		getServer().getPluginManager().registerEvents(new BukkitEvents(), this);

	}

}
