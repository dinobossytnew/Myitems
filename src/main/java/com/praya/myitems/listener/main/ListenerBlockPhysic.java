// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class ListenerBlockPhysic extends HandlerEvent implements Listener {
    public ListenerBlockPhysic(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler
    public void eventBlockPhysic(final BlockPhysicsEvent event) {
        if (!event.isCancelled() && event.getBlock().hasMetadata("Anti_Block_Physic")) {
            event.setCancelled(true);
        }
    }
}
