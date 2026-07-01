package potatowolfie.arlo_the_little_guy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.registry.CompostableRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import potatowolfie.arlo_the_little_guy.attachment.ModAttachments;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;
import potatowolfie.arlo_the_little_guy.block.entity.ModBlockEntities;
import potatowolfie.arlo_the_little_guy.client.arlrooms.ArlroomsTimeTracker;
import potatowolfie.arlo_the_little_guy.client.arlrooms.StareTimeTracker;
import potatowolfie.arlo_the_little_guy.component.ModDataComponentTypes;
import potatowolfie.arlo_the_little_guy.datagen.ModLootTableModifier;
import potatowolfie.arlo_the_little_guy.entity.ModEntities;
import potatowolfie.arlo_the_little_guy.entity.cactus_horse.CactusHorseEntity;
import potatowolfie.arlo_the_little_guy.event.ArlroomsNoInteractionHandler;
import potatowolfie.arlo_the_little_guy.item.ModItems;
import potatowolfie.arlo_the_little_guy.recipe.ModRecipes;
import potatowolfie.arlo_the_little_guy.sound.ModSounds;
import potatowolfie.arlo_the_little_guy.structure.ModStructurePieceTypes;
import potatowolfie.arlo_the_little_guy.structure.ModStructureTypes;
import potatowolfie.arlo_the_little_guy.structure.arlrooms.ArlroomsRuntime;
import potatowolfie.arlo_the_little_guy.structure.arlrooms.ArlroomsSavedData;
import potatowolfie.arlo_the_little_guy.structure.arlrooms.DoorLookPayload;
import potatowolfie.arlo_the_little_guy.structure.arlrooms.ArlroomsExpansionManager;
import potatowolfie.arlo_the_little_guy.world.ModBiomeModifications;
import potatowolfie.arlo_the_little_guy.world.dimension.ModDimensions;
import potatowolfie.arlo_the_little_guy.world.feature.ModFeatures;

public class ArloTheLittleGuy implements ModInitializer {
	public static final String MOD_ID = "arlo-the-little-guy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final SimpleParticleType CEILING_LIGHT_SPARK = FabricParticleTypes.simple();

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModBlockEntities.registerBlockEntities();
		ModEntities.registerModEntities();
		ModSounds.registerSounds();

		Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "ceiling_light_spark"), CEILING_LIGHT_SPARK);

		PayloadTypeRegistry.serverboundPlay().register(DoorLookPayload.TYPE, DoorLookPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(DoorLookPayload.TYPE, (payload, context) -> {
			context.server().execute(() -> {
				ServerPlayer player = context.player();

				ServerLevel level = player.level();
				BlockPos pos = payload.pos();
				ArlroomsExpansionManager.tryExpand(level, pos, player);
			});
		});

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			ServerLevel level = server.getLevel(ModDimensions.ARLROOMS_LEVEL_KEY);

			if (level != null) {
				ArlroomsSavedData data = ArlroomsSavedData.get(level);

				if (data.getPlacedBoxes().isEmpty()) {
					placeStarterRoom(level, new BlockPos(-7, 77, -7));
				}
			}
		});

		ArlroomsRuntime.init();
		ModStructureTypes.registerStructureTypes();
		ModStructurePieceTypes.registerStructurePieceTypes();
		ModFeatures.registerFeatures();
		ModBiomeModifications.registerBiomeModifications();
		ModLootTableModifier.modifyLootTables();
		registerChunkLoadEvent();
		ArlroomsNoInteractionHandler.register();
		ModRecipes.registerRecipes();
		ModDataComponentTypes.register();
		ModAttachments.initialize();
		ArlroomsTimeTracker.register();
		StareTimeTracker.register();

		org.apache.logging.log4j.core.Logger rootLogger =
				(org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();

		rootLogger.addFilter(new AbstractFilter() {
			@Override
			public Filter.Result filter(LogEvent event) {
				if (event != null && event.getMessage() != null) {
					String messageStr = event.getMessage().getFormattedMessage();

					if (messageStr.contains("Mismatch in destroy block pos")) {
						return Filter.Result.DENY;
					}
				}
				return Filter.Result.NEUTRAL;
			}
		});

		FabricDefaultAttributeRegistry.register(ModEntities.CACTUS_HORSE, CactusHorseEntity.createBaseHorseAttributes());

		System.out.println("Registering Arlrooms for " + MOD_ID);
		var levelKey = ModDimensions.ARLROOMS_LEVEL_KEY;

		CompostableRegistry.INSTANCE.add(ModBlocks.MINI_CACTUS, 0.5f);

		LOGGER.info("CACTUS");
	}

	private void registerChunkLoadEvent() {
		ServerChunkEvents.CHUNK_LOAD.register((world, chunk, generated) -> {
			scheduleBlockTicksForChunk(world, chunk);
		});
	}

	private void placeStarterRoom(ServerLevel level, BlockPos pos) {
		Identifier id = Identifier.parse("arlo-the-little-guy:arlrooms/starter_room");

		StructureTemplate template = level.getServer()
				.getStructureManager()
				.getOrCreate(id);

		StructurePlaceSettings settings = new StructurePlaceSettings()
				.setRotation(Rotation.NONE)
				.setMirror(Mirror.NONE)
				.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);

		template.placeInWorld(
				level,
				pos,
				pos,
				settings,
				level.getRandom(),
				2
		);

		BoundingBox box = template.getBoundingBox(settings, pos);
		ArlroomsSavedData.get(level).addBox(box);
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