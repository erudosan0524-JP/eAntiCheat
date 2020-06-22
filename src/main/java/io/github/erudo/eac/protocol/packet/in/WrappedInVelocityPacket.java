/*
 * Copyright (c) 2018 NGXDEV.COM. Licensed under MIT.
 */

package io.github.erudo.eac.protocol.packet.in;

import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.Packet;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.Getter;

@Getter
public class WrappedInVelocityPacket extends Packet {
    private static final String packet = Client.FLYING;

    // Fields
    private static FieldAccessor<Integer> fieldX = fetchField(packet, int.class, 0);
    private static FieldAccessor<Integer> fieldY = fetchField(packet, int.class, 1);
    private static FieldAccessor<Integer> fieldZ = fetchField(packet, int.class, 2);

    // Decoded data
    private double x, y, z;

    public WrappedInVelocityPacket(Object packet) {
        super(packet);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        x = fieldX.get(getPacket());
        y = fieldY.get(getPacket());
        z = fieldZ.get(getPacket());
    }
}
