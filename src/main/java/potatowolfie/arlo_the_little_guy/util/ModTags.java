package potatowolfie.arlo_the_little_guy.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

public class ModTags {
    public static class Item {
        public static final TagKey<net.minecraft.world.item.Item> HAT = createTag("hat");

        private static TagKey<net.minecraft.world.item.Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name));
        }
    }
}
