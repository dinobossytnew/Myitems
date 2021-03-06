// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.player;

import api.praya.myitems.builder.player.PlayerPassiveEffectCooldown;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.UUID;

public class PlayerPassiveEffectManager extends HandlerManager {
    private final HashMap<UUID, PlayerPassiveEffectCooldown> mapPassiveEffectCooldown;

    protected PlayerPassiveEffectManager(final MyItems plugin) {
        super(plugin);
        this.mapPassiveEffectCooldown = new HashMap<UUID, PlayerPassiveEffectCooldown>();
    }

    public final PlayerPassiveEffectCooldown getPlayerPassiveEffectCooldown(final OfflinePlayer player) {
        final UUID uuid = player.getUniqueId();
        if (!this.mapPassiveEffectCooldown.containsKey(uuid)) {
            this.mapPassiveEffectCooldown.put(uuid, new PlayerPassiveEffectCooldown());
        }
        return this.mapPassiveEffectCooldown.get(uuid);
    }

    public final void removePlayerPassiveEffectCooldown(final OfflinePlayer player) {
        final UUID uuid = player.getUniqueId();
        this.mapPassiveEffectCooldown.remove(uuid);
    }
}
