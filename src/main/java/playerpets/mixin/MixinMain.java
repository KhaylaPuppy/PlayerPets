package playerpets.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

import playerpets.system.AccessorMixinMain;

@Mixin(PlayerEntity.class)
public class MixinMain implements AccessorMixinMain {

    private UUID playerpets$owner;
    private boolean playerpets$sitting = false; // important default

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
            System.out.println("New Owner: " + playerpets$owner);
        }

        nbt.putBoolean("Sitting", playerpets$sitting);
        System.out.println("Saved Sitting: " + playerpets$sitting);
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

        if (nbt.contains("Sitting")) {
            playerpets$sitting = nbt.getBoolean("Sitting");
        } else {
            playerpets$sitting = false; // important fallback
        }

        System.out.println("Loaded Sitting: " + playerpets$sitting);
    }
}