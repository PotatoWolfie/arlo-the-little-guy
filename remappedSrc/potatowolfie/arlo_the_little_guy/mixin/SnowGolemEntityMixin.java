package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import potatowolfie.arlo_the_little_guy.item.ModItems;

@Mixin(SnowGolemEntity.class)
public class SnowGolemEntityMixin {

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        SnowGolemEntity snowGolem = (SnowGolemEntity) (Object) this;
        ItemStack stack = player.getStackInHand(hand);

        if (stack.isOf(Items.SHEARS)) {
            ItemStack currentArmor = snowGolem.getBodyArmor();

            if (currentArmor.getItem() == ModItems.TOP_HAT) {
                if (!snowGolem.getEntityWorld().isClient()) {
                    ItemEntity itemEntity = new ItemEntity(
                            snowGolem.getEntityWorld(),
                            snowGolem.getX(),
                            snowGolem.getY() + 1.0,
                            snowGolem.getZ(),
                            currentArmor.copy()
                    );

                    snowGolem.getEntityWorld().spawnEntity(itemEntity);
                    snowGolem.equipBodyArmor(ItemStack.EMPTY);
                    stack.damage(1, player, player.getPreferredEquipmentSlot(stack));

                    snowGolem.getEntityWorld().playSound(
                            null,
                            snowGolem.getX(),
                            snowGolem.getY(),
                            snowGolem.getZ(),
                            SoundEvents.ENTITY_SNOW_GOLEM_SHEAR,
                            SoundCategory.PLAYERS,
                            1.0F,
                            1.0F
                    );
                }

                cir.setReturnValue(ActionResult.SUCCESS);
                return;
            }
        }

        if (stack.getItem() == ModItems.TOP_HAT && !snowGolem.hasPumpkin()) {
            ItemStack currentArmor = snowGolem.getBodyArmor();

            if (currentArmor.getItem() == ModItems.TOP_HAT) {
                cir.setReturnValue(ActionResult.PASS);
                return;
            }

            if (!snowGolem.getEntityWorld().isClient()) {
                if (!currentArmor.isEmpty()) {
                    player.giveItemStack(currentArmor);
                }

                ItemStack topHatCopy = stack.copy();
                topHatCopy.setCount(1);
                snowGolem.equipBodyArmor(topHatCopy);

                if (!player.isCreative()) {
                    stack.decrement(1);
                }
            }

            cir.setReturnValue(snowGolem.getEntityWorld().isClient() ? ActionResult.SUCCESS : ActionResult.CONSUME);
        }
    }
}