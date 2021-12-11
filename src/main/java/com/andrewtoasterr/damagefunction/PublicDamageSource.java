package com.andrewtoasterr.damagefunction;

import net.minecraft.entity.damage.DamageSource;

public class PublicDamageSource extends DamageSource {
    public PublicDamageSource(String name) {
        super(name);
    }

    @Override
    public PublicDamageSource setBypassesArmor() {
        super.setBypassesArmor();
        return this;
    }

    @Override
    public PublicDamageSource setFallingBlock() {
        super.setFallingBlock();
        return this;
    }

    @Override
    public PublicDamageSource setFire() {
        super.setFire();
        return this;
    }

    @Override
    public PublicDamageSource setOutOfWorld() {
        super.setOutOfWorld();
        return this;
    }

    @Override
    public PublicDamageSource setUnblockable() {
        super.setUnblockable();
        return this;
    }
}
