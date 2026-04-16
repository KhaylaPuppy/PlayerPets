package playerpets.system;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class PlayerPetsSit {

    private static boolean sitting = false;

    public static ActionResult handle(ServerPlayerEntity owner, ServerPlayerEntity target) {

        sitting = !sitting;

        if (sitting) {
            System.out.println("sit: sitting");
        } else {
            System.out.println("sit: standing");
        }

        return ActionResult.SUCCESS;
    }
}