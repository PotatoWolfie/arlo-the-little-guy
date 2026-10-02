package potatowolfie.arlo_the_little_guy.datagen.trade;

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

public class ModVillagerTrades {
    public static final ResourceKey<VillagerTrade> FARMER_4_STRAW_HAT = createKey("farmer/4/straw_hat");
    public static final ResourceKey<VillagerTrade> LEATHERWORKER_5_COWBOY_HAT = createKey("leatherworker/5/cowboy_hat");
    public static final ResourceKey<VillagerTrade> FISHERMAN_5_TRICORN = createKey("fisherman/5/tricorn");

    public static final ResourceKey<VillagerTrade> WANDERING_TRADER_CROWN = createKey("wandering_trader/crown");

    public static void bootstrap(BootstrapContext<VillagerTrade> context) {

        context.register(FARMER_4_STRAW_HAT, VillagerTrade.builder(
                new TradeCost(Items.WHEAT, 10),
                new TradeCost(Items.EMERALD, 5),
                new ItemStackTemplate(ModItems.STRAW_HAT),
                5, 2, 0.05f
        ).build());

        context.register(LEATHERWORKER_5_COWBOY_HAT, VillagerTrade.builder(
                new TradeCost(Items.LEATHER, 5),
                new TradeCost(Items.EMERALD, 8),
                new ItemStackTemplate(ModItems.COWBOY_HAT),
                5, 2, 0.05f
        ).build());

        context.register(FISHERMAN_5_TRICORN, VillagerTrade.builder(
                new TradeCost(Items.TROPICAL_FISH, 3),
                new TradeCost(Items.EMERALD, 6),
                new ItemStackTemplate(ModItems.TRICORN),
                5, 2, 0.05f
        ).build());

        context.register(WANDERING_TRADER_CROWN, VillagerTrade.builder(
                new TradeCost(Items.EMERALD, 12),
                new ItemStackTemplate(ModItems.CROWN),
                5, 2, 0.05f
        ).build());
    }

    private static ResourceKey<VillagerTrade> createKey(String name) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name));
    }
}