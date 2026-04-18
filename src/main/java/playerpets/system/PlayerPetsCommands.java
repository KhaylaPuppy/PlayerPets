package playerpets.system;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import playerpets.system.AccessorMixinMain;

public class PlayerPetsCommands {

    static {
        System.out.println("[PlayerPets] PlayerPetsCommands loaded");
    }

    public static void register() {

        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> {
                    registerCommands(dispatcher);
                }
        );
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(
                CommandManager.literal("playerpets")

                        // /playerpets runaway
                        .then(CommandManager.literal("runaway")
                                .executes(ctx -> {

                                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                                    AccessorMixinMain pet = (AccessorMixinMain) player;

                                    if (pet.playerpets$getOwner() == null) {

                                        ctx.getSource().sendFeedback(
                                                () -> Text.literal("You are already free."),
                                                false
                                        );

                                        System.out.println("NBT data already empty "
                                                + player.getName().getString());

                                        return 1;
                                    }

                                    pet.playerpets$setOwner(null);

                                    ctx.getSource().sendFeedback(
                                            () -> Text.literal("You ran away. You are now free."),
                                            false
                                    );

                                    System.out.println("NBT data cleared "
                                            + player.getName().getString());

                                    return 1;
                                })
                        )

                        // /playerpets owner
                        .then(CommandManager.literal("owner")
                                .executes(ctx -> {

                                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                                    AccessorMixinMain pet = (AccessorMixinMain) player;

                                    if (pet.playerpets$getOwner() == null) {

                                        ctx.getSource().sendFeedback(
                                                () -> Text.literal("You are not owned."),
                                                false
                                        );

                                        System.out.println("No NBT data "
                                                + player.getName().getString());

                                        return 1;
                                    }

                                    String ownerId = pet.playerpets$getOwner().toString();

                                    ctx.getSource().sendFeedback(
                                            () -> Text.literal("Your owner is: " + ownerId),
                                            false
                                    );

                                    System.out.println("NBT Data: "
                                            + player.getName().getString()
                                            + " owned by " + ownerId);

                                    return 1;
                                })
                        )
        );
    }
}