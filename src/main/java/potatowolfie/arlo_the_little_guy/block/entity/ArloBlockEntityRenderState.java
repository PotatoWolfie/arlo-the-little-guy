package potatowolfie.arlo_the_little_guy.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class ArloBlockEntityRenderState extends BlockEntityRenderState {
    public final AnimationState interactAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState randomAnimationState = new AnimationState();
    public float age;
    public String hatType = "none";
    public ArloBlockEntity.ArloState arloState = ArloBlockEntity.ArloState.IDLE;
    public int interactAnimationStateTimer = 0;
    public int randomAnimationStateTimer = 0;
    public int light = 15728880;

    public ArloBlockEntityRenderState() {
    }
}