package playerpets.system;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class PlayerPetsSit {

    public static ActionResult handle(ServerPlayerEntity owner, ServerPlayerEntity target) {

        boolean newState = !target.playerpets$isSitting();
        target.playerpets$setSitting(newState);

        if (newState) {
            System.out.println("sit: sitting " + target.getName().getString());
        } else {
            System.out.println("sit: standing " + target.getName().getString());
        }

        return ActionResult.SUCCESS;
    }
}
