package com.github.jp.erudo.eanticheat.event.events.packetevents;

import org.bukkit.entity.Entity;

import com.github.jp.erudo.eanticheat.event.EACEvent;

public class EntityInteractEvent extends EACEvent {

    private final int entityId;
    private final Entity entity;

    public EntityInteractEvent(int entityId, Entity entity) {
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
