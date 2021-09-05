package com.andrewtoasterr.damagefunction.mixinEx;

import net.minecraft.entity.Entity;

public interface DamageSourceEx {
    Entity getCustomAttacker();
    void setCustomAttacker(Entity attacker);
}
