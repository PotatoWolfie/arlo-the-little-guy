package potatowolfie.arlo_the_little_guy;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.chunk.WorldChunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;
import potatowolfie.arlo_the_little_guy.block.entity.ModBlockEntities;
import potatowolfie.arlo_the_little_guy.datagen.ModLootTableModifier;
import potatowolfie.arlo_the_little_guy.item.ModItems;
import potatowolfie.arlo_the_little_guy.sound.ModSounds;
import potatowolfie.arlo_the_little_guy.structure.ModStructurePieceTypes;
import potatowolfie.arlo_the_little_guy.structure.ModStructureTypes;
import potatowolfie.arlo_the_little_guy.world.ModBiomeModifications;
import potatowolfie.arlo_the_little_guy.world.feature.ModFeatures;

import java.util.Optional;

public class ArloTheLittleGuy implements ModInitializer {
	public static final String MOD_ID = "arlo-the-little-guy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModBlockEntities.registerBlockEntities();
		ModSounds.registerSounds();
		ModStructureTypes.registerStructureTypes();
		ModStructurePieceTypes.registerStructurePieceTypes();
		ModFeatures.registerFeatures();
		ModBiomeModifications.registerBiomeModifications();
		ModLootTableModifier.modifyLootTables();
		registerChunkLoadEvent();

		CompostingChanceRegistry.INSTANCE.add(ModBlocks.MINI_CACTUS, 0.5f);

		TradeOfferHelper.registerVillagerOffers(
				VillagerProfession.FARMER, 4, factories -> {factories.add((entity, random, context) ->
						new TradeOffer(
								new TradedItem(Items.WHEAT, 10),
								Optional.of(new TradedItem(Items.EMERALD, 5)),
								new ItemStack(ModItems.STRAW_HAT, 1),
								5,
								2,
								0.05f
						));
				});
		TradeOfferHelper.registerVillagerOffers(
				VillagerProfession.LEATHERWORKER, 5, factories -> {factories.add((entity, random, context) ->
						new TradeOffer(
								new TradedItem(Items.LEATHER, 5),
								Optional.of(new TradedItem(Items.EMERALD, 8)),
								new ItemStack(ModItems.COWBOY_HAT, 1),
								5,
								2,
								0.05f
						));
				});
		TradeOfferHelper.registerVillagerOffers(
				VillagerProfession.FISHERMAN, 5, factories -> {factories.add((entity, random, context) ->
						new TradeOffer(
								new TradedItem(Items.TROPICAL_FISH, 3),
								Optional.of(new TradedItem(Items.EMERALD, 6)),
								new ItemStack(ModItems.TRICORN, 1),
								5,
								2,
								0.05f
						));
				});

		TradeOfferHelper.registerWanderingTraderOffers(builder -> {
			builder.addOffersToPool(
					TradeOfferHelper.WanderingTraderOffersBuilder.SELL_SPECIAL_ITEMS_POOL,
					(entity, random, context) -> new TradeOffer(
							new TradedItem(Items.EMERALD, 12),
							new ItemStack(ModItems.CROWN, 1),
							1, 5, 0.05f
					)
			);
		});

		LOGGER.info("CACTUS");
	}

	private void registerChunkLoadEvent() {
		ServerChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
			scheduleBlockTicksForChunk(world, chunk);
		});
	}

	private void scheduleBlockTicksForChunk(ServerWorld world, WorldChunk chunk) {
		BlockPos.Mutable pos = new BlockPos.Mutable();

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int minY = world.getBottomY();
				int maxY = minY + world.getHeight();

				for (int y = minY; y < maxY; y++) {
					pos.set(chunk.getPos().getStartX() + x, y, chunk.getPos().getStartZ() + z);
					var state = chunk.getBlockState(pos);
					var block = state.getBlock();

					if (block == ModBlocks.MINI_CACTUS) {

						world.scheduleBlockTick(pos.toImmutable(), block, 2);
					}
				}
			}
		}
	}
}