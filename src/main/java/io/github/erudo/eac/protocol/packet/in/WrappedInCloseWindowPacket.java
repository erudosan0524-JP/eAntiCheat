package io.github.erudo.eac.protocol.packet.in;

import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.Getter;

@Getter
public class WrappedInCloseWindowPacket extends NMSObject {
    private static final String packet = Client.CLOSE_WINDOW;

    // Fields
    private static FieldAccessor<Integer> fieldId = fetchField(packet, int.class, 0);

    // Decoded data
    private int id;

    public WrappedInCloseWindowPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void updateObject() {

    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        id = fetch(fieldId);
    }
}
