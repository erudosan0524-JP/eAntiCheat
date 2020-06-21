package io.github.erudo.eac.protocol.api;

import io.github.erudo.eac.protocol.api.channel.ChannelInjector;
import lombok.Getter;

public class ProtocolHandler {
	
	@Getter
	private static ChannelInjector instance;

}
