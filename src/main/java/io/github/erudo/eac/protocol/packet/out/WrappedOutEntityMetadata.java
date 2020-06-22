package io.github.erudo.eac.protocol.packet.out;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.Packet;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrappedOutEntityMetadata extends NMSObject {
    private static final String packet = Packet.Server.ENTITY_METADATA;

    private static FieldAccessor<Integer> entityidField;
    private static FieldAccessor<List> watchableObjectsField;

    private List<Object> watchableObjects;
    private int entityId;

    public WrappedOutEntityMetadata(Object object, Player player) {
        super(object, player);
    }

    @Override
    public void updateObject() {

    }

    public WrappedOutEntityMetadata(int entityId, List<Object> objects) {
        setPacket(packet, entityId, objects);
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        watchableObjectsField = fetchField(packet, List.class, 0);
        entityidField = fetchField(packet, int.class, 0);

        watchableObjects = new ArrayList<>();
        entityId = fetch(entityidField);

        List list = fetch(watchableObjectsField);

        if(list != null) {
            list.forEach(object -> watchableObjects.add(object));
        }
    }
}
