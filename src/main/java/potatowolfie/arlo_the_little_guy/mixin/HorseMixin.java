package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.animal.equine.Markings;
import net.minecraft.world.entity.animal.equine.Variant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import potatowolfie.arlo_the_little_guy.entity.cactus_horse.HorseAccessBridge;

@Mixin(Horse.class)
public abstract class HorseMixin implements HorseAccessBridge {

    @Invoker("setVariantAndMarkings")
    protected abstract void invokeSetVariantAndMarkings(Variant variant, Markings markings);

    @Override
    public void arlo$setVariantAndMarkings(Variant variant, Markings markings) {
        this.invokeSetVariantAndMarkings(variant, markings);
    }
}