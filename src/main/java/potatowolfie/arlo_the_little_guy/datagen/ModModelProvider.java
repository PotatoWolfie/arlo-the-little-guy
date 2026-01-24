package potatowolfie.arlo_the_little_guy.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.util.Identifier;
import potatowolfie.arlo_the_little_guy.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        registerHeadItem(itemModelGenerator, ModItems.BOWLER_HAT);
        registerHeadItem(itemModelGenerator, ModItems.TRICORN);
        registerHeadItem(itemModelGenerator, ModItems.STRAW_HAT);
        registerHeadItem(itemModelGenerator, ModItems.COWBOY_HAT);
        registerHeadItem(itemModelGenerator, ModItems.TOP_HAT);
        registerHeadItem(itemModelGenerator, ModItems.SUN_HAT);
        registerHeadItem(itemModelGenerator, ModItems.CROWN);
    }

    private void registerHeadItem(ItemModelGenerator generator, net.minecraft.item.Item item) {
        Identifier modelId = uploadHeadItemModel(generator, item);
        generator.output.accept(item, ItemModels.basic(modelId));
    }

    private Identifier uploadHeadItemModel(ItemModelGenerator generator, net.minecraft.item.Item item) {
        Identifier modelId = ModelIds.getItemModelId(item);

        generator.modelCollector.accept(modelId, () -> {
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