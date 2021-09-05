package com.andrewtoasterr.damagefunction.mixin;

import com.andrewtoasterr.damagefunction.mixinEx.DamageSourceEx;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageSource.class)
public class DamageSourceMixin implements DamageSourceEx {
    private Entity customAttacker = null;

    @Inject(at = @At("HEAD"), method = "getAttacker", cancellable = true)
    public void getAttacker(CallbackInfoReturnable<Entity> cir) {
        Entity attacker = getCustomAttacker();
        if (attacker != null)
        {
            cir.setReturnValue(attacker);
        }
    }

    //@Override
    public Entity getCustomAttacker() {
        return customAttacker;
    }

    //@Override
    public void setCustomAttacker(Entity attacker) {
        customAttacker = attacker;
    }
}
