package com.github.jp.erudo.eanticheat.event.events.bukkitevents;

import org.bukkit.Location;

import com.github.jp.erudo.eanticheat.event.EACEvent;

public class MoveEvent extends EACEvent  {

    private final Location to;
    private final Location from;

    public MoveEvent(Location to, Location from) {
        this.to = to;
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public Location getFrom() {
        return from;
    }

}
