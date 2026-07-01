package potatowolfie.arlo_the_little_guy.block.custom;

import net.minecraft.util.StringRepresentable;

public enum TapeType implements StringRepresentable {
    EMPTY("empty", 0),
    TAPE_1("tape_1", 1),
    TAPE_2("tape_2", 2),
    TAPE_3("tape_3", 3),
    TAPE_4("tape_4", 4),
    TAPE_5("tape_5", 5),
    TAPE_6("tape_6", 6),
    TAPE_7("tape_7", 7),
    TAPE_8("tape_8", 8);

    private final String name;
    private final int id;

    TapeType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static TapeType fromId(int id) {
        for (TapeType type : values()) {
            if (type.getId() == id) return type;
        }
        return EMPTY;
    }
}