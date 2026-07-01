package potatowolfie.arlo_the_little_guy.recipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.resources.Identifier;

public class ModRecipes {

    public static final RecipeSerializer<CactusFlashlightRechargeRecipe> CACTUS_FLASHLIGHT_RECHARGE_SERIALIZER =
            new RecipeSerializer<>(
                    CactusFlashlightRechargeRecipe.MAP_CODEC,
                    CactusFlashlightRechargeRecipe.STREAM_CODEC
            );

    public static void registerRecipes() {
        Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                Identifier.fromNamespaceAndPath("arlo_the_little_guy", "cactus_flashlight_recharge"),
                CACTUS_FLASHLIGHT_RECHARGE_SERIALIZER
        );
    }
}