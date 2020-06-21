package io.github.erudo.eac.protocol.api.channel.reflections;

import org.bukkit.Bukkit;

public class Reflections {

	//CB = craft Bukkit
	//NMS = net Minecraft Server
	private static final String CBString;
	private static final String NMSString;

	static { //パッケージ化されたCraftBukkit,Spigotのクラスやメソッドを取得するため
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		CBString = "org.bukkit.craftbukkit." + version + ".";
		NMSString = "net.minecraft.server." + version + ".";
	}

	public static boolean classExists(String name) {
		try {
			Class.forName(name); //指定したクラスのClassクラスを取得する
			return true;
		} catch(ClassNotFoundException e) {
			return false;
		}
	}

	public static WrappedClass getCBClass(String name) {
		return getClass(CBString + name);
	}

	public static WrappedClass getNMSClass(String name) {
		return getClass(NMSString + name);
	}

	public static WrappedClass getClass(String name) {
		try {
			return new WrappedClass(Class.forName(name));
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static WrappedClass getClass(Class<?> clazz) {
		return new WrappedClass(clazz);
	}
}
