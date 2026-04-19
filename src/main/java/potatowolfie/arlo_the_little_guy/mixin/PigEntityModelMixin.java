package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import potatowolfie.arlo_the_little_guy.entity.crown.PigModelAccessor;

@Mixin(QuadrupedModel.class)
public abstract class PigEntityModelMixin implements PigModelAccessor {

    @Shadow
    protected ModelPart head;

    @Override
    public ModelPart arloTheLittleGuy$getHead() {
        return this.head;
    }
}