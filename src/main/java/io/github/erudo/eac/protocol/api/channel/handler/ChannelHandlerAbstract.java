package io.github.erudo.eac.protocol.api.channel.handler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.bukkit.entity.Player;

import io.github.erudo.eac.protocol.api.ProtocolVersion;
import io.github.erudo.eac.protocol.api.channel.reflections.Reflections;
import io.github.erudo.eac.protocol.api.channel.reflections.WrappedField;

public abstract class ChannelHandlerAbstract {
	static final WrappedField networkManagerField = Reflections.getNMSClass("PlayerConnection").getFieldByName("networkManager");
	static final WrappedField playerConnectionField = Reflections.getNMSClass("EntityPlayer").getFieldByName("playerConnection");

	//遮断しなければならないPacket


	final Executor addChannelHandlerExecutor;
	final Executor removeChannelHandlerExecutor;
	final String handlerKey;
	final String playerKey;

	ChannelHandlerAbstract() {
		addChannelHandlerExecutor = Executors.newSingleThreadExecutor();
		removeChannelHandlerExecutor = Executors.newSingleThreadExecutor();
		this.handlerKey = "packet_handler";
		this.playerKey = "sparky_player_handler";
	}

	public abstract void addChannel(Player player);

    public abstract void removeChannel(Player player);

    public abstract void sendPacket(Player player, Object packet);

    public abstract ProtocolVersion getProtocolVersion(Player player);

}
