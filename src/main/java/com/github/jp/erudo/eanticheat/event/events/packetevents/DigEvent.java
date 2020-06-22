package com.github.jp.erudo.eanticheat.event.events.packetevents;

import com.github.jp.erudo.eanticheat.event.EACEvent;

import io.github.erudo.eac.protocol.packet.in.WrappedInBlockDigPacket;
import io.github.erudo.eac.protocol.packet.types.BaseBlockPosition;
import io.github.erudo.eac.protocol.packet.types.EnumDirection;

public class DigEvent extends EACEvent {

    private final BaseBlockPosition blockPos;
    private final EnumDirection direction;
    private final WrappedInBlockDigPacket.EnumPlayerDigType digType;

    public DigEvent(BaseBlockPosition blockPos, EnumDirection direction, WrappedInBlockDigPacket.EnumPlayerDigType digType) {
        this.blockPos = blockPos;
        this.direction = direction;
        this.digType = digType;
    }

    public BaseBlockPosition getBlockPos() {
        return blockPos;
    }

    public EnumDirection getDirection() {
        return direction;
    }

    public WrappedInBlockDigPacket.EnumPlayerDigType getDigType() {
        return digType;
    }

}
