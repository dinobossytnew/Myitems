// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.command;

import com.praya.agarthalib.utility.*;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerCommand;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandLoreRemove extends HandlerCommand implements CommandExecutor {
    public CommandLoreRemove(final MyItems plugin) {
        super(plugin);
    }

    protected static final boolean removeLore(final CommandSender sender, final Command command, final String label, final String[] args) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!commandManager.checkPermission(sender, "Lore_Remove")) {
            final String permission = commandManager.getPermission("Lore_Remove");
            final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
            message.sendMessage(sender, "permission", permission);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        if (!SenderUtil.isPlayer(sender)) {
            final MessageBuild message2 = lang.getMessage(sender, "Console_Command_Forbiden");
            message2.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final Player player = PlayerUtil.parse(sender);
        final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
        if (!EquipmentUtil.isSolid(item)) {
            final MessageBuild message3 = lang.getMessage(sender, "Item_MainHand_Empty");
            message3.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        if (!EquipmentUtil.hasLore(item)) {
            final MessageBuild message3 = lang.getMessage(sender, "Item_Lore_Empty");
            message3.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        if (args.length < 1) {
            final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Remove"));
            final MessageBuild message4 = lang.getMessage(sender, "Argument_RemoveLore");
            message4.sendMessage(sender, "tooltip_lore_remove", tooltip);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final String textLine = args[0];
        if (!MathUtil.isNumber(textLine)) {
            final MessageBuild message4 = lang.getMessage(sender, "Argument_Invalid_Value");
            message4.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final int line = MathUtil.parseInteger(textLine);
        if (line < 1 || line > EquipmentUtil.getLores(item).size()) {
            final MessageBuild message5 = lang.getMessage(sender, "MyItems_RemoveLore_Lore_Correction");
            message5.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final MessageBuild message5 = lang.getMessage(sender, "MyItems_RemoveLore_Success");
        EquipmentUtil.removeLore(item, line);
        message5.sendMessage(sender, "line", String.valueOf(line));
        SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
        player.updateInventory();
        return true;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        return removeLore(sender, command, label, args);
    }
}
