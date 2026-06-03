package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import potatowolfie.arlo_the_little_guy.entity.top_hat.SnowGolemModelAccessor;

@Mixin(SnowGolemEntityModel.class)
public abstract class SnowGolemEntityModelMixin implements SnowGolemModelAccessor {

    @Shadow
    @Final
    private ModelPart head;

    @Override
    public ModelPart arloTheLittleGuy$getHead() {
        return this.head;
    }
}