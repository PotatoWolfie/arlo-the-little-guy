package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import potatowolfie.arlo_the_little_guy.item.ModItems;

@Mixin(Pig.class)
public class PigEntityMixin {

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Pig pig = (Pig) (Object) this;
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() == ModItems.CROWN) {
            ItemStack currentArmor = pig.getBodyArmorItem();

            if (currentArmor.getItem() == ModItems.CROWN) {
                cir.setReturnValue(InteractionResult.PASS);
                return;
            }

            if (!pig.level().isClientSide()) {
                if (!currentArmor.isEmpty()) {
                    player.addItem(currentArmor);
                }

                ItemStack crownCopy = stack.copy();
                crownCopy.setCount(1);
                pig.setBodyArmorItem(crownCopy);

                if (!player.isCreative()) {
                    stack.shrink(1);
                }
            }

            cir.setReturnValue(pig.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME);
        }
    }
}