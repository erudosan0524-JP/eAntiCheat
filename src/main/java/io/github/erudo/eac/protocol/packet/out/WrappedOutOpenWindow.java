package io.github.erudo.eac.protocol.packet.out;

import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.packet.types.WrappedChatMessage;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.Getter;

@Getter
public class WrappedOutOpenWindow extends NMSObject {

    private static String packet = Server.OPEN_WINDOW;

    public WrappedOutOpenWindow(Object object, Player player) {
        super(object, player);
    }

    @Override
    public void updateObject() {

    }

    public WrappedOutOpenWindow(int id, String name, WrappedChatMessage msg, int size) {
        setPacket(packet, id, name, msg.getObject(), size);
    }

    private static FieldAccessor<Integer> idField = fetchField(packet, int.class, 0);
    private static FieldAccessor<String> nameField = fetchField(packet, String.class, 0);
    private static FieldAccessor<Object> chatCompField = fetchField(packet, Object.class, 2);
    private static FieldAccessor<Integer> inventorySize = fetchField(packet, int.class, 1);

    private int id;
    private String name;
    private WrappedChatMessage chatComponent;
    private int size;

    @Override
    public void process(Player player, ProtocolVersion version) {
        id = fetch(idField);
        name = fetch(nameField);
        chatComponent = new WrappedChatMessage(fetch(chatCompField));
        size = fetch(inventorySize);
    }
}
