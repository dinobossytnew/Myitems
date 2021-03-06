// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeEffect;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;
import com.praya.agarthalib.utility.BlockUtil;
import com.praya.agarthalib.utility.CombatUtil;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.utility.main.CustomEffectUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityWeaponFreeze extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeEffect {
    private static final String ABILITY_ID = "Freeze";

    private AbilityWeaponFreeze(final MyItems plugin, final String id) {
        super(plugin, id);
    }

    public static final AbilityWeaponFreeze getInstance() {
        return AbilityFreezeHelper.instance;
    }

    @Override
    public String getKeyLore() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getAbilityWeaponIdentifierFreeze();
    }

    @Override
    public List<String> getDescription() {
        return null;
    }

    @Override
    public int getMaxGrade() {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Freeze");
        return abilityWeaponProperties.getMaxGrade();
    }

    @Override
    public double getBaseBonusDamage(final int grade) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Freeze");
        final double baseBonusDamage = grade * abilityWeaponProperties.getScaleBaseBonusDamage();
        return baseBonusDamage;
    }

    @Override
    public double getBasePercentDamage(final int grade) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Freeze");
        final double basePercentDamage = grade * abilityWeaponProperties.getScaleBasePercentDamage();
        return basePercentDamage;
    }

    @Override
    public int getEffectDuration(final int grade) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Freeze");
        return abilityWeaponProperties.getTotalDuration(grade);
    }

    @Override
    public void cast(final Entity caster, final Entity target, final int grade, final double damage) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        LivingEntity attacker;
        if (caster instanceof Projectile) {
            final Projectile projectile = (Projectile) caster;
            final ProjectileSource projectileSource = projectile.getShooter();
            if (projectileSource == null || !(projectileSource instanceof LivingEntity)) {
                return;
            }
            attacker = (LivingEntity) projectileSource;
        } else {
            attacker = (LivingEntity) caster;
        }
        if (target instanceof LivingEntity) {
            final LivingEntity victims = (LivingEntity) target;
            final Location location = victims.getLocation();
            final int duration = this.getEffectDuration(grade);
            final long milis = duration * 50;
            final double seconds = duration / 20;
            final MessageBuild messageAttacker = lang.getMessage(attacker, "Ability_Freeze_Attacker");
            final MessageBuild messageVictims = lang.getMessage(victims, "Ability_Freeze_Victims");
            final Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
            final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
            mapPlaceholder.put("seconds", String.valueOf(MathUtil.roundNumber(seconds)));
            victims.setVelocity(victims.getVelocity().multiply(0.0));
            messageAttacker.sendMessage(attacker, mapPlaceholder);
            messageVictims.sendMessage(victims, mapPlaceholder);
            Bridge.getBridgeParticle().playParticle(players, ParticleEnum.CLOUD, location, 10, 0.25, 0.25, 0.25, 0.10000000149011612);
            Bridge.getBridgeSound().playSound(players, location, SoundEnum.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
            if (victims instanceof Player) {
                CustomEffectUtil.setCustomEffect(victims, milis, PassiveEffectTypeEnum.FREEZE);
            } else {
                CombatUtil.applyPotion(victims, PotionEffectType.SLOW, duration, 100, true, mainConfig.isMiscEnableParticlePotion());
            }
            new BukkitRunnable() {
                public void run() {
                    final Collection<Location> locationIce = new ArrayList<Location>();
                    final Location location = victims.getLocation();
                    for (int i = 0; i < 2; ++i) {
                        if (i > 0) {
                            location.add(0.0, 1.0, 0.0);
                        }
                        final Block block = location.getBlock();
                        final Material material = block.getType();
                        if (material.equals(Material.AIR)) {
                            final Location locationBlock = block.getLocation();
                            locationIce.add(locationBlock);
                            block.setType(Material.PACKED_ICE);
                            BlockUtil.set(locationBlock);
                        }
                    }
                    new BukkitRunnable() {
                        public void run() {
                            for (final Location location : locationIce) {
                                final Block block = location.getBlock();
                                final Material material = block.getType();
                                final Location locationBlock = block.getLocation();
                                BlockUtil.remove(locationBlock);
                                if (material.equals(Material.PACKED_ICE)) {
                                    block.setType(Material.AIR);
                                }
                            }
                        }
                    }.runTaskLater(plugin, duration);
                }
            }.runTaskLater(plugin, 1L);
        }
    }

    private static class AbilityFreezeHelper {
        private static final AbilityWeaponFreeze instance;

        static {
            final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
            instance = new AbilityWeaponFreeze(plugin, "Freeze");
        }
    }
}
