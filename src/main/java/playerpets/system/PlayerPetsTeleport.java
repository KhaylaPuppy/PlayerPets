package playerpets.system;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;

import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

public class PlayerPetsTeleport {

    private static final double TELEPORT_DISTANCE = 12.0;

    // cooldown in ticks (20 ticks = 1 second)
    private static final int TELEPORT_COOLDOWN = 20;

    // track last teleport time per pet
    private static final Map<UUID, Integer> lastTeleportTick = new HashMap<>();

    public static void register() {

        System.out.println("[PlayerPets] Teleport system loaded");

        ServerTickEvents.END_SERVER_TICK.register(PlayerPetsTeleport::tick);
    }

    private static void tick(MinecraftServer server) {

        int currentTick = server.getTicks();

        for (ServerPlayerEntity pet : server.getPlayerManager().getPlayerList()) {

            PlayerPetsAccessor petAcc = (PlayerPetsAccessor) pet;

            UUID petId = pet.getUuid();

            // ❌ cooldown check
            if (lastTeleportTick.containsKey(petId)) {
                int lastTick = lastTeleportTick.get(petId);
                if (currentTick - lastTick < TELEPORT_COOLDOWN) continue;
            }

            UUID ownerId = petAcc.playerpets$getOwner();
            if (ownerId == null) continue;

            ServerPlayerEntity owner = server.getPlayerManager().getPlayer(ownerId);
            if (owner == null) continue;

            // must be same dimension
            if (owner.getWorld() != pet.getWorld()) continue;

            // ❌ both must be properly standing
            if (!pet.isOnGround() || !owner.isOnGround()) continue;
            if (pet.hasVehicle() || owner.hasVehicle()) continue;
            if (pet.isTouchingWater() || owner.isTouchingWater()) continue;

            double distanceSq = pet.squaredDistanceTo(owner);

            if (distanceSq > TELEPORT_DISTANCE * TELEPORT_DISTANCE) {

                ServerWorld world = owner.getServerWorld();
                BlockPos base = owner.getBlockPos();

                BlockPos safePos = null;

                for (BlockPos pos : BlockPos.iterateOutwards(base, 2, 1, 2)) {

                    BlockPos below = pos.down();

                    BlockState belowState = world.getBlockState(below);
                    BlockState state = world.getBlockState(pos);
                    BlockState above = world.getBlockState(pos.up());

                    if (!belowState.isSolidBlock(world, below)) continue;
                    if (!state.isAir() || !above.isAir()) continue;

                    safePos = pos;
                    break;
                }

                if (safePos == null) continue;

                pet.refreshPositionAndAngles(
                        safePos.getX() + 0.5,
                        safePos.getY(),
                        safePos.getZ() + 0.5,
                        pet.getYaw(),
                        pet.getPitch()
                );

                pet.networkHandler.syncWithPlayerPosition();

                // ✅ set cooldown
                lastTeleportTick.put(petId, currentTick);
            }
        }
    }
}