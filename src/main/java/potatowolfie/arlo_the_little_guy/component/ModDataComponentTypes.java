package potatowolfie.arlo_the_little_guy.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;

public class ModDataComponentTypes {
    public static final DataComponentType<Unit> FLASHLIGHT_ON = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "flashlight_on"),
            DataComponentType.<Unit>builder().persistent(Unit.CODEC).build()
    );

    public static final DataComponentType<Long> FLASHLIGHT_LAST_TOGGLED = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath("arlo-the-little-guy", "flashlight_last_toggled"),
            DataComponentType.<Long>builder().persistent(Codec.LONG).build()
    );

    public static void register() {
    }
}