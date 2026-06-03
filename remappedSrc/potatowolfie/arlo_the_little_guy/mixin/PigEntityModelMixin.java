package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import potatowolfie.arlo_the_little_guy.entity.crown.PigModelAccessor;

@Mixin(QuadrupedEntityModel.class)
public abstract class PigEntityModelMixin implements PigModelAccessor {

    @Shadow
    protected ModelPart head;

    @Override
    public ModelPart arloTheLittleGuy$getHead() {
        return this.head;
    }
}