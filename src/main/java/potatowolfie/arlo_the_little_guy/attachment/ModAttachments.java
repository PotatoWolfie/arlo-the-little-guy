package potatowolfie.arlo_the_little_guy.attachment;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;

public class ModAttachments {

    public static final AttachmentType<Integer> ARLROOMS_TIME =
            AttachmentRegistry.create(
                    Identifier.fromNamespaceAndPath(
                            "arlo-the-little-guy",
                            "arlrooms_time"
                    ),
                    builder -> builder
                            .initializer(() -> 0)
                            .persistent(Codec.INT)
                            .copyOnDeath()
            );

    public static final AttachmentType<Integer> STARE_TIME =
            AttachmentRegistry.create(
                    Identifier.fromNamespaceAndPath(
                            "arlo-the-little-guy",
                            "arlo_stare_time"
                    ),
                    builder -> builder
                            .initializer(() -> 0)
                            .persistent(Codec.INT)
                            .copyOnDeath()
            );

    public static void initialize() {

    }
}