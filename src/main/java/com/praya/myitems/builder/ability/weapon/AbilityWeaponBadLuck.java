// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeEffect;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import com.praya.agarthalib.utility.CombatUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.GameManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.List;

public class AbilityWeaponBadLuck extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeEffect {
    private static final String ABILITY_ID = "Bad_Luck";

    private AbilityWeaponBadLuck(final MyItems plugin, final String id) {
        super(plugin, id);
    }

    public static final AbilityWeaponBadLuck getInstance() {
        return AbilityWeaponBadLuckHelper.instance;
    }

    @Override
    public String getKeyLore() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getAbilityWeaponIdentifierBadLuck();
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
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bad_Luck");
        return abilityWeaponProperties.getMaxGrade();
    }

    @Override
    public double getBaseBonusDamage(final int grade) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bad_Luck");
        final double baseBonusDamage = grade * abilityWeaponProperties.getScaleBaseBonusDamage();
        return baseBonusDamage;
    }

    @Override
    public double getBasePercentDamage(final int grade) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bad_Luck");
        final double basePercentDamage = grade * abilityWeaponProperties.getScaleBasePercentDamage();
        return basePercentDamage;
    }

    @Override
    public int getEffectDuration(final int grade) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bad_Luck");
        return abilityWeaponProperties.getTotalDuration(grade);
    }

    @Override
    public void cast(final Entity caster, final Entity target, final int grade, final double damage) {
        final MainConfig mainConfig = MainConfig.getInstance();
        if (target instanceof LivingEntity) {
            final LivingEntity victims = (LivingEntity) target;
            final Location location = victims.getEyeLocation();
            final int duration = this.getEffectDuration(grade);
            final Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
            Bridge.getBridgeParticle().playParticle(players, ParticleEnum.VILLAGER_ANGRY, location, 25, 0.5, 0.5, 0.5, 0.5);
            Bridge.getBridgeSound().playSound(players, location, SoundEnum.ENTITY_VILLAGER_NO, 0.7f, 1.0f);
            CombatUtil.applyPotion(victims, PotionEffectType.UNLUCK, duration, grade);
        }
    }

    private static class AbilityWeaponBadLuckHelper {
        private static final AbilityWeaponBadLuck instance;

        static {
            final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
            instance = new AbilityWeaponBadLuck(plugin, "Bad_Luck");
        }
    }
}
