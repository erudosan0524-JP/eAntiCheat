package com.github.jp.erudo.eanticheat.event.events;

import org.bukkit.entity.Player;

import com.github.jp.erudo.eanticheat.event.Cancellable;
import com.github.jp.erudo.eanticheat.event.EACEvent;
import com.github.jp.erudo.eanticheat.utils.location.CustomLocation;
import com.github.jp.erudo.eanticheat.utils.user.User;

import io.github.erudo.eac.protocol.api.Packet;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PacketEvent extends EACEvent implements Cancellable {

    private Player player;

    @Setter
    private Object packet;

    @Setter
    private boolean cancelled;

    private String type;

    private Direction direction;

    private User user;

    private CustomLocation to, from;

    private long timeStamp;

    public PacketEvent(Player player, Object packet, String type, Direction direction, User user) {
        this.player = player;
        this.packet = packet;
        this.type = type;
        this.direction = direction;
        this.user = user;
        this.to = user.getTo();
        this.from = user.getFrom();

        timeStamp = System.currentTimeMillis();
    }

    public PacketEvent(Player player, Object packet, String type, Direction direction) {
        this.player = player;
        this.packet = packet;
        this.type = type;
        this.direction = direction;

        timeStamp = System.currentTimeMillis();
    }

    public boolean isPacketMovement() {
        return (type.equalsIgnoreCase(Packet.Client.POSITION) || type.equalsIgnoreCase(Packet.Client.FLYING) || type.equalsIgnoreCase(Packet.Client.POSITION_LOOK) || type.equalsIgnoreCase(Packet.Client.LOOK));
    }

    public enum Direction {
        CLIENT,
        SERVER
    }
}