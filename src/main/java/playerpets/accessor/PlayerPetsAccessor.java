package playerpets.accessor;

public interface PlayerPetsAccessor {
    void playerpets$setOwner(String name);
    String playerpets$getOwner();

    void playerpets$setPet(String name);
    String playerpets$getPet();

    void playerpets$setSitting(boolean sitting);
    boolean playerpets$isSitting();
}