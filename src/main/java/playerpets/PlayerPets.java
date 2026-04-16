package playerpets;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import playerpets.system.PlayerPetsCommands;
import playerpets.system.PlayerPetsHandler;
import playerpets.system.PlayerPetsTeleport;

public class PlayerPets implements ModInitializer {
	public static final String MOD_ID = "playerpets";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Arf Bark UwU OwO Mrrp Mew :3");
		LOGGER.info("English Translation: PlayerPets Initialized :3");

		PlayerPetsCommands.register();
		PlayerPetsHandler.register();
		PlayerPetsTeleport.register();
	}
}