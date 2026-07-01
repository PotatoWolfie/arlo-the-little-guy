package potatowolfie.arlo_the_little_guy.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import potatowolfie.arlo_the_little_guy.item.ModItems;
import potatowolfie.arlo_the_little_guy.item.custom.CactusFlashlightItem;

public class CactusFlashlightRechargeRecipe extends CustomRecipe {

    public static final MapCodec<CactusFlashlightRechargeRecipe> MAP_CODEC =
            MapCodec.unit(new CactusFlashlightRechargeRecipe(CraftingBookCategory.EQUIPMENT));

    public static final StreamCodec<RegistryFriendlyByteBuf, CactusFlashlightRechargeRecipe> STREAM_CODEC =
            StreamCodec.unit(new CactusFlashlightRechargeRecipe(CraftingBookCategory.EQUIPMENT));

    public CactusFlashlightRechargeRecipe(CraftingBookCategory category) {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != 2) {
            return false;
        }

        boolean hasFlashlight = false;
        boolean hasBattery = false;

        for (int slot = 0; slot < input.size(); ++slot) {
            ItemStack itemStack = input.getItem(slot);
            if (!itemStack.isEmpty()) {
                if (itemStack.getItem() instanceof CactusFlashlightItem) {
                    if (hasFlashlight) return false;
                    hasFlashlight = true;
                } else if (itemStack.getItem() == ModItems.CACTUS_BATTERY) {
                    if (hasBattery) return false;
                    hasBattery = true;
                } else {
                    return false;
                }
            }
        }

        return hasFlashlight && hasBattery;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack flashlightStack = ItemStack.EMPTY;

        for (int slot = 0; slot < input.size(); ++slot) {
            ItemStack itemStack = input.getItem(slot);
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof CactusFlashlightItem) {
                flashlightStack = itemStack;
                break;
            }
        }

        if (flashlightStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack result = flashlightStack.copy();
        result.setCount(1);

        int currentDamage = result.getDamageValue();
        int newDamage = Math.max(0, currentDamage - 90);
        result.setDamageValue(newDamage);

        return result;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipes.CACTUS_FLASHLIGHT_RECHARGE_SERIALIZER;
    }
}