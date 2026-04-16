package playerpets.system;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class PlayerPetsHeal {

    public static ActionResult handle(ServerPlayerEntity owner, ServerPlayerEntity target) {
        System.out.println("player healed");
        return ActionResult.SUCCESS;
    }
}