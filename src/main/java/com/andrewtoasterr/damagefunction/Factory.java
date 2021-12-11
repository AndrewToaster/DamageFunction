package com.andrewtoasterr.damagefunction;

import net.minecraft.entity.damage.DamageSource;

import java.util.function.Supplier;

public class Factory implements Supplier<DamageSource> {

    private final Supplier<DamageSource> _factory;

    public Factory(Supplier<DamageSource> factory) {
        _factory = factory;
    }

    @Override
    public DamageSource get() {
        return _factory.get();
    }
}
