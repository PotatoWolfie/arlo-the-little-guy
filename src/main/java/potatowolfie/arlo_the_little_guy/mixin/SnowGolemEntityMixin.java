package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot; // Added
import net.minecraft.world.entity.animal.golem.SnowGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import potatowolfie.arlo_the_little_guy.item.ModItems;

@Mixin(SnowGolem.class)
public class SnowGolemEntityMixin {

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        SnowGolem snowGolem = (SnowGolem) (Object) this;
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(Items.SHEARS)) {
            ItemStack currentArmor = snowGolem.getItemBySlot(EquipmentSlot.BODY);

            if (currentArmor.getItem() == ModItems.TOP_HAT) {
                if (!snowGolem.level().isClientSide()) {
                    ItemEntity itemEntity = new ItemEntity(
                            snowGolem.level(),
                            snowGolem.getX(),
                            snowGolem.getY() + 1.0,
                            snowGolem.getZ(),
                            currentArmor.copy()
                    );

                    snowGolem.level().addFreshEntity(itemEntity);

                    snowGolem.setItemSlot(EquipmentSlot.BODY, ItemStack.EMPTY);
                    stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));

                    snowGolem.level().playSound(
                            null,
                            snowGolem.getX(),
                            snowGolem.getY(),
                            snowGolem.getZ(),
                            SoundEvents.SNOW_GOLEM_SHEAR,
                            SoundSource.PLAYERS,
                            1.0F,
                            1.0F
                    );
                }

                cir.setReturnValue(InteractionResult.SUCCESS);
                return;
            }
        }

        if (stack.getItem() == ModItems.TOP_HAT && !snowGolem.hasPumpkin()) {
            ItemStack currentArmor = snowGolem.getItemBySlot(EquipmentSlot.BODY);

            if (currentArmor.getItem() == ModItems.TOP_HAT) {
                cir.setReturnValue(InteractionResult.PASS);
                return;
            }

            if (!snowGolem.level().isClientSide()) {
                if (!currentArmor.isEmpty()) {
                    player.addItem(currentArmor);
                }

                ItemStack topHatCopy = stack.copy();
                topHatCopy.setCount(1);

                snowGolem.setItemSlot(EquipmentSlot.BODY, topHatCopy);

                if (!player.isCreative()) {
                    stack.shrink(1);
                }
            }

            cir.setReturnValue(snowGolem.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME);
        }
    }
}