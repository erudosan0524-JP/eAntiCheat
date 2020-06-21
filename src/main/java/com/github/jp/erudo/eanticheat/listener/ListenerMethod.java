package com.github.jp.erudo.eanticheat.listener;

import java.lang.reflect.Method;

import org.bukkit.plugin.Plugin;

import com.github.jp.erudo.eanticheat.event.Listen;

import io.github.erudo.eac.protocol.api.channel.reflections.WrappedClass;
import io.github.erudo.eac.protocol.api.channel.reflections.WrappedMethod;
import lombok.Getter;

@Getter
public class ListenerMethod {
	private Plugin plugin;
	private WrappedClass listenerClass;
	private WrappedMethod listenerMethod;
	private EACListener listener;
	private ListenerPriority listenerPriority;
	private String className;
	private boolean ignoreCancelled;

	public ListenerMethod(Plugin plugin, Method method, EACListener listener, ListenerPriority priority) {
		this.plugin = plugin;
		this.listenerClass = new WrappedClass(listener.getClass());
		this.listenerMethod = new WrappedMethod(listenerClass,method);
		this.listener = listener;
		this.listenerPriority = priority;
		this.className = method.getParameterTypes()[0].getName();
		this.ignoreCancelled = method.getAnnotation(Listen.class).ignoreCancelled();
	}

}