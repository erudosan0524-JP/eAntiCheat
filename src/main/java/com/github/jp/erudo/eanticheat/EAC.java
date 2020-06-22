package com.github.jp.erudo.eanticheat;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jp.erudo.eanticheat.event.EventManager;
import com.github.jp.erudo.eanticheat.listener.BukkitEvents;
import com.github.jp.erudo.eanticheat.listener.PacketListener;
import com.github.jp.erudo.eanticheat.utils.RunUtils;
import com.github.jp.erudo.eanticheat.utils.VersionUtil;
import com.github.jp.erudo.eanticheat.utils.boundingbox.box.BlockBoxManager;
import com.github.jp.erudo.eanticheat.utils.boundingbox.box.utils.BoundingBoxes;
import com.github.jp.erudo.eanticheat.utils.reflection.CraftReflection;
import com.github.jp.erudo.eanticheat.utils.user.UserManager;

import io.github.erudo.eac.protocol.api.ProtocolHandler;
import io.github.erudo.eac.protocol.api.channel.reflections.Reflections;
import io.github.erudo.eac.protocol.api.channel.reflections.WrappedField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EAC extends JavaPlugin {

	//checkの内容はすべてパケットによるイベントで行う。

	@Getter
	public static EAC instance;
	public static UserManager userManager;
	public static String bukkitVersion;
	public static VersionUtil versionUtil;
	private EventManager eventManager;
	private ScheduledExecutorService executorService;
    private BlockBoxManager blockBoxManager;
    private BoundingBoxes boxes;
    private ProtocolHandler protocolHandler;
    private Map<UUID, List<Entity>> entities = new ConcurrentHashMap<>();
    private WrappedField entityList = Reflections.getNMSClass("World").getFieldByName("entityList");



	public static final String pluginName = "eAntiCheat";

	@Override
	public void onDisable() {
		getLogger().info("プラグインが停止しました。");
	}

	@Override
	public void onEnable() {
		getLogger().info("プラグインが起動しました。");

		instance = this;
		userManager = new UserManager();
		versionUtil = new VersionUtil();

		protocolHandler = new ProtocolHandler();

		bukkitVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);

		executorService = Executors.newSingleThreadScheduledExecutor();

		//Event登録
		getServer().getPluginManager().registerEvents(new BukkitEvents(), this);
		eventManager.registerListeners(new PacketListener(), this);

		Bukkit.getOnlinePlayers().forEach(player -> ProtocolHandler.getInstance().addChannel(player));


		//boundingbox関連(ブロックやエンティティの当たり判定のこと)
		this.blockBoxManager = new BlockBoxManager();
        this.boxes = new BoundingBoxes();

        RunUtils.taskTimer(() -> {
            for (World world : Bukkit.getWorlds()) {
                Object vWorld = CraftReflection.getVanillaWorld(world);

                List<Object> vEntities = Collections.synchronizedList(EAC.getInstance().getEntityList().get(vWorld));

                List<Entity> bukkitEntities = vEntities.parallelStream().map(CraftReflection::getBukkitEntity).collect(Collectors.toList());

                EAC.getInstance().getEntities().put(world.getUID(), bukkitEntities);
            }
        }, 2L, 2L);

	}

}
