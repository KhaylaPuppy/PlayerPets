package playerpets.system;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import playerpets.accessor.PlayerPetsAccessor;

public class PlayerPetsHandler {

    private static final Map<ServerPlayerEntity, Long> lastClick = new HashMap<>();

    public static void register() {

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {

            if (world.isClient) return ActionResult.PASS;
            if (hand != Hand.MAIN_HAND) return ActionResult.PASS;
            if (!(entity instanceof ServerPlayerEntity target)) return ActionResult.PASS;

            ServerPlayerEntity owner = (ServerPlayerEntity) player;

            long now = world.getTime();
            Long last = lastClick.get(owner);
            if (last != null && last == now) return ActionResult.PASS;
            lastClick.put(owner, now);

            ItemStack held = owner.getMainHandStack();
            boolean hasBone = held.getItem() == Items.BONE;

            PlayerPetsAccessor pet = (PlayerPetsAccessor) target;

            UUID targetOwner = pet.playerpets$getOwner();

            // OWNER OF THIS PET
            if (targetOwner != null && targetOwner.equals(owner.getUuid())) {

                if (held.getItem().isFood()) {
                    return PlayerPetsHeal.handle(owner, target);
                }

                if (held.isOf(Items.NAME_TAG)) {
                    return PlayerPetsNametag.handle(owner, target);
                }

                return PlayerPetsSit.handle(owner, target);
            }

            // NON-OWNER INTERACTION
            if (!hasBone) return ActionResult.PASS;

            if (targetOwner != null) return ActionResult.PASS;

            return PlayerPetsTame.handle(owner, target, held);
        });
    }
}