package com.github.jp.erudo.eanticheat.event.events.packetevents;

import org.bukkit.entity.Entity;

import com.github.jp.erudo.eanticheat.event.EACEvent;

public class AttackEvent extends EACEvent {

    private final int entityId;
    private final Entity entity;

    public AttackEvent(int entityId, Entity entity) {
        this.entityId = entityId;
        this.entity = entity;
    }

    public int getEntityId() {
        return entityId;
    }

    public Entity getEntity() {
        return entity;
    }

}
