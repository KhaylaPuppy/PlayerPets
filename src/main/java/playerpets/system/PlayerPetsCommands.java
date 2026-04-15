package playerpets.system;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import playerpets.accessor.PlayerPetsAccessor;

public class PlayerPetsCommands {

    public static void register() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            dispatcher.register(CommandManager.literal("petinfo")
                .executes(context -> {

                    ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
                    PlayerPetsAccessor acc = (PlayerPetsAccessor) player;

                    String owner = acc.playerpets$getOwner();
                    String pet = acc.playerpets$getPet();

                    if (owner != null) {
                        player.sendMessage(Text.literal("You are owned by: " + owner), false);
                    } else if (pet != null) {
                        player.sendMessage(Text.literal("Your pet: " + pet), false);
                    } else {
                        player.sendMessage(Text.literal("Unclaimed"), false);
                    }

                    return 1;
                })
            );

        });
    }
}