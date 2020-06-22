package io.github.erudo.eac.protocol.packet.types;

import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WrappedPlayerInfoData extends NMSObject {
    private static String type = Type.PLAYERINFODATA;

    private static FieldAccessor<Enum> enumGamemodeAccessor = fetchField(type, Enum.class, 0);
    private static FieldAccessor<Object> profileAcessor = fetchFieldByName(type, "d", Object.class);
    private static FieldAccessor<Integer> pingAcessor = fetchField(type, Integer.class, 0);

    private int ping;
    private WrappedEnumGameMode gameMode;
    private WrappedGameProfile gameProfile;
    private String username = "";

    public WrappedPlayerInfoData(Object object, Player player) {
        super(object, player);
    }

    @Override
    public void updateObject() {

    }

    public WrappedPlayerInfoData(Object object) {
        super(object);
        ping = fetch(pingAcessor);
        gameProfile = new WrappedGameProfile(fetch(profileAcessor));
        gameMode = WrappedEnumGameMode.fromObject(fetch(enumGamemodeAccessor));
    }

    public WrappedPlayerInfoData(WrappedGameProfile gameProfile, WrappedEnumGameMode gameMode, int ping) {
        this.ping = ping;
        this.gameProfile = gameProfile;
        this.gameMode = gameMode;
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        super.process(player, version);

        ping = fetch(pingAcessor);
        gameProfile = new WrappedGameProfile(fetch(profileAcessor));
        gameMode = WrappedEnumGameMode.fromObject(fetch(enumGamemodeAccessor));
        username = player.getName();
    }
}
