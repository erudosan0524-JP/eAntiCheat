package com.github.jp.erudo.eanticheat.checks;

import org.bukkit.ChatColor;

import com.github.jp.erudo.eanticheat.EAC;
import com.github.jp.erudo.eanticheat.event.EACEvent;
import com.github.jp.erudo.eanticheat.event.EACListener;
import com.github.jp.erudo.eanticheat.utils.user.User;

public class Check implements EACListener , CheckInterface{

	private String checkName, mode;
	private CheckType type;
	private boolean enabled;

	public Check(String checkName, String mode, CheckType type, boolean enabled) {
		this.checkName = checkName;
		this.mode = mode;
		this.type = type;
		this.enabled = enabled;
	}

	public void flag(User user, String... string) {

        user.setViolation(user.getViolation() + 1);

        StringBuilder dataStr = new StringBuilder();

        if (string.length > 0) {
            for (String s : string) {
                dataStr.append(s).append((string.length == 1 ? "" : ", "));
            }
        }

        String alert = ChatColor.DARK_PURPLE + "【eAntiCheat】" + ChatColor.WHITE + user.getPlayer().getName() + ChatColor.GRAY + "TYPE:" + ChatColor.WHITE + type.toString() + " " + ChatColor.GRAY + checkName + "(" + mode + ")";
        if (dataStr.length() > 0) {

        }

        EAC.userManager.getUsers().stream().filter(staff -> (staff.getPlayer().isOp() || staff.getPlayer().hasPermission("eac.alerts"))).forEach(staff -> staff.getPlayer().sendMessage(alert));

    }

	@Override
	public void onHandle(User u, EACEvent e) {

	}






}
