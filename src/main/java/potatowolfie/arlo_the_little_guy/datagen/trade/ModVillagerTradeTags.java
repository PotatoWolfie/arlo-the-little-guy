package potatowolfie.arlo_the_little_guy.datagen.trade;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.concurrent.CompletableFuture;

public class ModVillagerTradeTags extends FabricTagsProvider<VillagerTrade> {
    public ModVillagerTradeTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.VILLAGER_TRADE, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(VillagerTradeTags.FARMER_LEVEL_4)
                .add(TagEntry.element(ModVillagerTrades.FARMER_4_STRAW_HAT.identifier()));

        getOrCreateRawBuilder(VillagerTradeTags.LEATHERWORKER_LEVEL_5)
                .add(TagEntry.element(ModVillagerTrades.LEATHERWORKER_5_COWBOY_HAT.identifier()));

        getOrCreateRawBuilder(VillagerTradeTags.FISHERMAN_LEVEL_5)
                .add(TagEntry.element(ModVillagerTrades.FISHERMAN_5_TRICORN.identifier()));

        getOrCreateRawBuilder(VillagerTradeTags.WANDERING_TRADER_UNCOMMON)
                .add(TagEntry.element(ModVillagerTrades.WANDERING_TRADER_CROWN.identifier()));

    }
}