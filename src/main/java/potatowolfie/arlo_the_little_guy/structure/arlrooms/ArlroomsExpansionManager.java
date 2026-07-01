package potatowolfie.arlo_the_little_guy.structure.arlrooms;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;
import potatowolfie.arlo_the_little_guy.block.custom.CeilingLightBlock;
import potatowolfie.arlo_the_little_guy.block.custom.DecorationMarkerBlock;
import potatowolfie.arlo_the_little_guy.block.custom.DoorMarkerBlock;
import potatowolfie.arlo_the_little_guy.world.dimension.ModDimensions;

import java.util.*;
import java.util.function.Supplier;

public final class ArlroomsExpansionManager {

    public static final Logger LOGGER = LoggerFactory.getLogger("ArlroomsExpansion");

    private static final int FAILURE_COOLDOWN_TICKS = 1;
    private static final double DEFAULT_ROOM_WEIGHT = 1.0;
    private static final Map<BlockPos, Integer> MARKER_COOLDOWN = new HashMap<>();

    private static final int ATTEMPT_LIMIT = 5;
    private static final Map<BlockPos, Integer> MARKER_ATTEMPTS = new HashMap<>();

    private static final Rotation[] ROTATIONS = {
            Rotation.NONE,
            Rotation.CLOCKWISE_90,
            Rotation.CLOCKWISE_180,
            Rotation.COUNTERCLOCKWISE_90
    };

    private interface WeightedPool {
        String resourceFolder();
        String identifierPrefix();
        boolean excludeStarterRoom();
        Map<String, Double> weights();
    }

    private enum RoomPool implements WeightedPool {
        ARLROOMS(
                () -> ModBlocks.DOOR_MARKER,
                () -> ModBlocks.CACTUS_WALLPAPER,
                "structure/arlrooms",
                "arlrooms/",
                true,
                Map.of(
                        "room_4", 0.08,
                        "room_6", 0.08,
                        "room_12", 0.1,
                        "room_14", 0.45,
                        "level_188", 0.06,
                        "arlrooms_change", 0.5
                )
        ),
        POOLROOMS(
                () -> ModBlocks.POOL_ROOMS_DOOR_MARKER,
                () -> ModBlocks.TILED_WALL,
                "structure/arlrooms/poolrooms",
                "arlrooms/poolrooms/",
                false,
                Map.of(
                        "poolrooms_2", 0.5,
                        "poolrooms_6", 0.06,
                        "poolrooms_11", 0.1,
                        "poolrooms_change", 0.5
                )
        );

        private final Supplier<Block> markerBlock;
        private final Supplier<Block> fallbackBlock;
        private final String resourceFolder;
        private final String identifierPrefix;
        private final boolean excludeStarterRoom;
        private final Map<String, Double> weights;

        RoomPool(Supplier<Block> markerBlock,
                 Supplier<Block> fallbackBlock,
                 String resourceFolder,
                 String identifierPrefix,
                 boolean excludeStarterRoom,
                 Map<String, Double> weights) {
            this.markerBlock = markerBlock;
            this.fallbackBlock = fallbackBlock;
            this.resourceFolder = resourceFolder;
            this.identifierPrefix = identifierPrefix;
            this.excludeStarterRoom = excludeStarterRoom;
            this.weights = weights;
        }

        private static RoomPool fromMarkerState(BlockState state) {
            for (RoomPool pool : values()) {
                if (state.is(pool.markerBlock.get())) return pool;
            }
            return null;
        }

        @Override
        public String resourceFolder() { return resourceFolder; }
        @Override
        public String identifierPrefix() { return identifierPrefix; }
        @Override
        public boolean excludeStarterRoom() { return excludeStarterRoom; }
        @Override
        public Map<String, Double> weights() { return weights; }
    }

