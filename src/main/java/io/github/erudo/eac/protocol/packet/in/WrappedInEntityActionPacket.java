package io.github.erudo.eac.protocol.packet.in;

import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.Getter;

@Getter
public class WrappedInEntityActionPacket extends NMSObject {
    private static final String packet = Client.ENTITY_ACTION;

    // Fields
    private static FieldAccessor<Integer> fieldAction1_7;
    private static FieldAccessor<Enum> fieldAction1_8;

    // Decoded data
    private EnumPlayerAction action;

    public WrappedInEntityActionPacket(Object packet, Player player) {
        super(packet, player);
    }

    @Override
    public void updateObject() {

    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_8)) {
            fieldAction1_7 = fetchField(packet, int.class, 0);
            action = EnumPlayerAction.values()[Math.min(8, fetch(fieldAction1_7))];
        } else {
            fieldAction1_8 = fetchField(packet, Enum.class, 0);
            action = EnumPlayerAction.values()[fetch(fieldAction1_8).ordinal()];
        }
    }

    public enum EnumPlayerAction {
        START_SNEAKING,
        STOP_SNEAKING,
        STOP_SLEEPING,
        START_SPRINTING,
        STOP_SPRINTING,
        START_RIDING_JUMP,
        STOP_RIDING_JUMP,
        OPEN_INVENTORY,
        START_FALL_FLYING
    }
}
