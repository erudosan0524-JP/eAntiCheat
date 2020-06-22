package com.github.jp.erudo.eanticheat.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import com.github.jp.erudo.eanticheat.EAC;

public class RunUtils {
	 public static BukkitTask taskTimer(Runnable runnable, Plugin plugin, long delay, long interval) {
	        return Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, interval);
	    }

	    public static BukkitTask taskTimer(Runnable runnable, long delay, long interval) {
	        return taskTimer(runnable, EAC.instance, delay, interval);
	    }

	    public static BukkitTask taskTimerAsync(Runnable runnable, Plugin plugin, long delay, long interval) {
	        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, interval);
	    }

	    public static BukkitTask taskTimerAsync(Runnable runnable, long delay, long interval) {
	        return taskTimerAsync(runnable, EAC.instance, delay, interval);
	    }

	    public static BukkitTask task(Runnable runnable, EAC plugin) {
	        return Bukkit.getScheduler().runTask(plugin, runnable);
	    }

	    public static BukkitTask task(Runnable runnable) {
	        return task(runnable, EAC.instance);
	    }

	    public static BukkitTask taskAsync(Runnable runnable, EAC plugin) {
	        return Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
	    }

	    public static BukkitTask taskLater(Runnable runnable, EAC plugin, long delay) {
	        return Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
	    }


	    public static BukkitTask taskLaterAsync(Runnable runnable, EAC plugin, long delay) {
	        return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
	    }

}
