package potatowolfie.arlo_the_little_guy.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.resources.Identifier;
import potatowolfie.arlo_the_little_guy.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        registerHeadItem(itemModelGenerator, ModItems.BOWLER_HAT);
        registerHeadItem(itemModelGenerator, ModItems.TRICORN);
        registerHeadItem(itemModelGenerator, ModItems.STRAW_HAT);
        registerHeadItem(itemModelGenerator, ModItems.COWBOY_HAT);
        registerHeadItem(itemModelGenerator, ModItems.TOP_HAT);
        registerHeadItem(itemModelGenerator, ModItems.SUN_HAT);
        registerHeadItem(itemModelGenerator, ModItems.CROWN);
    }

    private void registerHeadItem(ItemModelGenerators generator, net.minecraft.world.item.Item item) {
        Identifier modelId = uploadHeadItemModel(generator, item);
        generator.itemModelOutput.accept(item, ItemModelUtils.plainModel(modelId));
    }

    private Identifier uploadHeadItemModel(ItemModelGenerators generator, net.minecraft.world.item.Item item) {
        Identifier modelId = ModelLocationUtils.getModelLocation(item);

        generator.modelOutput.accept(modelId, () -> {
            JsonObject json = new JsonObject();
            json.addProperty("parent", "minecraft:item/generated");

            JsonObject textures = new JsonObject();
            textures.addProperty("layer0", modelId.toString());
            json.add("textures", textures);

            JsonObject display = new JsonObject();
            JsonObject head = new JsonObject();

            JsonArray scale = new JsonArray();
            scale.add(0);
            scale.add(0);
            scale.add(0);
            head.add("scale", scale);

            display.add("head", head);
            json.add("display", display);

            return json;
        });

        return modelId;
    }
}