package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.renderer.entity.state.SnowGolemRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import potatowolfie.arlo_the_little_guy.entity.top_hat.SnowGolemRenderStateAccessor;

@Mixin(SnowGolemRenderState.class)
public class SnowGolemEntityRenderStateMixin implements SnowGolemRenderStateAccessor {
    @Unique
    private ItemStack arloTheLittleGuy$bodyArmor = ItemStack.EMPTY;

    @Override
    public ItemStack arloTheLittleGuy$getBodyArmor() {
        return this.arloTheLittleGuy$bodyArmor;
    }

    @Override
    public void arloTheLittleGuy$setBodyArmor(ItemStack stack) {
        this.arloTheLittleGuy$bodyArmor = stack;
    }
}