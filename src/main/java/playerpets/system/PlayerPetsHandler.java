package playerpets.system;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;

import playerpets.accessor.PlayerPetsAccessor;

import java.util.HashMap;
import java.util.Map;

public class PlayerPetsHandler {

    private static final Map<ServerPlayerEntity, Long> lastClick = new HashMap<>();
    private static final double TAMING_CHANCE = 0.33;

    public static void register() {

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {

            if (world.isClient) return ActionResult.PASS;
            if (hand != Hand.MAIN_HAND) return ActionResult.PASS;
            if (!(entity instanceof ServerPlayerEntity target)) return ActionResult.PASS;

            ServerPlayerEntity owner = (ServerPlayerEntity) player;

            PlayerPetsAccessor ownerAcc = (PlayerPetsAccessor) owner;
            PlayerPetsAccessor petAcc = (PlayerPetsAccessor) target;

            long now = world.getTime();
            Long last = lastClick.get(owner);
            if (last != null && last == now) return ActionResult.PASS;
            lastClick.put(owner, now);

            ItemStack held = owner.getMainHandStack();
            boolean hasBone = held.getItem() == Items.BONE;

            // 1. release
            if (isOwnerOf(ownerAcc, petAcc, owner, target)) {
                return handleRelease(ownerAcc, petAcc, target);
            }

            // 2. sit toggle
            if (isOwnerOf(ownerAcc, petAcc, owner, target)) {
                return handleSitToggle(petAcc);
            }

            // 3. claim
            if (!hasBone) return ActionResult.PASS;

            return handleClaim(ownerAcc, petAcc, owner, target, held);
        });
    }

    private static boolean isOwnerOf(PlayerPetsAccessor ownerAcc, PlayerPetsAccessor petAcc,
                                     ServerPlayerEntity owner, ServerPlayerEntity target) {

        return ownerAcc.playerpets$getPet() != null &&
               ownerAcc.playerpets$getPet().equals(target.getName().getString()) &&
               petAcc.playerpets$getOwner() != null &&
               petAcc.playerpets$getOwner().equals(owner.getName().getString());
    }

    private static ActionResult handleRelease(PlayerPetsAccessor ownerAcc,
                                              PlayerPetsAccessor petAcc,
                                              ServerPlayerEntity target) {

        ownerAcc.playerpets$setPet(null);
        petAcc.playerpets$setOwner(null);
        petAcc.playerpets$setSitting(false);

        spawnSmoke(target, 4);

        return ActionResult.SUCCESS;
    }

    private static ActionResult handleSitToggle(PlayerPetsAccessor petAcc) {

        boolean sitting = petAcc.playerpets$isSitting();
        petAcc.playerpets$setSitting(!sitting);

        return ActionResult.SUCCESS;
    }

    private static ActionResult handleClaim(PlayerPetsAccessor ownerAcc,
                                            PlayerPetsAccessor petAcc,
                                            ServerPlayerEntity owner,
                                            ServerPlayerEntity target,
                                            ItemStack held) {

        if (owner.equals(target)) return ActionResult.FAIL;
        if (ownerAcc.playerpets$getPet() != null) return ActionResult.FAIL;
        if (petAcc.playerpets$getOwner() != null) return ActionResult.FAIL;

        held.decrement(1);

        if (Math.random() < TAMING_CHANCE) {

            petAcc.playerpets$setOwner(owner.getName().getString());
            ownerAcc.playerpets$setPet(target.getName().getString());

            spawnHearts(target);

        } else {
            spawnSmoke(target, 6);
        }

        return ActionResult.SUCCESS;
    }

    private static void spawnHearts(ServerPlayerEntity target) {
        ((ServerWorld) target.getWorld()).spawnParticles(
            ParticleTypes.HEART,
            target.getX(),
            target.getY() + 1.0,
            target.getZ(),
            5,
            0.3, 0.3, 0.3,
            0.0
        );
    }

    private static void spawnSmoke(ServerPlayerEntity target, int count) {
        ((ServerWorld) target.getWorld()).spawnParticles(
            ParticleTypes.SMOKE,
            target.getX(),
            target.getY() + 1.0,
            target.getZ(),
            count,
            0.2, 0.2, 0.2,
            0.02
        );
    }
}