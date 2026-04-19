package potatowolfie.arlo_the_little_guy.sound;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

public class ModSounds {
    public static final SoundEvent NO_ONE_WILL_BELIEVE_YOU = registerSoundEvent("no_one_will_believe_you");
    public static final SoundEvent APRIL_FOOLS_WHISPER = registerSoundEvent("april_fools_whisper");

    private static SoundEvent registerSoundEvent(String name) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name),
                SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name)));
    }

    public static void registerSounds() {
        ArloTheLittleGuy.LOGGER.info("Registering Mod Sounds for " + ArloTheLittleGuy.MOD_ID);
    }
}