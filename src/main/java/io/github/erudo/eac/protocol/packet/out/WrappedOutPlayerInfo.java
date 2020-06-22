package io.github.erudo.eac.protocol.packet.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import com.github.jp.erudo.eanticheat.utils.boundingbox.box.ReflectionUtils;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.api.channel.reflections.Reflections;
import io.github.erudo.eac.protocol.api.channel.reflections.WrappedClass;
import io.github.erudo.eac.protocol.packet.types.WrappedEnumGameMode;
import io.github.erudo.eac.protocol.packet.types.WrappedEnumPlayerInfoAction;
import io.github.erudo.eac.protocol.packet.types.WrappedGameProfile;
import io.github.erudo.eac.protocol.packet.types.WrappedPlayerInfoData;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;

public class WrappedOutPlayerInfo extends NMSObject {
    private static String packet = Server.PLAYER_INFO;

    public WrappedOutPlayerInfo(Object object, Player player) {
        super(object, player);
    }

    @Override
    public void updateObject() {

    }

    public WrappedOutPlayerInfo(WrappedEnumPlayerInfoAction action, Player... players) {
        if(ProtocolVersion.getGameVersion().isOrAbove(ProtocolVersion.V1_8)) {
            setPacket(packet, Reflections.getNMSClass("PacketPlayOutPlayerInfo.EnumPlayerInfoAction").getEnum(action.name()), Arrays.stream(players).map(ReflectionUtils::getEntityPlayer).collect(Collectors.toList()));
        } else {
            WrappedClass outPlayerInfo = Reflections.getNMSClass(packet);
            Object packet = outPlayerInfo.getConstructor().newInstance();
            outPlayerInfo.getMethod(action.legacyMethodName, ReflectionUtils.EntityPlayer).invoke(packet, ReflectionUtils.getEntityPlayer(players[0]));

            setObject(packet);
        }
    }

    //1.8+
    private static FieldAccessor<List> playerInfoListAccessor;
    private static FieldAccessor<Enum> actionAcessorEnum;

    //1.7.10
    private static FieldAccessor<Integer> actionAcessorInteger;
    private static FieldAccessor<Integer> gamemodeAccessor;
    private static FieldAccessor<Object> profileAcessor;
    private static FieldAccessor<Integer> pingAcessor;


    private List<WrappedPlayerInfoData> playerInfo = new ArrayList<>();
    private WrappedEnumPlayerInfoAction action;

    @Override
    public void process(Player player, ProtocolVersion version) {
        if(ProtocolVersion.getGameVersion().isOrAbove(ProtocolVersion.V1_8)) {
            playerInfoListAccessor = fetchField(packet, List.class, 0);
            actionAcessorEnum = fetchField(packet, Enum.class, 0);

            List list = fetch(playerInfoListAccessor);

            for (Object object : list) {
                playerInfo.add(new WrappedPlayerInfoData(object));
            }

            action = WrappedEnumPlayerInfoAction.valueOf(fetch(actionAcessorEnum).name());
        } else {
            actionAcessorInteger = fetchField(packet, Integer.class, 5);
            profileAcessor = fetchFieldByName(packet, "player", Object.class);
            gamemodeAccessor = fetchField(packet, Integer.class, 6);
            pingAcessor = fetchField(packet, Integer.class, 7);

            action = WrappedEnumPlayerInfoAction.values()[fetch(actionAcessorInteger)];

            WrappedGameProfile profile = new WrappedGameProfile(fetch(profileAcessor));
            WrappedEnumGameMode gamemode = WrappedEnumGameMode.getById(fetch(gamemodeAccessor));
            int ping = fetch(pingAcessor);
            playerInfo.add(new WrappedPlayerInfoData(profile, gamemode, ping));
        }
    }
}
