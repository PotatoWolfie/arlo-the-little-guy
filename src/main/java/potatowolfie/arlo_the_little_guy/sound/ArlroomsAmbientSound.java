package potatowolfie.arlo_the_little_guy.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

public class ArlroomsAmbientSound extends AbstractTickableSoundInstance {

    public ArlroomsAmbientSound() {
        super(ModSounds.ARLROOMS_AMBIENCE, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        this.looping = true;
        this.delay = 0;
        this.volume = 0.8f;
        this.pitch = 1.0f;
        this.relative = true;
    }

    @Override
    public void tick() {}

    public void stopSound() {
        stop();
    }
}