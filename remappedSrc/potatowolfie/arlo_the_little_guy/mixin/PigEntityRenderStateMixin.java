package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.render.entity.state.PigEntityRenderState;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import potatowolfie.arlo_the_little_guy.entity.crown.PigRenderStateAccessor;

@Mixin(PigEntityRenderState.class)
public class PigEntityRenderStateMixin implements PigRenderStateAccessor {
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