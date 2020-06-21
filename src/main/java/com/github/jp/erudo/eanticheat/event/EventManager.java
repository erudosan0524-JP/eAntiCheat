package com.github.jp.erudo.eanticheat.event;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.plugin.Plugin;

import com.github.jp.erudo.eanticheat.listener.EACListener;
import com.github.jp.erudo.eanticheat.listener.ListenerMethod;
import com.github.jp.erudo.eanticheat.utils.exception.ListenerParameterException;

public class EventManager {

	private final List<ListenerMethod> listenerMethods = new CopyOnWriteArrayList<>();
	public boolean paused = false;

	public void registerListener(Method method, EACListener listener, Plugin plugin) throws ListenerParameterException {
		if(method.getParameterTypes().length == 1) {
			if(method.getParameterTypes()[0].getSuperclass().equals(EACEvent.class)) {
				Listen listen = method.getAnnotation(Listen.class);
				ListenerMethod lm = new ListenerMethod(plugin, method, listener, listen.priority());
			}
		}
	}

	public void registerListeners(EACListener listener, Plugin plugin) {
		//Methodsの配列をstreamに変換してfilterにかけforで回している。つまり
		/*
		 * for(Method method : getMethods()) {
		 * 	if(method.isAnnotationPresent(Listen.class)){ ←個々の部分がfilterの処理で書かれている
		 * 		try{
		 * 			registerListener(method, listener, plugin);
		 * 		}catch(ListenerParameterException e) {
		 * 			e.printStackTrace();
		 * 		}
		 * 	}
		 * }
		 * これと下は同じ意味
		 */
		Arrays.stream(listener.getClass().getMethods()).filter(method -> method.isAnnotationPresent(Listen.class)).forEach(method -> {
			try {
				registerListener(method,listener,plugin);
			} catch(ListenerParameterException e) {
				e.printStackTrace();
			}
		});
	}
}
