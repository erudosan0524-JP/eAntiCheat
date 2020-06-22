package com.github.jp.erudo.eanticheat.event;

import java.lang.reflect.Method;

import org.bukkit.plugin.Plugin;

import io.github.erudo.eac.protocol.api.channel.reflections.WrappedClass;
import io.github.erudo.eac.protocol.api.channel.reflections.WrappedMethod;
import lombok.Getter;

@Getter
public class ListenerMethod {
	public Plugin plugin;
	public WrappedClass listenerClass;
	public WrappedMethod method;
	public EACListener listener;
	public ListenerPriority listenerPriority;
	public String className;
	public boolean ignoreCancelled;

	public ListenerMethod(Plugin plugin, Method method, EACListener listener, ListenerPriority priority) {
		this.plugin = plugin;
		this.listenerClass = new WrappedClass(listener.getClass());
		this.method = new WrappedMethod(listenerClass,method);
		this.listener = listener;
		this.listenerPriority = priority;
		this.className = method.getParameterTypes()[0].getName();
		this.ignoreCancelled = method.getAnnotation(Listen.class).ignoreCancelled();
	}

}