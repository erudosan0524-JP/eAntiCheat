package io.github.erudo.eac.protocol.packet;

import io.github.erudo.eac.protocol.api.channel.reflections.WrappedField;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GeneralField {
	public final WrappedField field;
	private final Object object;

	@SuppressWarnings("unchecked")
	public <T> T getObject() {
		return (T) object;
	}
}
