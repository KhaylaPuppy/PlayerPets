package playerpets.system;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class PlayerPetsSit {

    public static ActionResult handle(ServerPlayerEntity owner, ServerPlayerEntity target) {

        AccessorMixinMain pet = (AccessorMixinMain) target;

        boolean newState = !pet.playerpets$isSitting();
        pet.playerpets$setSitting(newState);

        System.out.println(
                newState
                        ? "sit: sitting " + target.getName().getString()
                        : "sit: standing " + target.getName().getString()
        );

        return ActionResult.SUCCESS;
    }
}