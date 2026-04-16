package playerpets.system;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.particle.ParticleTypes;

import java.util.UUID;

import playerpets.accessor.PlayerPetsAccessor;

public class PlayerPetsTame {

    private static final double TAMING_CHANCE = 0.33;

    public static ActionResult handle(
            ServerPlayerEntity owner,
            ServerPlayerEntity target,
            ItemStack held
    ) {

        PlayerPetsAccessor pet = (PlayerPetsAccessor) target;

        if (owner.equals(target)) {
            System.out.println("Tame blocked: you cannot tame yourself");
            return ActionResult.FAIL;
        }

        // only check ONE direction: is pet already owned?
        if (pet.playerpets$getOwner() != null) {
            System.out.println("Tame blocked: pet is already owned");
            return ActionResult.FAIL;
        }

        held.decrement(1);

        if (owner.getRandom().nextDouble() < TAMING_CHANCE) {

            UUID ownerId = owner.getUuid();
            pet.playerpets$setOwner(ownerId);

            System.out.println("Tame success: "
                    + owner.getName().getString() + " -> "
                    + target.getName().getString());

            ((ServerWorld) target.getWorld()).spawnParticles(
                    ParticleTypes.HEART,
                    target.getX(),
                    target.getY() + 1.0,
                    target.getZ(),
                    5,
                    0.3, 0.3, 0.3,
                    0.0
            );

        } else {

            System.out.println("Tame failed: "
                    + owner.getName().getString() + " -> "
                    + target.getName().getString());

            ((ServerWorld) target.getWorld()).spawnParticles(
                    ParticleTypes.SMOKE,
                    target.getX(),
                    target.getY() + 1.0,
                    target.getZ(),
                    6,
                    0.2, 0.2, 0.2,
                    0.02
            );
        }

        return ActionResult.SUCCESS;
    }
}