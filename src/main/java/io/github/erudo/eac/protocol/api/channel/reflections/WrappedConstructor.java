package io.github.erudo.eac.protocol.api.channel.reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class WrappedConstructor {

	private final WrappedClass parent;
	@SuppressWarnings("rawtypes")
	private Constructor constructor;

	@SuppressWarnings("unchecked")
	public <T> T newInstance(Object... obj) {
		try {
			constructor.setAccessible(true);
			return (T) constructor.newInstance(obj);
		} catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T newInstance() {
		try {
			constructor.setAccessible(true);
			return (T) constructor.newInstance();
		} catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

}
