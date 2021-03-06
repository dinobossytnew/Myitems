// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.task;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.builder.task.TaskCustomEffect;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class TaskCustomEffectManager extends HandlerManager {
    private BukkitTask taskCustomEffect;

    protected TaskCustomEffectManager(final MyItems plugin) {
        super(plugin);
        this.reloadTaskCustomEffect();
    }

    public final void reloadTaskCustomEffect() {
        if (this.taskCustomEffect != null) {
            this.taskCustomEffect.cancel();
        }
        this.taskCustomEffect = this.createTaskCustomEffect();
    }

    private final BukkitTask createTaskCustomEffect() {
        final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        final Runnable runnable = new TaskCustomEffect(this.plugin);
        final int delay = 2;
        final int period = 1;
        final BukkitTask task = scheduler.runTaskTimer(this.plugin, runnable, 2L, 1L);
        return task;
    }
}
