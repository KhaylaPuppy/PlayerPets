package playerpets.system;

import java.util.UUID;

public interface AccessorMixinMain {

    UUID playerpets$getOwner();
    void playerpets$setOwner(UUID owner);

    boolean playerpets$isSitting();
    void playerpets$setSitting(boolean sitting);

}