    private enum DecorationPool implements WeightedPool {
        DECORATIONS(
                "structure/arlrooms/decorations",
                "arlrooms/decorations/",
                Map.ofEntries( // ? RARITY REGISTRATION
                        Map.entry("decoration_1", 1.0),
                        Map.entry("decoration_2", 1.0),
                        Map.entry("decoration_3", 0.2),
                        Map.entry("decoration_4", 0.9),
                        Map.entry("decoration_5", 1.0),
                        Map.entry("decoration_6", 0.1),
                        Map.entry("decoration_7", 0.2),
                        Map.entry("decoration_8", 0.2),
                        Map.entry("decoration_9", 0.2),
                        Map.entry("decoration_10", 0.2),
                        Map.entry("decoration_11", 0.2),
                        Map.entry("decoration_12", 0.2),
                        Map.entry("decoration_13", 0.2),
                        Map.entry("decoration_14", 0.2),
                        Map.entry("decoration_15", 0.2),
                        Map.entry("decoration_16", 0.2),
                        Map.entry("decoration_17", 0.3),
                        Map.entry("decoration_18", 1.0),
                        Map.entry("decoration_19", 0.2),
                        Map.entry("decoration_20", 0.7),
                        Map.entry("decoration_21", 0.7),
                        Map.entry("decoration_22", 0.7),
                        Map.entry("decoration_23", 0.2),
                        Map.entry("decoration_24", 0.35),
                        Map.entry("decoration_25", 1.0),
                        Map.entry("decoration_26", 0.5),
                        Map.entry("decoration_27", 0.5),
                        Map.entry("decoration_28", 1.0),
                        Map.entry("decoration_29", 0.9),
                        Map.entry("decoration_30", 0.3),
                        Map.entry("decoration_31", 0.8),

                        Map.entry("poolrooms_decoration_2", 0.7),
                        Map.entry("poolrooms_decoration_3", 0.7),
                        Map.entry("poolrooms_decoration_4", 0.7)
                ),
                Map.ofEntries( // ? LOOT TABLE REGISTRATION
                        Map.entry("decoration_20", ResourceKey.create(Registries.LOOT_TABLE,
                                Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlrooms/decorations/common"))),
                        Map.entry("decoration_21", ResourceKey.create(Registries.LOOT_TABLE,
                                Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlrooms/decorations/hanging"))),
                        Map.entry("decoration_22", ResourceKey.create(Registries.LOOT_TABLE,
                                Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlrooms/decorations/common"))),
                        Map.entry("decoration_30", ResourceKey.create(Registries.LOOT_TABLE,
                                Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlrooms/decorations/rare"))),

                        Map.entry("poolrooms_decoration_2", ResourceKey.create(Registries.LOOT_TABLE,
                                Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlrooms/decorations/poolrooms"))),
                        Map.entry("poolrooms_decoration_3", ResourceKey.create(Registries.LOOT_TABLE,
                                Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlrooms/decorations/hanging"))),
                        Map.entry("poolrooms_decoration_4", ResourceKey.create(Registries.LOOT_TABLE,
                                Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlrooms/decorations/poolrooms")))
                ),
                Map.ofEntries( // ? TAG REGISTRATION
                        Map.entry("hallway_5_chest", Set.of(DecorationMarkerBlock.DecorationTag.SECRET)),
                        Map.entry("decoration_2", Set.of(DecorationMarkerBlock.DecorationTag.HALLWAY, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_3", Set.of(DecorationMarkerBlock.DecorationTag.HALLWAY, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_20", Set.of(DecorationMarkerBlock.DecorationTag.HALLWAY, DecorationMarkerBlock.DecorationTag.NONE)),
                        Map.entry("decoration_21", Set.of(DecorationMarkerBlock.DecorationTag.HALLWAY, DecorationMarkerBlock.DecorationTag.NONE)),
                        Map.entry("decoration_22", Set.of(
                                DecorationMarkerBlock.DecorationTag.NONE)),
                        Map.entry("decoration_4", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE,
                                DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_5", Set.of(DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_6", Set.of(DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_23", Set.of(DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE)),
                        Map.entry("decoration_24", Set.of(DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_25", Set.of(DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_28", Set.of(
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_19", Set.of(DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_18", Set.of(DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_33", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE,
                                DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_32", Set.of(
                                DecorationMarkerBlock.DecorationTag.NONE,
                                DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_31", Set.of(
                                DecorationMarkerBlock.DecorationTag.NONE,
                                DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_30", Set.of(
                                DecorationMarkerBlock.DecorationTag.NONE,
                                DecorationMarkerBlock.DecorationTag.POOLROOMS)),

                        Map.entry("decoration_7", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_8", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_9", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_10", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_11", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_12", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_13", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_14", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_15", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_16", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("decoration_17", Set.of(
                                DecorationMarkerBlock.DecorationTag.HALLWAY,
                                DecorationMarkerBlock.DecorationTag.NONE, DecorationMarkerBlock.DecorationTag.POOLROOMS)),

                        Map.entry("poolrooms_decoration_1", Set.of(
                                DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("poolrooms_decoration_2", Set.of(
                                DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("poolrooms_decoration_3", Set.of(
                                DecorationMarkerBlock.DecorationTag.POOLROOMS)),
                        Map.entry("poolrooms_decoration_4", Set.of(
                                DecorationMarkerBlock.DecorationTag.POOLROOMS))
                )
        );

        private final String resourceFolder;
        private final String identifierPrefix;
        private final Map<String, Double> weights;
        private final Map<String, ResourceKey<LootTable>> lootTables;
        private final Map<String, Set<DecorationMarkerBlock.DecorationTag>> tags;

        DecorationPool(String resourceFolder,
                       String identifierPrefix,
                       Map<String, Double> weights,
                       Map<String, ResourceKey<LootTable>> lootTables,
                       Map<String, Set<DecorationMarkerBlock.DecorationTag>> tags) {
            this.resourceFolder = resourceFolder;
            this.identifierPrefix = identifierPrefix;
            this.weights = weights;
            this.lootTables = lootTables;
            this.tags = tags;
        }

        @Override
        public String resourceFolder() { return resourceFolder; }
        @Override
        public String identifierPrefix() { return identifierPrefix; }
        @Override
        public boolean excludeStarterRoom() { return false; }
        @Override
        public Map<String, Double> weights() { return weights; }

        private String shortName(Identifier decorationId) {
            String path = decorationId.getPath();
            return path.startsWith(identifierPrefix) ? path.substring(identifierPrefix.length()) : path;
        }

        private ResourceKey<LootTable> lootTableFor(Identifier decorationId) {
            return lootTables.get(shortName(decorationId));
        }

        private Set<DecorationMarkerBlock.DecorationTag> tagsFor(Identifier decorationId) {
            return tags.getOrDefault(shortName(decorationId), Set.of(DecorationMarkerBlock.DecorationTag.NONE));
        }
    }

    private static final double DECORATION_SPAWN_CHANCE = 0.65;

    private static final Map<RoomPool, Map<Identifier, RoomTemplateInfo>> ROOM_CACHE = new EnumMap<>(RoomPool.class);
    private static final Map<RoomPool, List<Identifier>> CACHED_ROOM_IDS = new EnumMap<>(RoomPool.class);

    private static volatile List<Identifier> CACHED_DECORATION_IDS = null;

    private ArlroomsExpansionManager() {}

    public static void tick() {
        Iterator<Map.Entry<BlockPos, Integer>> it = MARKER_COOLDOWN.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<BlockPos, Integer> entry = it.next();
            int next = entry.getValue() - 1;

            if (next <= 0) it.remove();
            else entry.setValue(next);
        }
    }

    public static boolean tryExpand(ServerLevel level, BlockPos markerPos, ServerPlayer player) {
        if (level.dimension() != ModDimensions.ARLROOMS_LEVEL_KEY) return false;

        if (markerPos.getY() > 200 || markerPos.getY() < 30) return false;

        if (MARKER_COOLDOWN.getOrDefault(markerPos, 0) > 0) return false;

        BlockState worldMarkerState = level.getBlockState(markerPos);

        RoomPool pool = RoomPool.fromMarkerState(worldMarkerState);
        if (pool == null) return false;

        FrontAndTop targetOrientation = worldMarkerState.getValue(DoorMarkerBlock.ORIENTATION);
        Direction targetFront = targetOrientation.front();

        List<Identifier> rooms = weightedOrder(getRoomIds(level.getServer(), pool), level.getRandom(), pool);

        boolean placedAny = false;

        for (Identifier roomId : rooms) {

            RoomTemplateInfo info = loadRoomInfo(level.getServer(), roomId, pool);
            if (info == null || info.doors().isEmpty()) continue;

            StructureTemplate template = level.getServer().getStructureManager().getOrCreate(roomId);
            if (template == null) continue;

            for (DoorMarkerInfo roomDoor : info.doors()) {

                for (Rotation rotation : ROTATIONS) {

                    Direction rotatedFront = rotateDirection(roomDoor.orientation().front(), rotation);

                    if (rotatedFront.getAxis() != targetFront.getAxis()) continue;

                    BlockPos rotatedDoorPos = StructureTemplate.transform(
                            roomDoor.localPos(),
                            Mirror.NONE,
                            rotation,
                            BlockPos.ZERO
                    );

                    BlockPos targetPos = markerPos.relative(targetFront);
                    BlockPos origin = targetPos.subtract(rotatedDoorPos);

                    StructurePlaceSettings settings = new StructurePlaceSettings()
                            .setRotation(rotation)
                            .setMirror(Mirror.NONE);

                    BoundingBox box = template.getBoundingBox(settings, origin);

                    if (!canPlace(level, box)) continue;

                    if (place(level, template, origin, rotation, box)) {

                        ArlroomsSavedData.get(level).addBox(box);

                        BlockPos worldDoorA = markerPos;
                        BlockPos worldDoorB = origin.offset(roomDoor.localPos().rotate(rotation));

                        Direction forwardA = targetFront;
                        Direction forwardB = rotatedFront;

                        clearDoor(level, worldDoorA, forwardA);
                        clearDoor(level, worldDoorB, forwardB);

                        spawnDecorations(level, origin, rotation, info);
                        maybeBlackoutRoom(level, box);

                        ChunkPos minChunk = ChunkPos.containing(new BlockPos(box.minX(), box.minY(), box.minZ()));
                        ChunkPos maxChunk = ChunkPos.containing(new BlockPos(box.maxX(), box.maxY(), box.maxZ()));

                        for (int cx = minChunk.x(); cx <= maxChunk.x(); cx++) {
                            for (int cz = minChunk.z(); cz <= maxChunk.z(); cz++) {
                                LevelChunk chunk = level.getChunk(cx, cz);
                                level.getChunkSource().chunkMap.getPlayers(new ChunkPos(cx, cz), false)
                                        .forEach(p -> p.connection.send(new ClientboundLevelChunkWithLightPacket(
                                                chunk, level.getLightEngine(), null, null
                                        )));
                            }
                        }

                        placedAny = true;
                        break;
                    }
                }

                if (placedAny) break;
            }

            if (placedAny) break;
        }

        if (placedAny) {
            MARKER_ATTEMPTS.remove(markerPos);
            return true;
        }

        int attempts = MARKER_ATTEMPTS.merge(markerPos, 1, Integer::sum);

        if (attempts >= ATTEMPT_LIMIT) {
            level.setBlock(markerPos, pool.fallbackBlock.get().defaultBlockState(), 3);
            MARKER_ATTEMPTS.remove(markerPos);
            MARKER_COOLDOWN.remove(markerPos);
            return false;
        }

        MARKER_COOLDOWN.put(markerPos, FAILURE_COOLDOWN_TICKS);
        return false;
    }

    private static final float BLACKOUT_CHANCE = 0.05F;

    private static void maybeBlackoutRoom(ServerLevel level, BoundingBox box) {
        if (level.getRandom().nextFloat() >= BLACKOUT_CHANCE) return;

        BlockPos.betweenClosedStream(box).forEach(pos -> {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof CeilingLightBlock
                    && state.getValue(CeilingLightBlock.LIT)) {
                level.setBlock(pos, state.setValue(CeilingLightBlock.LIT, false), 3);
            }
        });
    }

    private static void clearDoor(ServerLevel level, BlockPos topMiddle, Direction forward) {

        Direction right = forward.getClockWise();

        for (int r = -1; r <= 1; r++) {
            for (int y = 0; y < 4; y++) {
                BlockPos pos = topMiddle
                        .relative(right, r)
                        .relative(Direction.DOWN, y);

                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    private static void spawnDecorations(ServerLevel level,
                                         BlockPos roomOrigin,
                                         Rotation roomRotation,
                                         RoomTemplateInfo roomInfo) {

        if (roomInfo.decorationAnchors().isEmpty()) return;

        MinecraftServer server = level.getServer();
        List<Identifier> allDecorations = getDecorationIds(server);
        if (allDecorations.isEmpty()) return;

        RandomSource random = level.getRandom();

        for (DecorationAnchorInfo anchor : roomInfo.decorationAnchors()) {

            BlockPos worldAnchorPos = roomOrigin.offset(anchor.localPos().rotate(roomRotation));

            boolean placedDecoration = false;

            if (random.nextDouble() < DECORATION_SPAWN_CHANCE) {

                List<Identifier> eligibleDecorations = allDecorations.stream()
                        .filter(id -> DecorationPool.DECORATIONS.tagsFor(id).contains(anchor.tag()))
                        .toList();

                if (!eligibleDecorations.isEmpty()) {

                    List<Identifier> decorationOrder =
                            weightedOrder(eligibleDecorations, random, DecorationPool.DECORATIONS);

                    for (Identifier decorationId : decorationOrder) {

                        StructureTemplate decoTemplate = server.getStructureManager().getOrCreate(decorationId);
                        if (decoTemplate == null) continue;

                        DecorationPlacement placement =
                                findFittingDecorationPlacement(level, decoTemplate, worldAnchorPos, random);

                        if (placement == null) continue;

                        level.setBlock(worldAnchorPos, Blocks.AIR.defaultBlockState(), 3);

                        if (place(level, decoTemplate, placement.origin(), placement.rotation(), placement.box())) {
                            assignChestLootTables(level, placement.box(), decorationId);
                            placedDecoration = true;
                            break;
                        }
                    }
                }
            }

            if (!placedDecoration) {
                level.setBlock(worldAnchorPos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    private static DecorationPlacement findFittingDecorationPlacement(ServerLevel level,
                                                                      StructureTemplate decoTemplate,
                                                                      BlockPos worldAnchorPos,
                                                                      RandomSource random) {

        List<Rotation> shuffledRotations = new ArrayList<>(List.of(ROTATIONS));
        Collections.shuffle(shuffledRotations, new Random(random.nextLong()));

        for (Rotation rotation : shuffledRotations) {

            Vec3i size = decoTemplate.getSize();

            boolean swapXZ = rotation == Rotation.CLOCKWISE_90 || rotation == Rotation.COUNTERCLOCKWISE_90;

            int footprintX = swapXZ ? size.getZ() : size.getX();
            int footprintZ = swapXZ ? size.getX() : size.getZ();

            BlockPos decoOrigin = new BlockPos(
                    worldAnchorPos.getX() - footprintX / 2,
                    worldAnchorPos.getY(),
                    worldAnchorPos.getZ() - footprintZ / 2
            );

            StructurePlaceSettings decoSettings = new StructurePlaceSettings()
                    .setRotation(rotation)
                    .setMirror(Mirror.NONE);

            BoundingBox decoBox = decoTemplate.getBoundingBox(decoSettings, decoOrigin);

            if (canPlaceDecoration(level, decoTemplate, decoOrigin, rotation, worldAnchorPos)) {
                return new DecorationPlacement(decoOrigin, rotation, decoBox);
            }
        }

        return null;
    }

    private static boolean canPlaceDecoration(ServerLevel level,
                                              StructureTemplate template,
                                              BlockPos origin,
                                              Rotation rotation,
                                              BlockPos anchorPos) {

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setRotation(rotation)
                .setMirror(Mirror.NONE);

        Set<BlockPos> airLocalPositions = new HashSet<>();
        for (StructureTemplate.StructureBlockInfo info :
                template.filterBlocks(BlockPos.ZERO, settings, Blocks.AIR)) {
            airLocalPositions.add(info.pos());
        }

        Set<BlockPos> voidLocalPositions = new HashSet<>();
        for (StructureTemplate.StructureBlockInfo info :
                template.filterBlocks(BlockPos.ZERO, settings, Blocks.STRUCTURE_VOID)) {
            voidLocalPositions.add(info.pos());
        }

        BoundingBox localBox = template.getBoundingBox(settings, BlockPos.ZERO);

        for (BlockPos localPos : BlockPos.betweenClosed(
                new BlockPos(localBox.minX(), localBox.minY(), localBox.minZ()),
                new BlockPos(localBox.maxX(), localBox.maxY(), localBox.maxZ()))) {

            BlockPos localPosImmutable = localPos.immutable();

            if (airLocalPositions.contains(localPosImmutable)) continue;
            if (voidLocalPositions.contains(localPosImmutable)) continue;

            BlockPos worldPos = localPosImmutable.offset(origin);

            if (worldPos.equals(anchorPos)) continue;

            BlockState existing = level.getBlockState(worldPos);

            if (!existing.isAir()
                    && !existing.canBeReplaced()
                    && !existing.is(Blocks.STRUCTURE_VOID)) {
                return false;
            }
        }

        return true;
    }

    private record DecorationPlacement(BlockPos origin, Rotation rotation, BoundingBox box) {}

    private static void assignChestLootTables(ServerLevel level, BoundingBox box, Identifier decorationId) {

        ResourceKey<LootTable> lootTable = DecorationPool.DECORATIONS.lootTableFor(decorationId);
        if (lootTable == null) return;

        BlockPos.betweenClosedStream(box).forEach(pos -> {
            BlockEntity blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof RandomizableContainerBlockEntity container) {
                container.setLootTable(lootTable);
                container.setLootTableSeed(level.getRandom().nextLong());
            }
        });
    }

    private static boolean canPlace(ServerLevel level, BoundingBox candidateBox) {
        AABB candidateAABB = AABB.of(candidateBox).deflate(0.25);

        ArlroomsSavedData savedData = ArlroomsSavedData.get(level);
        for (BoundingBox placed : savedData.getPlacedBoxes()) {
            if (placed.intersects(candidateBox)) {
                AABB placedAABB = AABB.of(placed).deflate(0.25);
                if (candidateAABB.intersects(placedAABB)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean place(ServerLevel level, StructureTemplate template,
                                 BlockPos origin, Rotation rotation, BoundingBox box) {

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setRotation(rotation)
                .setMirror(Mirror.NONE)
                .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);

        boolean success = template.placeInWorld(level, origin, origin, settings, level.getRandom(), 2);

        BlockPos.betweenClosedStream(box).forEach(pos -> {
            BlockState state = level.getBlockState(pos);
            level.updateNeighborsAt(pos, state.getBlock());
            level.sendBlockUpdated(pos, state, state, 3);

            if (state.isRandomlyTicking()) {
                level.scheduleTick(pos, state.getBlock(), 1);
            }
        });

        BlockPos.betweenClosedStream(box).forEach(pos -> {
            level.getLightEngine().checkBlock(pos);
        });

        ArlroomsExpansionManager.queueChunkResend(box);

        return success;
    }

    private static final Map<BoundingBox, Integer> PENDING_RESENDS = new HashMap<>();

    public static void queueChunkResend(BoundingBox box) {
        PENDING_RESENDS.put(box, 5);
    }

    public static void tickResends(MinecraftServer server) {
        if (PENDING_RESENDS.isEmpty()) return;

        ServerLevel level = server.getLevel(ModDimensions.ARLROOMS_LEVEL_KEY);
        if (level == null) return;

        Iterator<Map.Entry<BoundingBox, Integer>> it = PENDING_RESENDS.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<BoundingBox, Integer> entry = it.next();
            int ticksLeft = entry.getValue() - 1;

            if (ticksLeft > 0) {
                entry.setValue(ticksLeft);
                continue;
            }

            it.remove();

            BoundingBox box = entry.getKey();
            ChunkPos minChunk = ChunkPos.containing(new BlockPos(box.minX(), box.minY(), box.minZ()));
            ChunkPos maxChunk = ChunkPos.containing(new BlockPos(box.maxX(), box.maxY(), box.maxZ()));

            for (int cx = minChunk.x() - 1; cx <= maxChunk.x() + 1; cx++) {
                for (int cz = minChunk.z() - 1; cz <= maxChunk.z() + 1; cz++) {
                    if (!level.hasChunk(cx, cz)) continue;
                    LevelChunk chunk = level.getChunk(cx, cz);
                    level.getChunkSource().chunkMap.getPlayers(new ChunkPos(cx, cz), false)
                            .forEach(p -> p.connection.send(new ClientboundLevelChunkWithLightPacket(
                                    chunk, level.getLightEngine(), null, null
                            )));
                }
            }
        }
    }

    private static RoomTemplateInfo loadRoomInfo(MinecraftServer server, Identifier roomId, RoomPool pool) {

        Map<Identifier, RoomTemplateInfo> cache =
                ROOM_CACHE.computeIfAbsent(pool, p -> new HashMap<>());

        if (cache.containsKey(roomId)) return cache.get(roomId);

        StructureTemplate template = server.getStructureManager().getOrCreate(roomId);
        if (template == null) return null;

        Vec3i size = template.getSize();

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setMirror(Mirror.NONE)
                .setRotation(Rotation.NONE);

        List<StructureTemplate.StructureBlockInfo> doorBlocks =
                template.filterBlocks(BlockPos.ZERO, settings, pool.markerBlock.get());

        List<DoorMarkerInfo> doors = new ArrayList<>();

        for (StructureTemplate.StructureBlockInfo info : doorBlocks) {
            BlockState state = info.state();
            doors.add(new DoorMarkerInfo(
                    info.pos(),
                    state.getValue(DoorMarkerBlock.ORIENTATION)
            ));
        }

        List<StructureTemplate.StructureBlockInfo> decorationBlocks =
                template.filterBlocks(BlockPos.ZERO, settings, ModBlocks.DECORATION_MARKER);

        List<DecorationAnchorInfo> decorationAnchors = new ArrayList<>();
        for (StructureTemplate.StructureBlockInfo info : decorationBlocks) {
            DecorationMarkerBlock.DecorationTag tag = info.state().getValue(DecorationMarkerBlock.TAG);
            decorationAnchors.add(new DecorationAnchorInfo(info.pos(), tag));
        }

        RoomTemplateInfo result = new RoomTemplateInfo(size, doors, decorationAnchors);
        cache.put(roomId, result);
        return result;
    }

    private static List<Identifier> getRoomIds(MinecraftServer server, RoomPool pool) {

        List<Identifier> cached = CACHED_ROOM_IDS.get(pool);
        if (cached != null) return cached;

        List<Identifier> result = listPoolIdentifiers(server, pool);

        CACHED_ROOM_IDS.put(pool, result);
        return result;
    }

    private static List<Identifier> getDecorationIds(MinecraftServer server) {

        if (CACHED_DECORATION_IDS != null) return CACHED_DECORATION_IDS;

        List<Identifier> result = listPoolIdentifiers(server, DecorationPool.DECORATIONS);

        CACHED_DECORATION_IDS = result;
        return result;
    }

    private static List<Identifier> listPoolIdentifiers(MinecraftServer server, WeightedPool pool) {

        Map<Identifier, ?> resources = server.getResourceManager().listResources(
                pool.resourceFolder(),
                id -> id.getPath().endsWith(".nbt")
        );

        String resourcePrefix = pool.resourceFolder() + "/";

        List<WeightedPool> allPools = new ArrayList<>();
        allPools.addAll(List.of(RoomPool.values()));
        allPools.addAll(List.of(DecorationPool.values()));

        List<Identifier> result = new ArrayList<>();

        for (Identifier id : resources.keySet()) {

            String path = id.getPath();

            if (!path.startsWith(resourcePrefix)) continue;

            boolean belongsToAnotherPool = false;
            for (WeightedPool other : allPools) {
                if (other == pool) continue;
                String otherResourcePrefix = other.resourceFolder() + "/";
                if (otherResourcePrefix.startsWith(resourcePrefix) && path.startsWith(otherResourcePrefix)) {
                    belongsToAnotherPool = true;
                    break;
                }
            }
            if (belongsToAnotherPool) continue;

            String name = path.substring(resourcePrefix.length(), path.length() - 4);

            Identifier clean = Identifier.fromNamespaceAndPath(
                    id.getNamespace(),
                    pool.identifierPrefix() + name
            );

            if (pool.excludeStarterRoom() && clean.getPath().endsWith("starter_room")) continue;

            result.add(clean);
        }

        return List.copyOf(result);
    }

    private static List<Identifier> weightedOrder(List<Identifier> ids, RandomSource random, WeightedPool pool) {
        List<Identifier> remaining = new ArrayList<>(ids);
        List<Identifier> ordered = new ArrayList<>(remaining.size());

        while (!remaining.isEmpty()) {
            double totalWeight = 0.0;
            for (Identifier id : remaining) {
                totalWeight += poolWeight(id, pool);
            }

            double roll = random.nextDouble() * totalWeight;
            double cumulative = 0.0;
            int chosenIndex = remaining.size() - 1;

            for (int i = 0; i < remaining.size(); i++) {
                cumulative += poolWeight(remaining.get(i), pool);
                if (roll < cumulative) {
                    chosenIndex = i;
                    break;
                }
            }

            ordered.add(remaining.remove(chosenIndex));
        }

        return ordered;
    }

    private static double poolWeight(Identifier id, WeightedPool pool) {
        String path = id.getPath();
        String prefix = pool.identifierPrefix();
        String shortName = path.startsWith(prefix) ? path.substring(prefix.length()) : path;

        return pool.weights().getOrDefault(shortName, DEFAULT_ROOM_WEIGHT);
    }

    private static Direction rotateDirection(Direction dir, Rotation rot) {

        if (dir.getAxis().isVertical()) return dir;

        return switch (rot) {
            case NONE -> dir;
            case CLOCKWISE_90 -> dir.getClockWise();
            case CLOCKWISE_180 -> dir.getOpposite();
            case COUNTERCLOCKWISE_90 -> dir.getCounterClockWise();
        };
    }

    private record RoomTemplateInfo(Vec3i size, List<DoorMarkerInfo> doors, List<DecorationAnchorInfo> decorationAnchors) {}

    public record DoorMarkerInfo(BlockPos localPos, FrontAndTop orientation) {}

    public record DecorationAnchorInfo(BlockPos localPos, DecorationMarkerBlock.DecorationTag tag) {}
}