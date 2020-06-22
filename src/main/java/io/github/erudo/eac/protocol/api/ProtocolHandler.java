package io.github.erudo.eac.protocol.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.jp.erudo.eanticheat.EAC;
import com.github.jp.erudo.eanticheat.event.events.PacketEvent;
import com.github.jp.erudo.eanticheat.utils.user.User;

import io.github.erudo.eac.protocol.api.channel.ChannelInjector;
import lombok.Getter;

public class ProtocolHandler {

	@Getter
    private static ChannelInjector instance;

    public boolean paused = false;

    public ProtocolHandler() {
        // 1.8+ and 1.7 NMS have different class paths for their libraries used. This is why we have to separate the two.
        // These feed the packets asynchronously, before Minecraft processes it, into our own methods to process and be used as an API.

        instance = new ChannelInjector();

        Bukkit.getPluginManager().registerEvents(instance, EAC.getInstance());
    }

    // Purely for making the code cleaner
    public static void sendPacket(Player player, Object packet) {
        instance.getChannel().sendPacket(player, packet);
    }

    public static ProtocolVersion getProtocolVersion(Player player) {
        return instance.getChannel().getProtocolVersion(player);
    }



    public Object onPacketOutAsync(Player sender, Object packet) {

        if(!paused) {

            String name = packet.getClass().getName();
            int index = name.lastIndexOf(".");
            String packetName = name.substring(index + 1);
            User user = EAC.userManager.getUser(sender.getUniqueId());
            PacketEvent event = new PacketEvent(sender, packet, packetName, PacketEvent.Direction.SERVER);

            if (user != null && (System.currentTimeMillis() - user.getTimestamp()) > 500L) {
                event = new PacketEvent(sender, packet, packetName, PacketEvent.Direction.SERVER, user);
            }
            EAC.getInstance().getEventManager().callEvent(event);


            return !event.isCancelled() ? event.getPacket() : null;
        } else return packet;
    }

    public Object onPacketInAsync(Player sender, Object packet) {
        if (!paused) {

            String name = packet.getClass().getName();
            int index = name.lastIndexOf(".");
            String packetName = name.substring(index + 1).replace("PacketPlayInUseItem", "PacketPlayInBlockPlace").replace(Packet.Client.LEGACY_LOOK, Packet.Client.LOOK).replace(Packet.Client.LEGACY_POSITION, Packet.Client.POSITION).replace(Packet.Client.LEGACY_POSITION_LOOK, Packet.Client.POSITION_LOOK);

            User user = EAC.userManager.getUser(sender.getUniqueId());
            PacketEvent event = new PacketEvent(sender, packet, packetName, PacketEvent.Direction.CLIENT);


            if (user != null && (System.currentTimeMillis() - user.getTimestamp()) > 500L) {
                event = new PacketEvent(sender, packet, packetName, PacketEvent.Direction.CLIENT, user);
            }

            if (user != null) {
                PacketEvent finalEvent = event;
                user.getExecutorService().execute(() -> EAC.getInstance().getEventManager().callEvent(finalEvent));
            }

            return !event.isCancelled() ? event.getPacket() : null;
        }
        return packet;
    }
}
