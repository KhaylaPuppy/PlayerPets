package playerpets.accessor;

import java.util.UUID;

public interface PlayerPetsAccessor {

    UUID playerpets$getOwner();
    void playerpets$setOwner(UUID owner);

    boolean playerpets$isSitting();
    void playerpets$setSitting(boolean sitting);
}