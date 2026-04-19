package potatowolfie.arlo_the_little_guy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.registry.CompostableRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
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

		CompostableRegistry.INSTANCE.add(ModBlocks.MINI_CACTUS, 0.5f);

		LOGGER.info("CACTUS");
	}

	private void registerChunkLoadEvent() {
		ServerChunkEvents.CHUNK_LOAD.register((world, chunk, generated) -> {
			scheduleBlockTicksForChunk(world, chunk);
		});
	}

	private void scheduleBlockTicksForChunk(ServerLevel world, LevelChunk chunk) {
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int minY = world.getMinY();
				int maxY = minY + world.getHeight();

				for (int y = minY; y < maxY; y++) {
					pos.set(chunk.getPos().getMinBlockX() + x, y, chunk.getPos().getMinBlockZ() + z);
					var state = chunk.getBlockState(pos);
					var block = state.getBlock();

					if (block == ModBlocks.MINI_CACTUS) {

						world.scheduleTick(pos.immutable(), block, 2);
					}
				}
			}
		}
	}
}