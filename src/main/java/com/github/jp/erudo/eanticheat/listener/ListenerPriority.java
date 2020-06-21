package com.github.jp.erudo.eanticheat.listener;

import lombok.Getter;

public enum ListenerPriority {
	NONE(2),LOWEST(0),LOW(1),NORMAL(2),HIGH(3),HIGHEST(4);

	@Getter
	private int priority;

	private ListenerPriority(int priority) {
		this.priority = priority;
	}

}
