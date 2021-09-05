package com.andrewtoasterr.damagefunction;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;

public class DamageFunctionMod implements ModInitializer {
	public static Registry<DamageSource> DAMAGE_SOURCES;

	@Override
	public void onInitialize() {
		DAMAGE_SOURCES = FabricRegistryBuilder.createSimple(DamageSource.class, makeId("damagesource_registry")).buildAndRegister();

		// Register the default minecraft damage sources
		Registry.register(DAMAGE_SOURCES, makeIdBase("anvil"), DamageSource.ANVIL);
		Registry.register(DAMAGE_SOURCES, makeIdBase("cramming"), DamageSource.CRAMMING);
		Registry.register(DAMAGE_SOURCES, makeIdBase("dragon_breath"), DamageSource.DRAGON_BREATH);
		Registry.register(DAMAGE_SOURCES, makeIdBase("drown"), DamageSource.DROWN);
		Registry.register(DAMAGE_SOURCES, makeIdBase("dryout"), DamageSource.DRYOUT);
		Registry.register(DAMAGE_SOURCES, makeIdBase("falling_block"), DamageSource.FALLING_BLOCK);
		Registry.register(DAMAGE_SOURCES, makeIdBase("falling_stalactite"), DamageSource.FALLING_STALACTITE);
		Registry.register(DAMAGE_SOURCES, makeIdBase("fly_into_wall"), DamageSource.FLY_INTO_WALL);
		Registry.register(DAMAGE_SOURCES, makeIdBase("freeze"), DamageSource.FREEZE);
		Registry.register(DAMAGE_SOURCES, makeIdBase("generic"), DamageSource.GENERIC);
		Registry.register(DAMAGE_SOURCES, makeIdBase("hot_floor"), DamageSource.HOT_FLOOR);
		Registry.register(DAMAGE_SOURCES, makeIdBase("in_fire"), DamageSource.IN_FIRE);
		Registry.register(DAMAGE_SOURCES, makeIdBase("in_wall"), DamageSource.IN_WALL);
		Registry.register(DAMAGE_SOURCES, makeIdBase("lava"), DamageSource.LAVA);
		Registry.register(DAMAGE_SOURCES, makeIdBase("lightning_bolt"), DamageSource.LIGHTNING_BOLT);
		Registry.register(DAMAGE_SOURCES, makeIdBase("magic"), DamageSource.MAGIC);
		Registry.register(DAMAGE_SOURCES, makeIdBase("on_fire"), DamageSource.ON_FIRE);
		Registry.register(DAMAGE_SOURCES, makeIdBase("out_of_world"), DamageSource.OUT_OF_WORLD);
		Registry.register(DAMAGE_SOURCES, makeIdBase("stalagmite"), DamageSource.STALAGMITE);
		Registry.register(DAMAGE_SOURCES, makeIdBase("starve"), DamageSource.STARVE);
		Registry.register(DAMAGE_SOURCES, makeIdBase("sweet_berry_bush"), DamageSource.SWEET_BERRY_BUSH);
		Registry.register(DAMAGE_SOURCES, makeIdBase("wither"), DamageSource.WITHER);

		// Register the actual command
		CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> DamageCommand.register(dispatcher)));
	}

	public final Identifier makeId(String path) {
		return new Identifier("damagecommand", path);
	}

	private Identifier makeIdBase(String path) {
		return new Identifier("minecraft", path);
	}
}
