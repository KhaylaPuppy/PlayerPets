package playerpets.accessor;

import java.util.UUID;

public interface PlayerPetsAccessor {

    UUID playerpets$getOwner();

    void playerpets$setOwner(UUID owner);
}