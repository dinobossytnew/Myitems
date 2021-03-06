// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemSetManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ListenerInventoryDrag extends HandlerEvent implements Listener {
    public ListenerInventoryDrag(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void inventoryDragEvent(final InventoryDragEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        if (!event.isCancelled()) {
            final Player player = PlayerUtil.parse(event.getWhoClicked());
            final HashMap<Slot, ItemStack> mapItemSlot = new HashMap<Slot, ItemStack>();
            Slot[] values;
            for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
                final Slot slot = values[i];
                final ItemStack itemSlot = Bridge.getBridgeEquipment().getEquipment(player, slot);
                mapItemSlot.put(slot, itemSlot);
            }
            new BukkitRunnable() {
                public void run() {
                    final InventoryView inventoryView = player.getOpenInventory();
                    final InventoryType inventoryType = inventoryView.getType();
                    final Inventory inventory = inventoryType.equals(InventoryType.CREATIVE) ? null : inventoryView.getTopInventory();
                    Slot[] values;
                    for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
                        final Slot slot = values[i];
                        final ItemStack itemBefore = mapItemSlot.get(slot);
                        final ItemStack itemAfter = Bridge.getBridgeEquipment().getEquipment(player, slot);
                        final boolean isSolidBefore = itemBefore != null;
                        final boolean isSolidAfter = itemAfter != null;
                        if (isSolidBefore && isSolidAfter) {
                            if (itemBefore.equals(itemAfter)) {
                                continue;
                            }
                        } else if (isSolidBefore == isSolidAfter) {
                            continue;
                        }
                        if (itemSetManager.isItemSet(itemBefore) || itemSetManager.isItemSet(itemAfter)) {
                            itemSetManager.updateItemSet(player, true, inventory);
                            break;
                        }
                    }
                }
            }.runTaskLater(this.plugin, 0L);
        }
    }
}
