package potatowolfie.arlo_the_little_guy.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import potatowolfie.arlo_the_little_guy.component.ModDataComponentTypes;

public class CactusFlashlightItem extends Item {

    private static final int TOGGLE_COOLDOWN_TICKS = 5;

    public CactusFlashlightItem(Properties properties) {
        super(properties.durability(180));
    }

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide()) {
            long lastToggled = stack.getOrDefault(ModDataComponentTypes.FLASHLIGHT_LAST_TOGGLED, 0L);
            if (world.getGameTime() - lastToggled < TOGGLE_COOLDOWN_TICKS) {
                return InteractionResult.FAIL;
            }

            if (!isOn(stack) && (stack.getMaxDamage() - stack.getDamageValue() <= 1)) {
                return InteractionResult.FAIL;
            }

            toggle(stack);
            stack.set(ModDataComponentTypes.FLASHLIGHT_LAST_TOGGLED, world.getGameTime());

            boolean isOn = isOn(stack);
            float pitch = isOn ? 1.2F : 0.8F;
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 0.6F, pitch);
        }

        return world.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
    }

    @Override
    public void inventoryTick(final ItemStack itemStack, final ServerLevel level, final Entity owner, final @Nullable EquipmentSlot slot) {
        if (!(owner instanceof Player player)) return;

        if (isOn(itemStack)) {
            if (slot != EquipmentSlot.MAINHAND && slot != EquipmentSlot.OFFHAND) {
                forceOff(itemStack);
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 0.6F, 0.8F);
                return;
            }

            if (level.getGameTime() % 20 == 0) {
                int currentRemainingDurability = itemStack.getMaxDamage() - itemStack.getDamageValue();

                if (currentRemainingDurability <= 1) {
                    forceOff(itemStack);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 0.6F, 0.8F);
                } else {
                    EquipmentSlot targetSlot = slot != null ? slot : player.getEquipmentSlotForItem(itemStack);

                    itemStack.hurtAndBreak(1, player, targetSlot);
                }
            }

            if (level.getGameTime() % 10 == 0) {
                player.hurt(level.damageSources().cactus(), 1.0F);
            }
        }
    }

    public static boolean isOn(ItemStack stack) {
        return stack.has(ModDataComponentTypes.FLASHLIGHT_ON);
    }

    private static void toggle(ItemStack stack) {
        if (isOn(stack)) {
            stack.remove(ModDataComponentTypes.FLASHLIGHT_ON);
        } else {
            stack.set(ModDataComponentTypes.FLASHLIGHT_ON, Unit.INSTANCE);
        }
    }

    private static void forceOff(ItemStack stack) {
        stack.remove(ModDataComponentTypes.FLASHLIGHT_ON);
    }
}