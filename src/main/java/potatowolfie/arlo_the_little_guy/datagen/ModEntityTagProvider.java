package potatowolfie.arlo_the_little_guy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;
import potatowolfie.arlo_the_little_guy.entity.ModEntities;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagProvider extends FabricTagsProvider.EntityTypeTagsProvider {
    public ModEntityTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        builder(EntityTypeTags.CAN_EQUIP_SADDLE)
                .add(BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(ModEntities.CACTUS_HORSE).unwrapKey().orElseThrow());

        builder(EntityTypeTags.CAN_WEAR_HORSE_ARMOR)
                .add(BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(ModEntities.CACTUS_HORSE).unwrapKey().orElseThrow());
    }
}