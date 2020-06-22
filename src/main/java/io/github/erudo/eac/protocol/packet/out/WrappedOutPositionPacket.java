/*
 * Copyright (c) 2018 NGXDEV.COM. Licensed under MIT.
 */

package io.github.erudo.eac.protocol.packet.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.packet.types.WrappedEnumTeleportFlag;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WrappedOutPositionPacket extends NMSObject {
    private static final String packet = Server.POSITION;

    // Fields
    private static FieldAccessor<Double> fieldX = fetchField(packet, double.class, 0);
    private static FieldAccessor<Double> fieldY = fetchField(packet, double.class, 1);
    private static FieldAccessor<Double> fieldZ = fetchField(packet, double.class, 2);
    private static FieldAccessor<Float> fieldYaw = fetchField(packet, float.class, 0);
    private static FieldAccessor<Float> fieldPitch = fetchField(packet, float.class, 1);

    // Decoded data
    private double x, y, z;
    private float yaw, pitch;

    public WrappedOutPositionPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void updateObject() {

    }

    public WrappedOutPositionPacket(Location location, int teleportAwait, @Nullable WrappedEnumTeleportFlag... flags) {
        if(ProtocolVersion.getGameVersion().isAbove(ProtocolVersion.V1_9)) {
            setPacket(packet, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), flags != null ? Arrays.stream(flags).map(WrappedEnumTeleportFlag::getObject).collect(Collectors.toSet()) : new HashSet<>(), teleportAwait);
        } else if(ProtocolVersion.getGameVersion().isAbove(ProtocolVersion.V1_7_10)) {
            setPacket(packet, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), flags != null ? Arrays.stream(flags).map(WrappedEnumTeleportFlag::getObject).collect(Collectors.toSet()) : new HashSet<>());
        } else setPacket(packet, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), flags != null);
    }

    public WrappedOutPositionPacket(Location location, @Nullable WrappedEnumTeleportFlag... flags) {
        this(location, 0, flags);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        x = fetch(fieldX);
        y = fetch(fieldY);
        z = fetch(fieldZ);
        yaw = fetch(fieldYaw);
        pitch = fetch(fieldPitch);
    }

    private List<Integer> toOrdinal(Set<Enum> enums) {
        List<Integer> ordinals = new ArrayList<>();
        enums.forEach(e -> ordinals.add(e.ordinal()));
        return ordinals;
    }
}
