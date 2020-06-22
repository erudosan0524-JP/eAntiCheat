package io.github.erudo.eac.protocol.packet.out;

import com.github.jp.erudo.eanticheat.utils.MathUtil;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.ProtocolVersion;

public class WrappedOutNamedEntitySpawn extends NMSObject {
    private static final String packet = NMSObject.Server.NAMED_ENTITY_SPAWN;

    public WrappedOutNamedEntitySpawn(ProtocolVersion version, int id, Object gameProfile, double x, double y, double z, Object dataWatcher, Object watchables) {
        if (version.isBelow(ProtocolVersion.V1_8)) {
       //     setPacket(packet,id, gameProfile, MathUtil.floor(x * 32.0D), MathUtil.floor(y * 32.0D), MathUtil.floor(z * 32.0D), null, null, null, dataWatcher);
        } else {
        //    WrappedGameProfile profile = new WrappedGameProfile(gameProfile);
         //   setPacket(packet,id, profile.getId(), profile.getName(), profile.getPropertyMap(), MathUtil.floor(x * 32.0D), MathUtil.floor(y * 32.0D), MathUtil.floor(z * 32.0D), null, null, null, dataWatcher, watchables);
        }
        setPacket(packet,id, gameProfile, MathUtil.floor(x * 32.0D), MathUtil.floor(y * 32.0D), MathUtil.floor(z * 32.0D), null, null, null, dataWatcher);
    }

    @Override
    public void updateObject() {

    }
}