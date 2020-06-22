package io.github.erudo.eac.protocol.api.channel;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.jp.erudo.eanticheat.EAC;
import com.github.jp.erudo.eanticheat.utils.user.User;

import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.api.channel.handler.ChannelHandler1_7;
import io.github.erudo.eac.protocol.api.channel.handler.ChannelHandler1_8;
import io.github.erudo.eac.protocol.api.channel.handler.ChannelHandlerAbstract;
import lombok.Getter;

@Getter
public class ChannelInjector implements Listener {

	private ChannelHandlerAbstract channel;

    public ChannelInjector() {
        this.channel = ProtocolVersion.getGameVersion().isOrAbove(ProtocolVersion.V1_8) ? new ChannelHandler1_8() : new ChannelHandler1_7();
    }

    public void addChannel(Player player) {
        EAC.userManager.addUser(new User(player));
        this.channel.addChannel(player);
        User user = EAC.userManager.getUser(player.getUniqueId());

       /* if (user != null) {
            try {
                ProtocolVersion protocolVersion = ProtocolVersion.getVersion(this.channel.getProtocolVersion(player).getVersion());
                user.setProtocolVersion(protocolVersion);
            } catch (Exception ignored) {
                user.setProtocolVersion(ProtocolVersion.V1_7_10);
            }
            if (user.getProtocolVersion() == ProtocolVersion.UNKNOWN) {
                user.setProtocolVersion(ProtocolVersion.V1_7_10);
            }
        }*/
    }

    public void removeChannel(Player player) {
        User user = EAC.userManager.getUser(player.getUniqueId());
        if (user != null) EAC.userManager.removeUser(user);
        this.channel.removeChannel(player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        EAC.getInstance().getExecutorService().execute(() -> addChannel(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        removeChannel(event.getPlayer());
    }

}
