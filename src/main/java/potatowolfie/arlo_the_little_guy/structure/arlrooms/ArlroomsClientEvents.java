package potatowolfie.arlo_the_little_guy.structure.arlrooms;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import potatowolfie.arlo_the_little_guy.sound.ArlroomsAmbientSound;
import potatowolfie.arlo_the_little_guy.world.dimension.ModDimensions;

@Environment(EnvType.CLIENT)
public class ArlroomsClientEvents implements ClientTickEvents.EndTick {
    private static ArlroomsAmbientSound ambientSound = null;

    @Override
    public void onEndTick(Minecraft minecraft) {
        if (minecraft.player == null) return;

        boolean inArlrooms = minecraft.player.level().dimension()
                .equals(ModDimensions.ARLROOMS_LEVEL_KEY);

        if (inArlrooms && ambientSound == null) {
            ambientSound = new ArlroomsAmbientSound();
            minecraft.getSoundManager().play(ambientSound);
        } else if (!inArlrooms && ambientSound != null) {
            ambientSound.stopSound();
            ambientSound = null;
        }
    }
}