package potatowolfie.arlo_the_little_guy.structure;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

import java.util.List;

public class CactusMonumentGenerator {
    private static final Identifier MAIN_PIECE =
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "cactus_monument/cactus_monument");

    private static final Identifier[] SIDE_PIECES = new Identifier[] {
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "cactus_monument/cactus_monument_piece_1"),
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "cactus_monument/cactus_monument_piece_2"),
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "cactus_monument/cactus_monument_piece_3"),
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "cactus_monument/cactus_monument_piece_4")
    };

    public static void addPieces(
            StructureTemplateManager manager,
            BlockPos pos,
            Rotation rotation,
            StructurePieceAccessor holder,
            RandomSource random
    ) {
        holder.addPiece(new Piece(manager, MAIN_PIECE, pos, rotation, true));

        int pieceCount = Mth.nextInt(random, 2, 4);
        addSurroundingPieces(manager, random, rotation, pos, holder, pieceCount);
    }

    private static void addSurroundingPieces(
            StructureTemplateManager manager,
            RandomSource random,
            Rotation centerRotation,
            BlockPos centerPos,
            StructurePieceAccessor pieces,
            int count
    ) {
        BlockPos blockPos3 = new BlockPos(centerPos.getX(), 0, centerPos.getZ());

        List<BlockPos> positions = getPiecePositions(random, blockPos3);

        for (int i = 0; i < count; i++) {
            if (positions.isEmpty()) {
                break;
            }

            int index = random.nextInt(positions.size());
            BlockPos piecePos = positions.remove(index);
            Rotation pieceRotation = Rotation.getRandom(random);

            Identifier pieceTemplate = Util.getRandom(SIDE_PIECES, random);

            pieces.addPiece(new Piece(manager, pieceTemplate, piecePos, pieceRotation, false));
        }
    }

    private static List<BlockPos> getPiecePositions(RandomSource random, BlockPos centerPos) {
        List<BlockPos> list = Lists.newArrayList();

        list.add(centerPos.offset(-10 + Mth.nextInt(random, 1, 3), 0, 10 + Mth.nextInt(random, 1, 3)));
        list.add(centerPos.offset(Mth.nextInt(random, 1, 3), 0, 10 + Mth.nextInt(random, 1, 3)));
        list.add(centerPos.offset(10 + Mth.nextInt(random, 1, 3), 0, 10 + Mth.nextInt(random, 1, 3)));

        list.add(centerPos.offset(-10 + Mth.nextInt(random, 1, 3), 0, -10 - Mth.nextInt(random, 1, 3)));
        list.add(centerPos.offset(Mth.nextInt(random, 1, 3), 0, -10 - Mth.nextInt(random, 1, 3)));
        list.add(centerPos.offset(10 + Mth.nextInt(random, 1, 3), 0, -10 - Mth.nextInt(random, 1, 3)));

        list.add(centerPos.offset(10 + Mth.nextInt(random, 1, 3), 0, -10 + Mth.nextInt(random, 1, 3)));
        list.add(centerPos.offset(10 + Mth.nextInt(random, 1, 3), 0, Mth.nextInt(random, 1, 3)));

        list.add(centerPos.offset(-10 - Mth.nextInt(random, 1, 3), 0, -10 + Mth.nextInt(random, 1, 3)));
        list.add(centerPos.offset(-10 - Mth.nextInt(random, 1, 3), 0, Mth.nextInt(random, 1, 3)));

        return list;
    }

    public static class Piece extends TemplateStructurePiece {
        private final boolean isMainPiece;

        public Piece(
                StructureTemplateManager manager,
                Identifier template,
                BlockPos pos,
                Rotation rotation,
                boolean isMainPiece
        ) {
            super(
                    ModStructurePieceTypes.CACTUS_MONUMENT,
                    0,
                    manager,
                    template,
                    template.toString(),
                    createPlacementData(rotation),
                    pos
            );
            this.isMainPiece = isMainPiece;
        }

        public Piece(StructureTemplateManager manager, CompoundTag nbt) {
            super(
                    ModStructurePieceTypes.CACTUS_MONUMENT,
                    nbt,
                    manager,
                    (identifier) -> createPlacementData(
                            nbt.read("Rot", Rotation.CODEC).orElse(Rotation.NONE)
                    )
            );
            this.isMainPiece = nbt.getBoolean("IsMainPiece").orElse(false);
        }

        private static StructurePlaceSettings createPlacementData(Rotation rotation) {
            return new StructurePlaceSettings()
                    .setRotation(rotation)
                    .setMirror(Mirror.NONE)
                    .addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
        }

        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag nbt) {
            super.addAdditionalSaveData(context, nbt);
            nbt.store("Rot", Rotation.CODEC, this.placeSettings.getRotation());
            nbt.putBoolean("IsMainPiece", this.isMainPiece);
        }

        protected void handleDataMarker(
                String metadata,
                BlockPos pos,
                ServerLevelAccessor world,
                RandomSource random,
                BoundingBox boundingBox
        ) {
            if (!this.isMainPiece) {
                return;
            }

            if (metadata.equals("chest")) {
                this.placeChestWithLoot(world, boundingBox, random, pos,
                        Identifier.fromNamespaceAndPath("arlo-the-little-guy", "chests/cactus_monument"));
            }
        }

        private void placeChestWithLoot(
                ServerLevelAccessor world,
                BoundingBox boundingBox,
                RandomSource random,
                BlockPos structureBlockPos,
                Identifier lootTable
        ) {
            for (Direction direction : Direction.values()) {
                BlockPos chestPos = structureBlockPos.relative(direction);

                if (!boundingBox.isInside(chestPos)) {
                    continue;
                }

                BlockState state = world.getBlockState(chestPos);

                if (state.is(Blocks.CHEST)) {
                    BlockEntity blockEntity = world.getBlockEntity(chestPos);
                    if (blockEntity instanceof ChestBlockEntity chestEntity) {
                        chestEntity.setLootTable(
                                ResourceKey.create(Registries.LOOT_TABLE, lootTable),
                                random.nextLong()
                        );
                        return;
                    }
                }
            }
        }

        private void fillUnderStructure(WorldGenLevel world, BoundingBox chunkBox, RandomSource random) {
            BlockPos structureStart = this.templatePosition;
            BlockPos structureEnd = StructureTemplate.transform(
                    new BlockPos(this.template.getSize().getX() - 1, this.template.getSize().getY() - 1, this.template.getSize().getZ() - 1),
                    Mirror.NONE,
                    this.placeSettings.getRotation(),
                    BlockPos.ZERO
            ).offset(this.templatePosition);

            int minX = Math.min(structureStart.getX(), structureEnd.getX());
            int maxX = Math.max(structureStart.getX(), structureEnd.getX());
            int minY = Math.min(structureStart.getY(), structureEnd.getY());
            int maxY = Math.max(structureStart.getY(), structureEnd.getY());
            int minZ = Math.min(structureStart.getZ(), structureEnd.getZ());
            int maxZ = Math.max(structureStart.getZ(), structureEnd.getZ());

            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos bottomPos = new BlockPos(x, minY, z);
                    BlockState bottomState = world.getBlockState(bottomPos);

                    boolean hasBottomBlock = !bottomState.isAir() &&
                            !bottomState.is(Blocks.STRUCTURE_VOID);

                    if (hasBottomBlock && bottomState.is(Blocks.WATER)) {
                        hasBottomBlock = false;
                    }

                    if (hasBottomBlock) {
                        BlockPos belowPos = new BlockPos(x, minY - 1, z);
                        BlockState belowState = world.getBlockState(belowPos);

                        if (belowState.isAir() || belowState.getFluidState().is(FluidTags.WATER)) {
                            int terrainY = findTerrainHeight(world, x, z, minY - 1);

                            if (terrainY < minY - 1) {
                                createTaperedPillar(world, x, z, terrainY, minY - 1, random);
                            }
                        }
                    }
                }
            }
        }

        private int findTerrainHeight(WorldGenLevel world, int x, int z, int startY) {
            for (int y = startY; y >= world.getMinY(); y--) {
                BlockPos pos = new BlockPos(x, y, z);
                BlockState state = world.getBlockState(pos);

                if (!state.isAir() && !state.getFluidState().is(FluidTags.WATER)) {
                    return y;
                }
            }
            return world.getMinY();
        }

        private void createTaperedPillar(WorldGenLevel world, int centerX, int centerZ, int terrainY, int topY, RandomSource random) {
            int pillarHeight = topY - terrainY;

            SimplexNoise noiseSampler = new SimplexNoise(random);
            float baseRadiusMultiplier = 2.0f + random.nextFloat() * 1.5f;
            float taperingCurve = 0.7f + random.nextFloat() * 0.6f;
            if (pillarHeight > 10) {
                baseRadiusMultiplier += (pillarHeight - 10) * 0.1f;
            }

            for (int y = terrainY + 1; y <= topY; y++) {
                float progress = (float)(y - terrainY) / (float)pillarHeight;

                double noiseScale = 0.1;
                double noise = noiseSampler.getValue(centerX * noiseScale, y * noiseScale * 0.5, centerZ * noiseScale);
                float noiseOffset = (float)noise * 0.5f;
                float radiusFloat = (1.0f - (float)Math.pow(progress, taperingCurve)) * baseRadiusMultiplier + noiseOffset;
                int radius = Math.max(0, (int)Math.ceil(radiusFloat));

                for (int dx = -radius - 1; dx <= radius + 1; dx++) {
                    for (int dz = -radius - 1; dz <= radius + 1; dz++) {
                        int actualX = centerX + dx;
                        int actualZ = centerZ + dz;

                        double distance = Math.sqrt(dx * dx + dz * dz);
                        double edgeNoise = noiseSampler.getValue(actualX * 0.3, y * 0.2, actualZ * 0.3);
                        float edgeVariation = (float)edgeNoise * 0.8f;

                        float threshold = radius + 0.5f + edgeVariation;

                        if (distance <= threshold) {
                            BlockPos fillPos = new BlockPos(actualX, y, actualZ);
                            BlockState currentState = world.getBlockState(fillPos);

                            if (currentState.isAir() || currentState.getFluidState().is(FluidTags.WATER)) {
                                BlockState blockToPlace = getTerrainMatchingBlock(world, centerX, centerZ, terrainY, random, y, terrainY);
                                world.setBlock(fillPos, blockToPlace, 3);
                            }
                        }
                    }
                }
            }
        }

        private BlockState getTerrainMatchingBlock(WorldGenLevel world, int x, int z, int terrainY, RandomSource random, int currentY, int baseY) {
            BlockPos terrainPos = new BlockPos(x, terrainY, z);
            BlockState terrainBlock = world.getBlockState(terrainPos);

            int depthFromBase = currentY - baseY;
            float depthRatio = (float)depthFromBase / (float)(terrainY - baseY + 1);

            if (terrainBlock.is(Blocks.SAND) || terrainBlock.is(Blocks.SANDSTONE)) {
                if (depthRatio < 0.3f) {
                    return Blocks.SANDSTONE.defaultBlockState();
                } else if (depthRatio < 0.6f) {
                    return random.nextFloat() < 0.5f ? Blocks.SANDSTONE.defaultBlockState() : Blocks.SAND.defaultBlockState();
                } else {
                    return Blocks.SAND.defaultBlockState();
                }
            }

            if (terrainBlock.is(Blocks.STONE) || terrainBlock.is(Blocks.COBBLESTONE) ||
                    terrainBlock.is(Blocks.ANDESITE) || terrainBlock.is(Blocks.DIORITE) ||
                    terrainBlock.is(Blocks.GRANITE)) {

                if (depthRatio < 0.4f) {
                    return Blocks.STONE.defaultBlockState();
                } else {
                    float r = random.nextFloat();
                    if (r < 0.4f) return Blocks.STONE.defaultBlockState();
                    else if (r < 0.6f) return Blocks.ANDESITE.defaultBlockState();
                    else if (r < 0.8f) return Blocks.COBBLESTONE.defaultBlockState();
                    else return terrainBlock;
                }
            }

            if (terrainBlock.is(Blocks.DIRT) || terrainBlock.is(Blocks.GRASS_BLOCK) ||
                    terrainBlock.is(Blocks.COARSE_DIRT)) {

                if (depthRatio < 0.3f) {
                    return Blocks.STONE.defaultBlockState();
                } else if (depthRatio < 0.6f) {
                    return random.nextFloat() < 0.5f ? Blocks.COARSE_DIRT.defaultBlockState() : Blocks.DIRT.defaultBlockState();
                } else {
                    return Blocks.DIRT.defaultBlockState();
                }
            }

            if (terrainBlock.is(Blocks.GRAVEL)) {
                if (depthRatio < 0.4f) {
                    return random.nextFloat() < 0.6f ? Blocks.STONE.defaultBlockState() : Blocks.GRAVEL.defaultBlockState();
                } else {
                    return Blocks.GRAVEL.defaultBlockState();
                }
            }

            if (depthRatio < 0.4f) {
                return Blocks.SANDSTONE.defaultBlockState();
            } else {
                return Blocks.SAND.defaultBlockState();
            }
        }

        public void postProcess(
                WorldGenLevel world,
                StructureManager structureAccessor,
                ChunkGenerator chunkGenerator,
                RandomSource random,
                BoundingBox chunkBox,
                ChunkPos chunkPos,
                BlockPos pivot
        ) {
            int groundY = world.getHeight(Heightmap.Types.WORLD_SURFACE_WG, this.templatePosition.getX(), this.templatePosition.getZ());
            this.templatePosition = new BlockPos(this.templatePosition.getX(), groundY, this.templatePosition.getZ());

            super.postProcess(world, structureAccessor, chunkGenerator, random, chunkBox, chunkPos, pivot);
            fillUnderStructure(world, chunkBox, random);

            this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.STRUCTURE_BLOCK, false)
                    .forEach(structureBlockInfo -> {
                        if (structureBlockInfo.nbt() != null) {
                            String metadata = structureBlockInfo.nbt().getStringOr("metadata", "");
                            if (!metadata.isEmpty()) {
                                this.handleDataMarker(
                                        metadata,
                                        structureBlockInfo.pos(),
                                        world,
                                        random,
                                        chunkBox
                                );
                            }
                        }
                    });
        }
    }
}