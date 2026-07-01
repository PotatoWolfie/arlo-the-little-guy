package potatowolfie.arlo_the_little_guy.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

public class ModTags {
    public static class Item {
        public static final TagKey<net.minecraft.world.item.Item> HAT = createTag("hat");

        private static TagKey<net.minecraft.world.item.Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> NO_CLIP_BLOCKS = createTag("no_clip_blocks");
        public static final TagKey<Block> ARLROOMS_BLOCKS = createTag("arlrooms_blocks");

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name));
        }
    }
}
