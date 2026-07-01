package potatowolfie.arlo_the_little_guy.sound;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

public class ModSounds {
    public static final SoundEvent NO_ONE_WILL_BELIEVE_YOU = registerSoundEvent("no_one_will_believe_you");
    public static final SoundEvent APRIL_FOOLS_WHISPER = registerSoundEvent("april_fools_whisper");

    public static final SoundEvent CASSETTE_TAPE_INSERT = registerSoundEvent("cassette_tape_insert");
    public static final SoundEvent CASSETTE_TAPE_EJECT = registerSoundEvent("cassette_tape_eject");
    public static final SoundEvent CASSETTE_TAPE_1_AUDIO = registerSoundEvent("cassette_tape_1_audio");
    public static final SoundEvent CASSETTE_TAPE_2_AUDIO = registerSoundEvent("cassette_tape_2_audio");
    public static final SoundEvent CASSETTE_TAPE_3_AUDIO = registerSoundEvent("cassette_tape_3_audio");
    public static final SoundEvent CASSETTE_TAPE_4_AUDIO = registerSoundEvent("cassette_tape_4_audio");
    public static final SoundEvent CASSETTE_TAPE_5_AUDIO = registerSoundEvent("cassette_tape_5_audio");
    public static final SoundEvent CASSETTE_TAPE_6_AUDIO = registerSoundEvent("cassette_tape_6_audio");
    public static final SoundEvent CASSETTE_TAPE_7_AUDIO = registerSoundEvent("cassette_tape_7_audio");

    public static final SoundEvent ARLROOMS_AMBIENCE = registerSoundEvent("arlrooms_ambience");

    private static SoundEvent registerSoundEvent(String name) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name),
                SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name)));
    }

    public static void registerSounds() {
        ArloTheLittleGuy.LOGGER.info("Registering Mod Sounds for " + ArloTheLittleGuy.MOD_ID);
    }
}