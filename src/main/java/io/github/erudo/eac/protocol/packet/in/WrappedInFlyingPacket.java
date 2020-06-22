/*
 * Copyright (c) 2018 NGXDEV.COM. Licensed under MIT.
 */

package io.github.erudo.eac.protocol.packet.in;

import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.Getter;

@Getter
public class WrappedInFlyingPacket extends NMSObject {
    private static final String packet = Client.FLYING;

    // Fields
    private static FieldAccessor<Double> fieldX = fetchField(packet, double.class, 0);
    private static FieldAccessor<Double> fieldY = fetchField(packet, double.class, 1);
    private static FieldAccessor<Double> fieldZ = fetchField(packet, double.class, 2);
    private static FieldAccessor<Float> fieldYaw = fetchField(packet, float.class, 0);
    private static FieldAccessor<Float> fieldPitch = fetchField(packet, float.class, 1);
    private static FieldAccessor<Boolean> fieldGround = fetchField(packet, boolean.class, 0);

    // Decoded data
    private double x, y, z;
    private float yaw, pitch;
    private boolean look, pos, ground;

    public WrappedInFlyingPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void updateObject() {

    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        String name = getPacketName();
        // This saves up 2 reflection calls
        pos = name.contains("Position");
        look = name.contains("Look");

        if (pos) {
            x = fetch(fieldX);
            y = fetch(fieldY);
            z = fetch(fieldZ);
        }
        if (look) {
            yaw = fetch(fieldYaw);
            pitch = fetch(fieldPitch);
        }
        ground = fetch(fieldGround);
    }
}
