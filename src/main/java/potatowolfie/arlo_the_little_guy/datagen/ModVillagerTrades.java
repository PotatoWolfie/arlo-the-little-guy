package potatowolfie.arlo_the_little_guy.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.item.ModItems;

import java.util.List;
import java.util.Optional;

public class ModVillagerTrades {

    // Doesn't work :(
    //TODO: Eventually make these able to generate via datagen

    public static final ResourceKey<VillagerTrade> FARMER_4_STRAW_HAT =
            resourceKey("farmer/4/straw_hat");

    public static final ResourceKey<VillagerTrade> LEATHERWORKER_5_COWBOY_HAT =
            resourceKey("leatherworker/5/cowboy_hat");

    public static final ResourceKey<VillagerTrade> FISHERMAN_5_TRICORN =
            resourceKey("fisherman/5/tricorn");

    public static final ResourceKey<VillagerTrade> WANDERING_TRADER_CROWN =
            resourceKey("wandering_trader/crown");

    public static void bootstrap(BootstrapContext<VillagerTrade> context) {

        register(context, FARMER_4_STRAW_HAT, new VillagerTrade(
                new TradeCost(Items.WHEAT, 10),
                Optional.of(new TradeCost(Items.EMERALD, 5)),
                new ItemStackTemplate(ModItems.STRAW_HAT),
                5,
                2,
                0.05F,
                Optional.empty(),
                List.of()
        ));

        register(context, LEATHERWORKER_5_COWBOY_HAT, new VillagerTrade(
                new TradeCost(Items.LEATHER, 5),
                Optional.of(new TradeCost(Items.EMERALD, 8)),
                new ItemStackTemplate(ModItems.COWBOY_HAT),
                5,
                2,
                0.05F,
                Optional.empty(),
                List.of()
        ));

        register(context, FISHERMAN_5_TRICORN, new VillagerTrade(
                new TradeCost(Items.TROPICAL_FISH, 3),
                Optional.of(new TradeCost(Items.EMERALD, 6)),
                new ItemStackTemplate(ModItems.TRICORN),
                5,
                2,
                0.05F,
                Optional.empty(),
                List.of()
        ));

        register(context, WANDERING_TRADER_CROWN, new VillagerTrade(
                new TradeCost(Items.EMERALD, 12),
                new ItemStackTemplate(ModItems.CROWN),
                1,
                5,
                0.05F,
                Optional.empty(),
                List.of()
        ));
    }

    private static void register(
            BootstrapContext<VillagerTrade> context,
            ResourceKey<VillagerTrade> resourceKey,
            VillagerTrade trade
    ) {
        context.register(resourceKey, trade);
    }

    public static ResourceKey<VillagerTrade> resourceKey(String path) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, path));
    }
}