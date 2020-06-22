package io.github.erudo.eac.protocol.packet.out;

import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.Getter;

@Getter
public class WrappedOutHeldItemSlot extends NMSObject {
    private static String packet = Server.HELD_ITEM;
    private FieldAccessor<Integer> slotField = fetchField(packet, int.class, 0);

    private int slot;

    public WrappedOutHeldItemSlot(Object object, Player player) {
        super(object, player);
    }

    @Override
    public void updateObject() {

    }

    public WrappedOutHeldItemSlot(int slot) {
        this.slot = slot;

        setObject(construct(packet, slot));
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        slot = fetch(slotField);
    }

    @Override
    public Object getObject() {
        return super.getObject();
    }
}
