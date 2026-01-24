package potatowolfie.arlo_the_little_guy.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;

public class ModItems {
    public static final Item BOWLER_HAT = registerItem("bowler_hat",
            new Item(new Item.Settings()
                    .maxCount(1)
                    .component(DataComponentTypes.EQUIPPABLE,
                            EquippableComponent.builder(EquipmentSlot.HEAD)
                                    .build())
                    .registryKey(createItemRegistryKey("bowler_hat"))));

    public static final Item TRICORN = registerItem("tricorn",
            new Item(new Item.Settings()
                    .maxCount(1)
                    .component(DataComponentTypes.EQUIPPABLE,
                            EquippableComponent.builder(EquipmentSlot.HEAD)
                                    .build())
                    .registryKey(createItemRegistryKey("tricorn"))));

    public static final Item STRAW_HAT = registerItem("straw_hat",
            new Item(new Item.Settings()
                    .maxCount(1)
                    .component(DataComponentTypes.EQUIPPABLE,
                            EquippableComponent.builder(EquipmentSlot.HEAD)
                                    .build())
                    .registryKey(createItemRegistryKey("straw_hat"))));

    public static final Item COWBOY_HAT = registerItem("cowboy_hat",
            new Item(new Item.Settings()
                    .maxCount(1)
                    .component(DataComponentTypes.EQUIPPABLE,
                            EquippableComponent.builder(EquipmentSlot.HEAD)
                                    .build())
                    .registryKey(createItemRegistryKey("cowboy_hat"))));

    public static final Item TOP_HAT = registerItem("top_hat",
            new Item(new Item.Settings()
                    .maxCount(1)
                    .component(DataComponentTypes.EQUIPPABLE,
                            EquippableComponent.builder(EquipmentSlot.HEAD)
                                    .build())
                    .registryKey(createItemRegistryKey("top_hat"))));

    public static final Item SUN_HAT = registerItem("sun_hat",
            new Item(new Item.Settings()
                    .maxCount(1)
                    .component(DataComponentTypes.EQUIPPABLE,
                            EquippableComponent.builder(EquipmentSlot.HEAD)
                                    .build())
                    .registryKey(createItemRegistryKey("sun_hat"))));

    public static final Item CROWN = registerItem("crown",
            new Item(new Item.Settings()
                    .maxCount(1)
                    .component(DataComponentTypes.EQUIPPABLE,
                            EquippableComponent.builder(EquipmentSlot.HEAD)
                                    .build())
                    .registryKey(createItemRegistryKey("crown"))));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, createItemRegistryKey(name), item);
    }

    private static RegistryKey<Item> createItemRegistryKey(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ArloTheLittleGuy.MOD_ID, name));
    }

    private static void customToolsAndUtilities(FabricItemGroupEntries entries) {
        entries.addBefore(Items.SADDLE, BOWLER_HAT);
        entries.addAfter(BOWLER_HAT, STRAW_HAT);
        entries.addAfter(STRAW_HAT, COWBOY_HAT);
        entries.addAfter(COWBOY_HAT, TRICORN);
        entries.addAfter(TRICORN, TOP_HAT);
        entries.addAfter(TOP_HAT, SUN_HAT);
        entries.addBefore(Items.CARROT_ON_A_STICK, CROWN);
    }

    public static void registerModItems() {
        ArloTheLittleGuy.LOGGER.info("Registering Mod Items for " + ArloTheLittleGuy.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::customToolsAndUtilities);
    }
}
