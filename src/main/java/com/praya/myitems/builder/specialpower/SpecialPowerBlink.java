// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.specialpower;

import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.agarthalib.utility.LocationUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.builder.abs.SpecialPower;
import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;

public class SpecialPowerBlink extends SpecialPower {
    private static final PowerSpecialEnum special;

    static {
        special = PowerSpecialEnum.BLINK;
    }

    public SpecialPowerBlink() {
        super(SpecialPowerBlink.special);
    }

    @Override
    public final void cast(final LivingEntity caster) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final Location locationCasterEye = caster.getEyeLocation();
        final Location locationBlink = LocationUtil.getLineBlock(locationCasterEye, 20, 20.0);
        final double height = caster.getEyeHeight();
        final Collection<Player> players = PlayerUtil.getNearbyPlayers(locationCasterEye, mainConfig.getEffectRange());
        locationBlink.setYaw(locationCasterEye.getYaw());
        locationBlink.setPitch(locationCasterEye.getPitch());
        locationBlink.subtract(0.0, height, 0.0);
        if (locationBlink.getBlock().getType().isSolid()) {
            locationBlink.add(0.0, height, 0.0);
        }
        caster.teleport(locationBlink);
        Bridge.getBridgeParticle().playParticle(players, ParticleEnum.PORTAL, locationBlink, 25, 0.5, 0.25, 0.5);
        Bridge.getBridgeSound().playSound(players, locationBlink, SoundEnum.BLOCK_PORTAL_TRAVEL, 0.6f, 1.0f);
    }
}
