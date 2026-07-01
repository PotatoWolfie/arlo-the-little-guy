package potatowolfie.arlo_the_little_guy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Blocks;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider wrapperLookup, RecipeOutput recipeExporter) {
        return new RecipeProvider(wrapperLookup, recipeExporter) {
            @Override
            public void buildRecipes() {
                simpleCookingRecipe("smelting", SmeltingRecipe::new,
                        200, ModBlocks.MINI_CACTUS, Items.DYE.pick(DyeColor.GREEN), 0.35f);

                stairBuilder(ModBlocks.CACTUS_WALLPAPER_STAIRS, Ingredient.of(ModBlocks.CACTUS_WALLPAPER));
                stairBuilder(ModBlocks.MOIST_WOOL_STAIRS, Ingredient.of(ModBlocks.MOIST_WOOL));
                stairBuilder(ModBlocks.TILED_WALL_STAIRS, Ingredient.of(ModBlocks.TILED_WALL));

                slabBuilder(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CACTUS_WALLPAPER_SLAB, Ingredient.of(ModBlocks.CACTUS_WALLPAPER));
                slabBuilder(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MOIST_WOOL_SLAB, Ingredient.of(ModBlocks.MOIST_WOOL));
                slabBuilder(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TILED_WALL_SLAB, Ingredient.of(ModBlocks.TILED_WALL));

                shaped(RecipeCategory.MISC, ModBlocks.CACTUS_WALLPAPER, 1)
                        .pattern("XX")
                        .pattern("XX")
                        .define('X', ModBlocks.CACTUS_WALLPAPER_SHEET)
                        .unlockedBy(getHasName(ModBlocks.CACTUS_WALLPAPER_SHEET), has(ModBlocks.CACTUS_WALLPAPER_SHEET))
                        .save(output, String.valueOf(Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "cactus_wallpaper_from_sheets")));

                shaped(RecipeCategory.MISC, ModBlocks.MOIST_WOOL, 2)
                        .pattern("XX")
                        .pattern("XX")
                        .define('X', ModBlocks.MOIST_WOOL_CARPET)
                        .unlockedBy(getHasName(ModBlocks.MOIST_WOOL_CARPET), has(ModBlocks.MOIST_WOOL_CARPET))
                        .save(output, String.valueOf(Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "moist_wool_from_carpet")));

                shaped(RecipeCategory.MISC, ModBlocks.MOIST_WOOL_CARPET, 3)
                        .pattern("XX")
                        .define('X', ModBlocks.MOIST_WOOL)
                        .unlockedBy(getHasName(ModBlocks.MOIST_WOOL), has(ModBlocks.MOIST_WOOL))
                        .save(output, String.valueOf(Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "moist_carpet_from_wool")));

                shaped(RecipeCategory.MISC, ModBlocks.CEILING_TILE, 2)
                        .pattern("ZZ")
                        .pattern("XX")
                        .define('X', Blocks.DIORITE)
                        .define('Z', Items.PAPER)
                        .unlockedBy(getHasName(Blocks.DIORITE), has(Blocks.DIORITE))
                        .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                        .save(output, String.valueOf(Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "ceiling_tile_diorite")));

                shaped(RecipeCategory.MISC, ModBlocks.TILED_WALL, 4)
                        .pattern("XZ")
                        .pattern("ZX")
                        .define('X', Blocks.CALCITE)
                        .define('Z', Blocks.GLASS)
                        .unlockedBy(getHasName(Blocks.CALCITE), has(Blocks.CALCITE))
                        .unlockedBy(getHasName(Blocks.GLASS), has(Blocks.GLASS))
                        .save(output, String.valueOf(Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "tiled_wall_calcite")));

                shapeless(RecipeCategory.MISC, ModBlocks.STRIPED_TILED_WALL, 1)
                        .requires(ModBlocks.TILED_WALL)
                        .requires(Items.DYE.pick(DyeColor.ORANGE))
                        .unlockedBy(getHasName(ModBlocks.TILED_WALL), has(ModBlocks.TILED_WALL))
                        .unlockedBy(getHasName(Items.DYE.pick(DyeColor.ORANGE)), has(Items.DYE.pick(DyeColor.ORANGE)))
                        .save(output, String.valueOf(Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "striped_tiled_wall_orange")));

                shapeless(RecipeCategory.MISC, ModBlocks.BLUE_TILED_WALL, 1)
                        .requires(ModBlocks.TILED_WALL)
                        .requires(Items.DYE.pick(DyeColor.BLUE))
                        .unlockedBy(getHasName(ModBlocks.TILED_WALL), has(ModBlocks.TILED_WALL))
                        .unlockedBy(getHasName(Items.DYE.pick(DyeColor.BLUE)), has(Items.DYE.pick(DyeColor.BLUE)))
                        .save(output, String.valueOf(Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "blue_tiled_wall_dye")));

                shapeless(RecipeCategory.MISC, ModBlocks.BLUE_TILED_WALL_STAIRS, 1)
                        .requires(ModBlocks.TILED_WALL)
                        .requires(Items.DYE.pick(DyeColor.BLUE))
                        .unlockedBy(getHasName(ModBlocks.TILED_WALL), has(ModBlocks.TILED_WALL))
                        .unlockedBy(getHasName(Items.DYE.pick(DyeColor.BLUE)), has(Items.DYE.pick(DyeColor.BLUE)))
                        .save(output, String.valueOf(Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "blue_tiled_wall_stairs_dye")));

                shapeless(RecipeCategory.MISC, ModBlocks.BLUE_TILED_WALL_SLAB, 1)
                        .requires(ModBlocks.TILED_WALL)
                        .requires(Items.DYE.pick(DyeColor.BLUE))
                        .unlockedBy(getHasName(ModBlocks.TILED_WALL), has(ModBlocks.TILED_WALL))
                        .unlockedBy(getHasName(Items.DYE.pick(DyeColor.BLUE)), has(Items.DYE.pick(DyeColor.BLUE)))
                        .save(output, String.valueOf(Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "blue_tiled_wall_slab_dye")));

                shapeless(RecipeCategory.MISC, ModBlocks.CEILING_LIGHT, 16)
                        .requires(Blocks.SEA_LANTERN)
                        .requires(Items.PHANTOM_MEMBRANE)
                        .unlockedBy(getHasName(Blocks.SEA_LANTERN), has(Blocks.SEA_LANTERN))
                        .unlockedBy(getHasName(Items.PHANTOM_MEMBRANE), has(Items.PHANTOM_MEMBRANE))
                        .save(output, String.valueOf(Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "ceiling_light_sea_lantern")));
            }
        };
    }

    @Override
    public String getName() {
        return "The 'don't cook arlo' recipes";
    }
}