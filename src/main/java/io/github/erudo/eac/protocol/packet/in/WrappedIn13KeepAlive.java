package io.github.erudo.eac.protocol.packet.in;

import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.Packet.Client;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import io.github.erudo.eac.protocol.reflection.Reflection;
import lombok.Getter;

public class WrappedIn13KeepAlive extends NMSObject {
    private static final String packet = Client.KEEP_ALIVE;
    @Getter
    private long ping;
    private FieldAccessor<Long> pingField = Reflection.getFieldSafe(packet, long.class, 0);

    public WrappedIn13KeepAlive(Object object, Player player) {
        super(object, player);
    }

    @Override
    public void updateObject() {

    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        ping = fetch(pingField);
    }
}
