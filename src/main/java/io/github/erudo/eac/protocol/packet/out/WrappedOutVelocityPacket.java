/*
 * Copyright (c) 2018 NGXDEV.COM. Licensed under MIT.
 */

package io.github.erudo.eac.protocol.packet.out;

import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.packet.types.Vec3D;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.Getter;

@Getter
public class WrappedOutVelocityPacket extends NMSObject {
    private static final String packet = Server.ENTITY_VELOCITY;

    // Fields
    private static FieldAccessor<Integer> fieldId = fetchField(packet, int.class, 0);
    private static FieldAccessor<Integer> fieldX = fetchField(packet, int.class, 1);
    private static FieldAccessor<Integer> fieldY = fetchField(packet, int.class, 2);
    private static FieldAccessor<Integer> fieldZ = fetchField(packet, int.class, 3);

    // Decoded data
    private int id;
    private double x, y, z;

    public WrappedOutVelocityPacket(int entityId, double x, double y, double z) {
        if(ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_14)) {
            setPacket(packet, entityId, x, y, z);
        } else setPacket(packet, entityId, new Vec3D(x, y, z).getObject()); //changed to Vec3D for some reason...
    }

    public WrappedOutVelocityPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void updateObject() {

    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        id = fetch(fieldId);
        x = fetch(fieldX) / 8000D;
        y = fetch(fieldY) / 8000D;
        z = fetch(fieldZ) / 8000D;
    }
}
