package com.github.jp.erudo.eanticheat.event.events.packetevents;

import com.github.jp.erudo.eanticheat.event.EACEvent;

import io.github.erudo.eac.protocol.packet.in.WrappedInEntityActionPacket;


public class EntityActionEvent extends EACEvent {

    private final WrappedInEntityActionPacket.EnumPlayerAction action;

    public EntityActionEvent(WrappedInEntityActionPacket.EnumPlayerAction action) {
        this.action = action;
    }

    public WrappedInEntityActionPacket.EnumPlayerAction getAction() {
        return action;
    }

}
