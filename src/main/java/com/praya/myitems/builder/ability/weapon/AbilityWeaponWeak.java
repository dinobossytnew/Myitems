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

public class AbilityWeaponWeak extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeEffect {
    private static final String ABILITY_ID = "Weak";

    private AbilityWeaponWeak(final MyItems plugin, final String id) {
        super(plugin, id);
    }

    public static final AbilityWeaponWeak getInstance() {
        return AbilityWeaknessHelper.instance;
    }

    @Override
    public String getKeyLore() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getAbilityWeaponIdentifierWeakness();
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
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Weak");
        return abilityWeaponProperties.getMaxGrade();
    }

    @Override
    public double getBaseBonusDamage(final int grade) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Weak");
        final double baseBonusDamage = grade * abilityWeaponProperties.getScaleBaseBonusDamage();
        return baseBonusDamage;
    }

    @Override
    public double getBasePercentDamage(final int grade) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Weak");
        final double basePercentDamage = grade * abilityWeaponProperties.getScaleBasePercentDamage();
        return basePercentDamage;
    }

    @Override
    public int getEffectDuration(final int grade) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Weak");
        final int baseDuration = abilityWeaponProperties.getBaseDurationEffect();
        final int scaleDuration = abilityWeaponProperties.getScaleDurationEffect();
        int duration = abilityWeaponProperties.getTotalDuration(grade);
        for (int amplifier = 0; duration > baseDuration + scaleDuration * (7 + amplifier); duration -= scaleDuration * 2, ++amplifier) {
        }
        return duration;
    }

    private final int getEffectAmplifier(final int grade) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Weak");
        int baseDuration;
        int scaleDuration;
        int duration;
        int amplifier;
        for (baseDuration = abilityWeaponProperties.getBaseDurationEffect(), scaleDuration = abilityWeaponProperties.getScaleDurationEffect(), duration = abilityWeaponProperties.getTotalDuration(grade), amplifier = 0; duration > baseDuration + scaleDuration * (7 + amplifier); duration -= scaleDuration * 2, ++amplifier) {
        }
        return amplifier;
    }

    @Override
    public void cast(final Entity caster, final Entity target, final int grade, final double damage) {
        final MainConfig mainConfig = MainConfig.getInstance();
        if (target instanceof LivingEntity) {
            final LivingEntity victims = (LivingEntity) target;
            final Location location = victims.getEyeLocation();
            final int duration = this.getEffectDuration(grade);
            final int amplifier = this.getEffectAmplifier(grade);
            final Collection<Player> players = PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
            Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SWEEP_ATTACK, location, 25, 0.5, 0.5, 0.5, 0.5);
            Bridge.getBridgeSound().playSound(players, location, SoundEnum.BLOCK_ANVIL_BREAK, 0.7f, 1.0f);
            CombatUtil.applyPotion(victims, PotionEffectType.WEAKNESS, duration, amplifier);
        }
    }

    private static class AbilityWeaknessHelper {
        private static final AbilityWeaponWeak instance;

        static {
            final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
            instance = new AbilityWeaponWeak(plugin, "Weak");
        }
    }
}
