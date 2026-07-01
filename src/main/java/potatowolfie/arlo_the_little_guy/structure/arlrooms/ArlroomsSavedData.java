package potatowolfie.arlo_the_little_guy.structure.arlrooms;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.List;

public class ArlroomsSavedData extends SavedData {

    public static final Codec<BoundingBox> BOUNDING_BOX_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("minX").forGetter(BoundingBox::minX),
            Codec.INT.fieldOf("minY").forGetter(BoundingBox::minY),
            Codec.INT.fieldOf("minZ").forGetter(BoundingBox::minZ),
            Codec.INT.fieldOf("maxX").forGetter(BoundingBox::maxX),
            Codec.INT.fieldOf("maxY").forGetter(BoundingBox::maxY),
            Codec.INT.fieldOf("maxZ").forGetter(BoundingBox::maxZ)
    ).apply(instance, BoundingBox::new));

    public static final Codec<ArlroomsSavedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BOUNDING_BOX_CODEC.listOf().fieldOf("boxes").forGetter(d -> d.placedBoxes)
    ).apply(instance, ArlroomsSavedData::new));

    public static final SavedDataType<ArlroomsSavedData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlrooms_placed_rooms"),
            ArlroomsSavedData::new,
            CODEC,
            null
    );

    private final List<BoundingBox> placedBoxes;

    public ArlroomsSavedData() {
        this.placedBoxes = new ArrayList<>();
    }

    private ArlroomsSavedData(List<BoundingBox> placedBoxes) {
        this.placedBoxes = new ArrayList<>(placedBoxes);
    }

    public static ArlroomsSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public void addBox(BoundingBox box) {
        placedBoxes.add(box);
        setDirty();
    }

    public List<BoundingBox> getPlacedBoxes() {
        return placedBoxes;
    }
}