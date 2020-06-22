package com.github.jp.erudo.eanticheat.checks;

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

	@Override
	public void onHandle(User u, EACEvent e) {

	}






}
