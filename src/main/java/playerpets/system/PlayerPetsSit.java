package playerpets.system;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

import playerpets.accessor.PlayerPetsAccessor;

public class PlayerPetsSit {

    public static ActionResult handle(ServerPlayerEntity owner, ServerPlayerEntity target) {

        PlayerPetsAccessor pet = (PlayerPetsAccessor) target;

        boolean newState = !pet.playerpets$isSitting();
        pet.playerpets$setSitting(newState);

        if (newState) {
            System.out.println("sit: sitting " + target.getName().getString());
        } else {
            System.out.println("sit: standing " + target.getName().getString());
        }

        return ActionResult.SUCCESS;
    }
}