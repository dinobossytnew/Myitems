// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.passive.debuff;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.praya.agarthalib.utility.PotionUtil;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.entity.Player;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.builder.abs.PassiveEffect;

public class DebuffWeak extends PassiveEffect
{
    private static final PassiveEffectEnum debuff;
    
    static {
        debuff = PassiveEffectEnum.WEAK;
    }
    
    public DebuffWeak() {
        super(DebuffWeak.debuff, 1);
    }
    
    public DebuffWeak(final int grade) {
        super(DebuffWeak.debuff, grade);
    }
    
    @Override
    public final void cast(final Player player) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final PotionEffectType potionType = this.getPotion();
        final boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
    }
}
