package playerpets.system;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class PlayerPetsNametag {

    public static ActionResult handle(ServerPlayerEntity owner, ServerPlayerEntity target) {
        System.out.println("name applied");
        return ActionResult.SUCCESS;
    }
}