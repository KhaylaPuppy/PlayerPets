package playerpets.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

import playerpets.system.AccessorMixinMain;

@Mixin(PlayerEntity.class)
public class MixinMain implements AccessorMixinMain {

    private UUID playerpets$owner;
    private boolean playerpets$sitting = false;

    @Override
    public UUID playerpets$getOwner() {
        return playerpets$owner;
    }

    @Override
    public void playerpets$setOwner(UUID owner) {
        this.playerpets$owner = owner;
    }

    @Override
    public boolean playerpets$isSitting() {
        return playerpets$sitting;
    }

    @Override
    public void playerpets$setSitting(boolean sitting) {
        this.playerpets$sitting = sitting;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeOwner(NbtCompound nbt, CallbackInfo ci) {
        if (playerpets$owner != null) {
            nbt.putUuid("Owner", playerpets$owner);
        }
        nbt.putBoolean("Sitting", playerpets$sitting);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readOwner(NbtCompound nbt, CallbackInfo ci) {

        if (nbt.containsUuid("Owner")) {
            playerpets$owner = nbt.getUuid("Owner");
        } else {
            playerpets$owner = null;
        }

        playerpets$sitting = nbt.getBoolean("Sitting");
    }
}