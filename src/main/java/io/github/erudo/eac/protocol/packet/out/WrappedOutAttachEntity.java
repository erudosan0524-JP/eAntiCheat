/*
 * Copyright (c) 2018 NGXDEV.COM. Licensed under MIT.
 */

package io.github.erudo.eac.protocol.packet.out;

import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.Getter;

@Getter
public class WrappedOutAttachEntity extends NMSObject {
    private static final String packet = Server.ATTACH;
    private static FieldAccessor<Integer> fieldA = fetchField(packet, int.class, 0);
    private static FieldAccessor<Integer> fieldB = fetchField(packet, int.class, 1);
    private static FieldAccessor<Integer> fieldC = fetchField(packet, int.class, 2);

    private int a, b, c;


    public WrappedOutAttachEntity(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void updateObject() {

    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        a = fetch(fieldA);
        b = fetch(fieldB);
        c = fetch(fieldC);
    }
}
