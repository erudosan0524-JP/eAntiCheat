package io.github.erudo.eac.protocol.packet;

import io.github.erudo.eac.protocol.api.channel.reflections.WrappedField;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PacketField<T> {
    private WrappedField field;
    private T value;
}