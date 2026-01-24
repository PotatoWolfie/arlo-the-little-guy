package potatowolfie.arlo_the_little_guy.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

public class ModSounds {
    public static final SoundEvent NO_ONE_WILL_BELIEVE_YOU = registerSoundEvent("no_one_will_believe_you");
    public static final SoundEvent APRIL_FOOLS_WHISPER = registerSoundEvent("april_fools_whisper");

    private static SoundEvent registerSoundEvent(String name) {
        return Registry.register(Registries.SOUND_EVENT, Identifier.of(ArloTheLittleGuy.MOD_ID, name),
                SoundEvent.of(Identifier.of(ArloTheLittleGuy.MOD_ID, name)));
    }

    public static void registerSounds() {
        ArloTheLittleGuy.LOGGER.info("Registering Mod Sounds for " + ArloTheLittleGuy.MOD_ID);
    }
}