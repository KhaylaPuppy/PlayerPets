package playerpets.system;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.UUID;

import playerpets.accessor.PlayerPetsAccessor;

public class PlayerPetsTeleport {

    private static final double TELEPORT_DISTANCE = 12.0;

    public static void register() {

        System.out.println("[PlayerPets] Teleport system loaded");

        ServerTickEvents.END_SERVER_TICK.register(PlayerPetsTeleport::tick);
    }

    private static void tick(MinecraftServer server) {

        for (ServerPlayerEntity pet : server.getPlayerManager().getPlayerList()) {

            PlayerPetsAccessor petAcc = (PlayerPetsAccessor) pet;

            UUID ownerId = petAcc.playerpets$getOwner();
            if (ownerId == null) continue;

            // ❌ sitting pets do not move
            if (petAcc.playerpets$isSitting()) continue;

            ServerPlayerEntity owner = server.getPlayerManager().getPlayer(ownerId);
            if (owner == null) continue;

            // must be same dimension
            if (owner.getWorld() != pet.getWorld()) continue;

            double distanceSq = pet.squaredDistanceTo(owner);

            if (distanceSq > TELEPORT_DISTANCE * TELEPORT_DISTANCE) {

                ServerWorld world = owner.getServerWorld();

                pet.teleport(
                        world,
                        owner.getX(),
                        owner.getY(),
                        owner.getZ(),
                        pet.getYaw(),
                        pet.getPitch()
                );

                System.out.println("Pet teleported: "
                        + pet.getName().getString()
                        + " -> "
                        + owner.getName().getString());
            }
        }
    }
}