package com.github.jp.erudo.eanticheat.checks;

import com.github.jp.erudo.eanticheat.event.EACEvent;
import com.github.jp.erudo.eanticheat.utils.user.User;

public interface CheckInterface {
	void onHandle(User u, EACEvent e);
}
