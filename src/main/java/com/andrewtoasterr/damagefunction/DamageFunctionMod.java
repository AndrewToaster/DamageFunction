package com.andrewtoasterr.damagefunction;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("Convert2MethodRef")
public class DamageFunctionMod implements ModInitializer {
	public static final Registry<Factory> DAMAGESOURCE_FACTORIES = FabricRegistryBuilder.createSimple(Factory.class, makeId("damagesource_factory_registry")).buildAndRegister();

	// Minecraft DamageSources
	public static final Factory IN_FIRE = registerFactory("inFire", x -> x.setBypassesArmor().setFire());
	public static final Factory LIGHTNING_BOLT = registerFactory("lightningBolt", x -> {});
	public static final Factory ON_FIRE = registerFactory("onFire", x -> x.setBypassesArmor().setFire());
	public static final Factory LAVA = registerFactory("lava", x -> x.setFire());
	public static final Factory HOT_FLOOR = registerFactory("hotFloor", x -> x.setFire());
	public static final Factory IN_WALL = registerFactory("inWall", x -> x.setBypassesArmor());
	public static final Factory CRAMMING = registerFactory("cramming", x -> x.setBypassesArmor());
	public static final Factory DROWN = registerFactory("drown", x -> x.setBypassesArmor());
	public static final Factory STARVE = registerFactory("starve", x -> x.setBypassesArmor().setUnblockable());
	public static final Factory CACTUS = registerFactory("cactus", x -> {});
	public static final Factory FALL = registerFactory("fall", x -> x.setBypassesArmor().setFromFalling());
	public static final Factory FLY_INTO_WALL = registerFactory("flyIntoWall", x -> x.setBypassesArmor());
	public static final Factory OUT_OF_WORLD = registerFactory("outOfWorld", x -> x.setBypassesArmor().setOutOfWorld());
	public static final Factory GENERIC = registerFactory("generic", x -> x.setBypassesArmor());
	public static final Factory MAGIC = registerFactory("magic", x -> x.setBypassesArmor().setUsesMagic());
	public static final Factory WITHER = registerFactory("wither", x -> x.setBypassesArmor());
	public static final Factory ANVIL = registerFactory("anvil", x -> x.setFallingBlock());
	public static final Factory FALLING_BLOCK = registerFactory("fallingBlock", x -> x.setFallingBlock());
	public static final Factory DRAGON_BREATH = registerFactory("dragonBreath", x -> x.setBypassesArmor());
	public static final Factory DRYOUT = registerFactory("dryout", x -> {});
	public static final Factory SWEET_BERRY_BUSH = registerFactory("sweetBerryBush", 	x -> {});
	public static final Factory FREEZE = registerFactory("freeze", x -> x.setBypassesArmor());
	public static final Factory FALLING_STALACTITE = registerFactory("fallingStalactite", x -> x.setFallingBlock());
	public static final Factory STALAGMITE = registerFactory("stalagmite", x -> x.setBypassesArmor().setFallingBlock());

	// Custom DamageSources
	public static final Factory NORMAL = registerFactory(makeId("normal"), () -> new PublicDamageSource("damagefunction_normal"));

	@Override
	public void onInitialize() {
		// Register the actual command
		CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> DamageCommand.register(dispatcher)));
	}

	private static Factory registerFactory(String id, Consumer<PublicDamageSource> modifier) {
		return registerFactory(makeIdBase(id.replaceAll("[A-Z]", "_$0").toLowerCase()), id, modifier);
	}

	private static Factory registerFactory(Identifier id, String name, Consumer<PublicDamageSource> modifier) {
		return registerFactory(id, () -> {
			PublicDamageSource src = new PublicDamageSource(name);
			modifier.accept(src);
			return src;
		});
	}

	private static Factory registerFactory(String id, Supplier<DamageSource> factory) {
		return registerFactory(makeIdBase(id), factory);
	}

	public static Factory registerFactory(Identifier id, Supplier<DamageSource> factory) {
		return Registry.register(DAMAGESOURCE_FACTORIES, id, new Factory(factory));
	}

	public static DamageSource getDamageSource(Identifier id) {
		Factory factory = DAMAGESOURCE_FACTORIES.get(id);
		if (factory == null) {
			return null;
		}
		return factory.get();
	}

	public static Identifier makeId(String path) {
		return new Identifier("damagecommand", path);
	}

	private static Identifier makeIdBase(String path) {
		return new Identifier("minecraft", path);
	}
}
