package playerpets.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import playerpets.accessor.PlayerPetsAccessor;

@Mixin(PlayerEntity.class)
public class PlayerPetsMixin implements PlayerPetsAccessor {

    private String playerpets$owner = null;
    private String playerpets$pet = null;
    private boolean playerpets$sitting = false;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeOwner(NbtCompound nbt, CallbackInfo ci) {

        if (playerpets$owner != null) {
            nbt.putString("Owner", playerpets$owner);
        }

        if (playerpets$pet != null) {
            nbt.putString("Pet", playerpets$pet);
        }

        nbt.putBoolean("Sit", playerpets$sitting);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readOwner(NbtCompound nbt, CallbackInfo ci) {

        if (nbt.contains("Owner")) {
            playerpets$owner = nbt.getString("Owner");
        }

        if (nbt.contains("Pet")) {
            playerpets$pet = nbt.getString("Pet");
        }

        if (nbt.contains("Sit")) {
            playerpets$sitting = nbt.getBoolean("Sit");
        }
    }

    @Override
    public String playerpets$getOwner() {
        return playerpets$owner;
    }

    @Override
    public void playerpets$setOwner(String owner) {
        this.playerpets$owner = owner;
    }

    @Override
    public String playerpets$getPet() {
        return playerpets$pet;
    }

    @Override
    public void playerpets$setPet(String pet) {
        this.playerpets$pet = pet;
    }

    @Override
    public void playerpets$setSitting(boolean sitting) {
        this.playerpets$sitting = sitting;
    }

    @Override
    public boolean playerpets$isSitting() {
        return playerpets$sitting;
    }
}