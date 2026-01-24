package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import potatowolfie.arlo_the_little_guy.item.ModItems;

@Mixin(PigEntity.class)
public class PigEntityMixin {

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        PigEntity pig = (PigEntity) (Object) this;
        ItemStack stack = player.getStackInHand(hand);

        if (stack.getItem() == ModItems.CROWN) {
            ItemStack currentArmor = pig.getBodyArmor();

            if (currentArmor.getItem() == ModItems.CROWN) {
                cir.setReturnValue(ActionResult.PASS);
                return;
            }

            if (!pig.getEntityWorld().isClient()) {
                if (!currentArmor.isEmpty()) {
                    player.giveItemStack(currentArmor);
                }

                ItemStack crownCopy = stack.copy();
                crownCopy.setCount(1);
                pig.equipBodyArmor(crownCopy);

                if (!player.isCreative()) {
                    stack.decrement(1);
                }
            }

            cir.setReturnValue(pig.getEntityWorld().isClient() ? ActionResult.SUCCESS : ActionResult.CONSUME);
        }
    }
}