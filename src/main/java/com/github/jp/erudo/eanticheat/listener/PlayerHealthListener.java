package com.github.jp.erudo.eanticheat.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.github.jp.erudo.eanticheat.Main;
import com.github.jp.erudo.eanticheat.checks.CheckResult;
import com.github.jp.erudo.eanticheat.checks.player.FastHeal;
import com.github.jp.erudo.eanticheat.utils.MessageManager;
import com.github.jp.erudo.eanticheat.utils.User;

public class PlayerHealthListener implements Listener {

	public PlayerHealthListener(Main main) {
		main.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onRegen(EntityRegainHealthEvent e) {
		if(e.getEntityType() == EntityType.PLAYER) {
			User u = Main.getUser((Player) e.getEntity());

			CheckResult result = FastHeal.runCheck(u);
			if(result.failed()) {
				e.setCancelled(true);
				e.setAmount(0);
				MessageManager.log(result, u);
			}
		}
	}
}
