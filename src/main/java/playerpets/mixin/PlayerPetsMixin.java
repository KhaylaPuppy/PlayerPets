package playerpets.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

import playerpets.accessor.PlayerPetsAccessor;

@Mixin(PlayerEntity.class)
public class PlayerPetsMixin implements PlayerPetsAccessor {

    private UUID playerpets$owner;

    @Override
    public UUID playerpets$getOwner() {
        return playerpets$owner;
    }

    @Override
    public void playerpets$setOwner(UUID owner) {
        this.playerpets$owner = owner;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeOwner(NbtCompound nbt, CallbackInfo ci) {
        if (playerpets$owner != null) {
            nbt.putUuid("Owner", playerpets$owner);

            System.out.println("New Owner: " + playerpets$owner);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readOwner(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.containsUuid("Owner")) {
            playerpets$owner = nbt.getUuid("Owner");

            System.out.println("Owner: " + playerpets$owner);
        } else {
            playerpets$owner = null;

            System.out.println("Owner: none");
        }
    }
}