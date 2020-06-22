package io.github.erudo.eac.protocol.api.channel.handler;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;

import com.github.jp.erudo.eanticheat.EAC;
import com.github.jp.erudo.eanticheat.utils.boundingbox.box.ReflectionUtils;
import com.mojang.authlib.GameProfile;

import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.api.channel.reflections.Reflections;
import io.github.erudo.eac.protocol.reflection.FieldAccessor;
import io.github.erudo.eac.protocol.reflection.MethodInvoker;
import io.github.erudo.eac.protocol.reflection.Reflection;
import io.netty.channel.Channel;

public class ChannelHandler1_8 extends ChannelHandlerAbstract {
	private static final MethodInvoker getPlayerHandle = Reflection.getMethod("{obc}.entity.CraftPlayer", "getHandle");
    private static final FieldAccessor<Object> getConnection = Reflection.getField("{nms}.EntityPlayer", "playerConnection", Object.class);
    private static final FieldAccessor<Object> getManager = Reflection.getField("{nms}.PlayerConnection", "networkManager", Object.class);
    private static final FieldAccessor<Channel> getChannel = Reflection.getField("{nms}.NetworkManager", Channel.class, 0);


    static final FieldAccessor<GameProfile> getGameProfile = Reflection.getField(PACKET_LOGIN_IN_START, GameProfile.class, 0);
    static final FieldAccessor<Integer> protocolId = Reflection.getField(PACKET_SET_PROTOCOL, int.class, 0);
    @SuppressWarnings("rawtypes")
	static final FieldAccessor<Enum> protocolType = Reflection.getField(PACKET_SET_PROTOCOL, Enum.class, 0);
    private Map<String, Channel> channelLookup = new WeakHashMap<>();
    private Map<Channel, Integer> protocolLookup = new WeakHashMap<>();

    @Override
    public void addChannel(Player player) {
        Channel channel = getChannel(player);
        this.addChannelHandlerExecutor.execute(() -> {
            if (channel != null) {
                if (channel.pipeline().get(this.playerKey) != null) {
                    channel.pipeline().remove(this.playerKey);
                }
                channel.pipeline().addBefore(this.handlerKey, this.playerKey, new ChannelHandler(player, this));
            }
        });
    }

    @Override
    public void removeChannel(Player player) {
        Channel channel = getChannel(player);
        this.removeChannelHandlerExecutor.execute(() -> {
            if (channel != null && channel.pipeline().get(this.playerKey) != null) {
                channel.pipeline().remove(this.playerKey);
            }
        });
    }

    public ProtocolVersion getProtocolVersion(Player player) {
        Channel channel = channelLookup.get(player.getName());

        // Lookup channel again
        if (channel == null) {

            channelLookup.put(player.getName(), getChannel(player));
        }

        return ProtocolVersion.getVersion(protocolLookup.getOrDefault(channel, -1));
    }

    private Channel getChannel(Player player) {
        return (Channel) Reflections.getNMSClass("NetworkManager").getFirstFieldByType(Channel.class).get(networkManagerField.get(playerConnectionField.get(ReflectionUtils.getEntityPlayer(player))));
    }

    private class ChannelHandler extends io.netty.channel.ChannelDuplexHandler {
        private final Player player;
        @SuppressWarnings("unused")
		private final ChannelHandlerAbstract channelHandlerAbstract;

        ChannelHandler(Player player, ChannelHandlerAbstract channelHandlerAbstract) {
            this.player = player;
            this.channelHandlerAbstract = channelHandlerAbstract;
        }

        @Override
        public void write(io.netty.channel.ChannelHandlerContext ctx, Object msg, io.netty.channel.ChannelPromise promise) throws Exception {
            Object packet = EAC.getInstance().getProtocolHandler().onPacketOutAsync(player, msg);
            if (packet != null) {
                super.write(ctx, packet, promise);
            }
        }

        @Override
        public void channelRead(io.netty.channel.ChannelHandlerContext ctx, Object msg) throws Exception {
            Channel channel = ctx.channel();
            if (PACKET_LOGIN_IN_START.isInstance(msg)) {
                GameProfile profile = getGameProfile.get(msg);
                channelLookup.put(profile.getName(), channel);
            } else if (PACKET_SET_PROTOCOL.isInstance(msg)) {
                String protocol = protocolType.get(msg).name();
                if (protocol.equalsIgnoreCase("LOGIN")) {
                    protocolLookup.put(channel, protocolId.get(msg));
                }
            }
            Object packet = EAC.getInstance().getProtocolHandler().onPacketInAsync(player, msg);
            if (packet != null) {
                super.channelRead(ctx, packet);
            }
        }
    }


    public int getProtocolVersion2(Player player) {
        Channel channel = channelLookup.get(player.getName());

        // Lookup channel again
        if (channel == null) {
            Object connection = getConnection.get(getPlayerHandle.invoke(player));
            Object manager = getManager.get(connection);

            channelLookup.put(player.getName(), channel = getChannel.get(manager));
        }

        Integer protocol = protocolLookup.get(channel);
        if (protocol == null) {
            int protocolVersion = 47;
            try {
                Class<?> Via = Class.forName("us.myles.ViaVersion.api.Via");
                Class<?> clazzViaAPI = Class.forName("us.myles.ViaVersion.api.ViaAPI");
                Object ViaAPI = Via.getMethod("getAPI").invoke(null);
                Method getPlayerVersion = clazzViaAPI.getMethod("getPlayerVersion", Object.class);
                protocolVersion = (int) getPlayerVersion.invoke(ViaAPI, player);
            } catch (Throwable e) {
            }
            protocolLookup.put(channel, protocolVersion);
            return protocolVersion;
        } else return protocol;
    }


    public void sendPacket(Player player, Object packet) {
        getChannel(player).pipeline().writeAndFlush(packet);
    }
}
