package com.andrewtoasterr.damagefunction;

import com.andrewtoasterr.damagefunction.mixinEx.DamageSourceEx;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
// getString(ctx, "string")
import java.util.Collection;

// word()
// literal("foo")
import static net.minecraft.server.command.CommandManager.literal;
// argument("bar", word())
import static net.minecraft.server.command.CommandManager.argument;
// Import everything


public final class DamageCommand {
    public static final SuggestionProvider<ServerCommandSource> DAMAGE_SOURCE_SUGGESTION_PROVIDER = new DamageSourceSuggestionsProvider();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("damage").requires(src -> src.hasPermissionLevel(2))
                .then(argument("entity", EntityArgumentType.entities())
                        .then(argument("amount", FloatArgumentType.floatArg())
                                .executes(ctx -> {
                                    Collection<? extends Entity> ents = EntityArgumentType.getEntities(ctx, "entity");
                                    float damageAmount = FloatArgumentType.getFloat(ctx, "amount");
                                    return DamageEntities(ents, damageAmount, DamageSource.GENERIC, null);
                                })
                                .then(argument("attacker", EntityArgumentType.entity())
                                        .executes(ctx -> {
                                            Collection<? extends Entity> ents = EntityArgumentType.getEntities(ctx, "entity");
                                            float damageAmount = FloatArgumentType.getFloat(ctx, "amount");
                                            Entity attacker = EntityArgumentType.getEntity(ctx, "attacker");

                                            return DamageEntities(ents, damageAmount, DamageSource.GENERIC, attacker);
                                        })
                                )
                                .then(argument("damageSource", IdentifierArgumentType.identifier())
                                        .suggests(DAMAGE_SOURCE_SUGGESTION_PROVIDER)
                                        .executes(ctx -> {
                                            Collection<? extends Entity> ents = EntityArgumentType.getEntities(ctx, "entity");
                                            float damageAmount = FloatArgumentType.getFloat(ctx, "amount");
                                            Identifier sourceId = IdentifierArgumentType.getIdentifier(ctx, "damageSource");
                                            DamageSource dmgSource = DamageFunctionMod.DAMAGE_SOURCES.get(sourceId);

                                            if (dmgSource == null) {
                                                throw new InvalidIdentifierException("Specified damageSource was not found");
                                            }

                                            return DamageEntities(ents, damageAmount, dmgSource, null);
                                        })
                                        .then(argument("attacker", EntityArgumentType.entity())
                                                .executes(ctx -> {
                                                    Collection<? extends Entity> ents = EntityArgumentType.getEntities(ctx, "entity");
                                                    float damageAmount = FloatArgumentType.getFloat(ctx, "amount");
                                                    Identifier sourceId = IdentifierArgumentType.getIdentifier(ctx, "damageSource");
                                                    DamageSource dmgSource = DamageFunctionMod.DAMAGE_SOURCES.get(sourceId);

                                                    if (dmgSource == null) {
                                                        throw new InvalidIdentifierException("Specified damageSource was not found");
                                                    }

                                                    Entity attacker = EntityArgumentType.getEntity(ctx, "attacker");

                                                    return DamageEntities(ents, damageAmount, dmgSource, attacker);
                                                })
                                        )
                                )
                        )
                )
        );
    }

    public static int DamageEntities(Collection<? extends Entity> ents, float amount, DamageSource damageSource, Entity attacker) {
        int affectedCount = 0;
        for (Entity ent: ents) {
            if (attacker != null) {
                ((DamageSourceEx)damageSource).setCustomAttacker(attacker);
            }

            if (ent.damage(damageSource, amount)) {
                affectedCount++;
            }
        }

        return affectedCount;
    }
}
