package potatowolfie.arlo_the_little_guy.structure;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.*;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.List;

public class CactusMonumentGenerator {
    private static final Identifier MAIN_PIECE =
            Identifier.of("arlo-the-little-guy", "cactus_monument/cactus_monument");

    private static final Identifier[] SIDE_PIECES = new Identifier[] {
            Identifier.of("arlo-the-little-guy", "cactus_monument/cactus_monument_piece_1"),
            Identifier.of("arlo-the-little-guy", "cactus_monument/cactus_monument_piece_2"),
            Identifier.of("arlo-the-little-guy", "cactus_monument/cactus_monument_piece_3"),
            Identifier.of("arlo-the-little-guy", "cactus_monument/cactus_monument_piece_4")
    };

    public static void addPieces(
            StructureTemplateManager manager,
            BlockPos pos,
            BlockRotation rotation,
            StructurePiecesHolder holder,
            Random random
    ) {
        holder.addPiece(new Piece(manager, MAIN_PIECE, pos, rotation, true));

        int pieceCount = MathHelper.nextInt(random, 2, 4);
        addSurroundingPieces(manager, random, rotation, pos, holder, pieceCount);
    }

    private static void addSurroundingPieces(
            StructureTemplateManager manager,
            Random random,
            BlockRotation centerRotation,
            BlockPos centerPos,
            StructurePiecesHolder pieces,
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
            BlockRotation pieceRotation = BlockRotation.random(random);

            Identifier pieceTemplate = Util.getRandom(SIDE_PIECES, random);

            pieces.addPiece(new Piece(manager, pieceTemplate, piecePos, pieceRotation, false));
        }
    }

    private static List<BlockPos> getPiecePositions(Random random, BlockPos centerPos) {
        List<BlockPos> list = Lists.newArrayList();

        list.add(centerPos.add(-10 + MathHelper.nextInt(random, 1, 3), 0, 10 + MathHelper.nextInt(random, 1, 3)));
        list.add(centerPos.add(MathHelper.nextInt(random, 1, 3), 0, 10 + MathHelper.nextInt(random, 1, 3)));
        list.add(centerPos.add(10 + MathHelper.nextInt(random, 1, 3), 0, 10 + MathHelper.nextInt(random, 1, 3)));

        list.add(centerPos.add(-10 + MathHelper.nextInt(random, 1, 3), 0, -10 - MathHelper.nextInt(random, 1, 3)));
        list.add(centerPos.add(MathHelper.nextInt(random, 1, 3), 0, -10 - MathHelper.nextInt(random, 1, 3)));
        list.add(centerPos.add(10 + MathHelper.nextInt(random, 1, 3), 0, -10 - MathHelper.nextInt(random, 1, 3)));

        list.add(centerPos.add(10 + MathHelper.nextInt(random, 1, 3), 0, -10 + MathHelper.nextInt(random, 1, 3)));
        list.add(centerPos.add(10 + MathHelper.nextInt(random, 1, 3), 0, MathHelper.nextInt(random, 1, 3)));

        list.add(centerPos.add(-10 - MathHelper.nextInt(random, 1, 3), 0, -10 + MathHelper.nextInt(random, 1, 3)));
        list.add(centerPos.add(-10 - MathHelper.nextInt(random, 1, 3), 0, MathHelper.nextInt(random, 1, 3)));

        return list;
    }

    public static class Piece extends SimpleStructurePiece {
        private final boolean isMainPiece;

        public Piece(
                StructureTemplateManager manager,
                Identifier template,
                BlockPos pos,
                BlockRotation rotation,
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

        public Piece(StructureTemplateManager manager, NbtCompound nbt) {
            super(
                    ModStructurePieceTypes.CACTUS_MONUMENT,
                    nbt,
                    manager,
                    (identifier) -> createPlacementData(
                            nbt.get("Rot", BlockRotation.CODEC).orElse(BlockRotation.NONE)
                    )
            );
            this.isMainPiece = nbt.getBoolean("IsMainPiece").orElse(false);
        }

        private static StructurePlacementData createPlacementData(BlockRotation rotation) {
            return new StructurePlacementData()
                    .setRotation(rotation)
                    .setMirror(BlockMirror.NONE)
                    .addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
        }

        protected void writeNbt(StructureContext context, NbtCompound nbt) {
            super.writeNbt(context, nbt);
            nbt.put("Rot", BlockRotation.CODEC, this.placementData.getRotation());
            nbt.putBoolean("IsMainPiece", this.isMainPiece);
        }

        protected void handleMetadata(
                String metadata,
                BlockPos pos,
                ServerWorldAccess world,
                Random random,
                BlockBox boundingBox
        ) {
            if (!this.isMainPiece) {
                return;
            }

            if (metadata.equals("chest")) {
                this.placeChestWithLoot(world, boundingBox, random, pos,
                        Identifier.of("arlo-the-little-guy", "chests/cactus_monument"));
            }
        }

        private void placeChestWithLoot(
                ServerWorldAccess world,
                BlockBox boundingBox,
                Random random,
                BlockPos structureBlockPos,
                Identifier lootTable
        ) {
            for (Direction direction : Direction.values()) {
                BlockPos chestPos = structureBlockPos.offset(direction);

                if (!boundingBox.contains(chestPos)) {
                    continue;
                }

                BlockState state = world.getBlockState(chestPos);

                if (state.isOf(Blocks.CHEST)) {
                    BlockEntity blockEntity = world.getBlockEntity(chestPos);
                    if (blockEntity instanceof ChestBlockEntity chestEntity) {
                        chestEntity.setLootTable(
                                RegistryKey.of(RegistryKeys.LOOT_TABLE, lootTable),
                                random.nextLong()
                        );
                        return;
                    }
                }
            }
        }

        private void fillUnderStructure(StructureWorldAccess world, BlockBox chunkBox, Random random) {
            BlockPos structureStart = this.pos;
            BlockPos structureEnd = StructureTemplate.transformAround(
                    new BlockPos(this.template.getSize().getX() - 1, this.template.getSize().getY() - 1, this.template.getSize().getZ() - 1),
                    BlockMirror.NONE,
                    this.placementData.getRotation(),
                    BlockPos.ORIGIN
            ).add(this.pos);

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
                            !bottomState.isOf(Blocks.STRUCTURE_VOID);

                    if (hasBottomBlock && bottomState.isOf(Blocks.WATER)) {
                        hasBottomBlock = false;
                    }

                    if (hasBottomBlock) {
                        BlockPos belowPos = new BlockPos(x, minY - 1, z);
                        BlockState belowState = world.getBlockState(belowPos);

                        if (belowState.isAir() || belowState.getFluidState().isIn(FluidTags.WATER)) {
                            int terrainY = findTerrainHeight(world, x, z, minY - 1);

                            if (terrainY < minY - 1) {
                                createTaperedPillar(world, x, z, terrainY, minY - 1, random);
                            }
                        }
                    }
                }
            }
        }

        private int findTerrainHeight(StructureWorldAccess world, int x, int z, int startY) {
            for (int y = startY; y >= world.getBottomY(); y--) {
                BlockPos pos = new BlockPos(x, y, z);
                BlockState state = world.getBlockState(pos);

                if (!state.isAir() && !state.getFluidState().isIn(FluidTags.WATER)) {
                    return y;
                }
            }
            return world.getBottomY();
        }

        private void createTaperedPillar(StructureWorldAccess world, int centerX, int centerZ, int terrainY, int topY, Random random) {
            int pillarHeight = topY - terrainY;

            SimplexNoiseSampler noiseSampler = new SimplexNoiseSampler(random);
            float baseRadiusMultiplier = 2.0f + random.nextFloat() * 1.5f;
            float taperingCurve = 0.7f + random.nextFloat() * 0.6f;
            if (pillarHeight > 10) {
                baseRadiusMultiplier += (pillarHeight - 10) * 0.1f;
            }

            for (int y = terrainY + 1; y <= topY; y++) {
                float progress = (float)(y - terrainY) / (float)pillarHeight;

                double noiseScale = 0.1;
                double noise = noiseSampler.sample(centerX * noiseScale, y * noiseScale * 0.5, centerZ * noiseScale);
                float noiseOffset = (float)noise * 0.5f;
                float radiusFloat = (1.0f - (float)Math.pow(progress, taperingCurve)) * baseRadiusMultiplier + noiseOffset;
                int radius = Math.max(0, (int)Math.ceil(radiusFloat));

                for (int dx = -radius - 1; dx <= radius + 1; dx++) {
                    for (int dz = -radius - 1; dz <= radius + 1; dz++) {
                        int actualX = centerX + dx;
                        int actualZ = centerZ + dz;

                        double distance = Math.sqrt(dx * dx + dz * dz);
                        double edgeNoise = noiseSampler.sample(actualX * 0.3, y * 0.2, actualZ * 0.3);
                        float edgeVariation = (float)edgeNoise * 0.8f;

                        float threshold = radius + 0.5f + edgeVariation;

                        if (distance <= threshold) {
                            BlockPos fillPos = new BlockPos(actualX, y, actualZ);
                            BlockState currentState = world.getBlockState(fillPos);

                            if (currentState.isAir() || currentState.getFluidState().isIn(FluidTags.WATER)) {
                                BlockState blockToPlace = getTerrainMatchingBlock(world, centerX, centerZ, terrainY, random, y, terrainY);
                                world.setBlockState(fillPos, blockToPlace, 3);
                            }
                        }
                    }
                }
            }
        }

        private BlockState getTerrainMatchingBlock(StructureWorldAccess world, int x, int z, int terrainY, Random random, int currentY, int baseY) {
            BlockPos terrainPos = new BlockPos(x, terrainY, z);
            BlockState terrainBlock = world.getBlockState(terrainPos);

            int depthFromBase = currentY - baseY;
            float depthRatio = (float)depthFromBase / (float)(terrainY - baseY + 1);

            if (terrainBlock.isOf(Blocks.SAND) || terrainBlock.isOf(Blocks.SANDSTONE)) {
                if (depthRatio < 0.3f) {
                    return Blocks.SANDSTONE.getDefaultState();
                } else if (depthRatio < 0.6f) {
                    return random.nextFloat() < 0.5f ? Blocks.SANDSTONE.getDefaultState() : Blocks.SAND.getDefaultState();
                } else {
                    return Blocks.SAND.getDefaultState();
                }
            }

            if (terrainBlock.isOf(Blocks.STONE) || terrainBlock.isOf(Blocks.COBBLESTONE) ||
                    terrainBlock.isOf(Blocks.ANDESITE) || terrainBlock.isOf(Blocks.DIORITE) ||
                    terrainBlock.isOf(Blocks.GRANITE)) {

                if (depthRatio < 0.4f) {
                    return Blocks.STONE.getDefaultState();
                } else {
                    float r = random.nextFloat();
                    if (r < 0.4f) return Blocks.STONE.getDefaultState();
                    else if (r < 0.6f) return Blocks.ANDESITE.getDefaultState();
                    else if (r < 0.8f) return Blocks.COBBLESTONE.getDefaultState();
                    else return terrainBlock;
                }
            }

            if (terrainBlock.isOf(Blocks.DIRT) || terrainBlock.isOf(Blocks.GRASS_BLOCK) ||
                    terrainBlock.isOf(Blocks.COARSE_DIRT)) {

                if (depthRatio < 0.3f) {
                    return Blocks.STONE.getDefaultState();
                } else if (depthRatio < 0.6f) {
                    return random.nextFloat() < 0.5f ? Blocks.COARSE_DIRT.getDefaultState() : Blocks.DIRT.getDefaultState();
                } else {
                    return Blocks.DIRT.getDefaultState();
                }
            }

            if (terrainBlock.isOf(Blocks.GRAVEL)) {
                if (depthRatio < 0.4f) {
                    return random.nextFloat() < 0.6f ? Blocks.STONE.getDefaultState() : Blocks.GRAVEL.getDefaultState();
                } else {
                    return Blocks.GRAVEL.getDefaultState();
                }
            }

            if (depthRatio < 0.4f) {
                return Blocks.SANDSTONE.getDefaultState();
            } else {
                return Blocks.SAND.getDefaultState();
            }
        }

        public void generate(
                StructureWorldAccess world,
                StructureAccessor structureAccessor,
                ChunkGenerator chunkGenerator,
                Random random,
                BlockBox chunkBox,
                ChunkPos chunkPos,
                BlockPos pivot
        ) {
            int groundY = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, this.pos.getX(), this.pos.getZ());
            this.pos = new BlockPos(this.pos.getX(), groundY, this.pos.getZ());

            super.generate(world, structureAccessor, chunkGenerator, random, chunkBox, chunkPos, pivot);
            fillUnderStructure(world, chunkBox, random);

            this.template.getInfosForBlock(this.pos, this.placementData, Blocks.STRUCTURE_BLOCK, false)
                    .forEach(structureBlockInfo -> {
                        if (structureBlockInfo.nbt() != null) {
                            String metadata = structureBlockInfo.nbt().getString("metadata", "");
                            if (!metadata.isEmpty()) {
                                this.handleMetadata(
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