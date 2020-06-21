package com.github.jp.erudo.eanticheat.utils.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.jp.erudo.eanticheat.EAC;
import com.github.jp.erudo.eanticheat.checks.Check;
import com.github.jp.erudo.eanticheat.checks.CheckType;
import com.github.jp.erudo.eanticheat.checks.modules.movement.SpeedA;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	List<Check> checkList = new ArrayList<Check>();

	private Player player;
	private UUID uuid;

	public User(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId();

		registerChecks();

	}

	private void addCheck(Check check) {
		if(!checkList.contains(check)) {
			checkList.add(check);
		}
	}

	private void registerChecks() {

		addCheck(new SpeedA("Speed","A",CheckType.MOVEMENT, true));

		//ここでチェックしたもののイベントを登録
		checkList.forEach(check -> {
			EAC.getInstance().getEventManager().registerListeners(check, EAC.getInstance());
		});

	}

}
