package com.github.jp.erudo.eanticheat.checks.player;

import com.github.jp.erudo.eanticheat.checks.CheckResult;
import com.github.jp.erudo.eanticheat.checks.CheckType;
import com.github.jp.erudo.eanticheat.checks.Level;
import com.github.jp.erudo.eanticheat.utils.User;

public class FastHeal {

	public static final CheckResult PASS = new CheckResult(Level.PASSED, null, CheckType.FASTHEAL);

	public static CheckResult runCheck(User u) {
		// TODO 自動生成されたメソッド・スタブ
		return PASS;
	}

}
