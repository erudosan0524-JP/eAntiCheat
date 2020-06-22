package io.github.erudo.eac.protocol.packet.out;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.github.jp.erudo.eanticheat.utils.boundingbox.box.ReflectionUtils;

import io.github.erudo.eac.protocol.api.NMSObject;
import io.github.erudo.eac.protocol.api.Packet;
import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.api.channel.reflections.Reflections;
import io.github.erudo.eac.protocol.api.channel.reflections.WrappedClass;
import io.github.erudo.eac.protocol.api.channel.reflections.WrappedField;
import io.github.erudo.eac.protocol.api.channel.reflections.WrappedMethod;
import io.github.erudo.eac.protocol.packet.types.BaseBlockPosition;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import lombok.Getter;

@Getter
public class WrappedOutBlockChange extends NMSObject {
    private static final String packet = Packet.Server.BLOCK_CHANGE;

    //1.7.10 and below
    private static FieldAccessor<Integer> legacyX;
    private static FieldAccessor<Integer> legacyY;
    private static FieldAccessor<Integer> legacyZ;
    private static WrappedField blockChangeBlockField;
    private static WrappedField blockDataIntField;
    private static WrappedMethod getDataMethod;

    //1.8 and newer
    private static FieldAccessor<Object> blockPosAccessor;
    private static WrappedField iBlockDataField;


    private static WrappedClass blockChangeClass = Reflections.getNMSClass(packet);
    private static WrappedClass nmsBlockClass = Reflections.getNMSClass("Block");
    private static WrappedClass craftBlockClass = Reflections.getCBClass("CraftBlock");

    private BaseBlockPosition position;

    public WrappedOutBlockChange(Object packet) {
        super(packet);
    }

    @Override
    public void updateObject() {

    }

    public WrappedOutBlockChange(Block block) {
        if(ProtocolVersion.getGameVersion().isAbove(ProtocolVersion.V1_7_10)) {
            setPacket(packet, block.getX(), block.getY(), block.getZ(), ReflectionUtils.getWorldHandle(block.getWorld()));
        } else {
            setPacket(packet, ReflectionUtils.getWorldHandle(block.getWorld()), new BaseBlockPosition(block.getX(), block.getY(), block.getZ()).getAsBlockPosition());
        }
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        if (ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_8)) {
            position = new BaseBlockPosition(fetch(legacyX), fetch(legacyY), fetch(legacyZ));
        } else {
            position = new BaseBlockPosition(fetch(blockPosAccessor));
        }
    }

    static {
        if(ProtocolVersion.getGameVersion().isBelow(ProtocolVersion.V1_8)) {
            legacyX = fetchField(packet, int.class, 0);
            legacyY = fetchField(packet, int.class, 1);
            legacyZ = fetchField(packet, int.class, 2);


            blockChangeBlockField = blockChangeClass.getFirstFieldByType(nmsBlockClass.getParent());
            blockDataIntField = blockChangeClass.getFieldByName("data");
            getDataMethod = Reflections.getNMSClass("World").getMethod("getData", int.class, int.class, int.class);
        } else {
            blockPosAccessor = fetchField(packet, Object.class, 0);
            iBlockDataField = blockChangeClass.getFieldByType(ReflectionUtils.iBlockData, 0);
        }
    }
}
