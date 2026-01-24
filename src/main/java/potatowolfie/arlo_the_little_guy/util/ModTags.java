package potatowolfie.arlo_the_little_guy.util;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

public class ModTags {
    public static class Item {
        public static final TagKey<net.minecraft.item.Item> HAT = createTag("hat");

        private static TagKey<net.minecraft.item.Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(ArloTheLittleGuy.MOD_ID, name));
        }
    }
}
