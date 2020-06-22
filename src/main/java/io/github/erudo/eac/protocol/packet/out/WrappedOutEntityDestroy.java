package io.github.erudo.eac.protocol.packet.out;

import io.github.erudo.eac.protocol.api.NMSObject;
import lombok.Getter;

@Getter
public class WrappedOutEntityDestroy extends NMSObject {
    private static final String packet = Server.ENTITY_DESTROY;

    public WrappedOutEntityDestroy(int[] ids) {
        setPacket(packet, ids);
    }

    @Override
    public void updateObject() {

    }
}