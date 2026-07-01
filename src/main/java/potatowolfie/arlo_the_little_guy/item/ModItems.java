package potatowolfie.arlo_the_little_guy.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.Equippable;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.item.custom.CactusFlashlightItem;

import java.util.List;

public class ModItems {
    public static final Item BOWLER_HAT = registerItem("bowler_hat",
            new Item(new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.EQUIPPABLE,
                            Equippable.builder(EquipmentSlot.HEAD)
                                    .build())
                    .setId(createItemRegistryKey("bowler_hat"))));

    public static final Item TRICORN = registerItem("tricorn",
            new Item(new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.EQUIPPABLE,
                            Equippable.builder(EquipmentSlot.HEAD)
                                    .build())
                    .setId(createItemRegistryKey("tricorn"))));

    public static final Item STRAW_HAT = registerItem("straw_hat",
            new Item(new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.EQUIPPABLE,
                            Equippable.builder(EquipmentSlot.HEAD)
                                    .build())
                    .setId(createItemRegistryKey("straw_hat"))));

    public static final Item COWBOY_HAT = registerItem("cowboy_hat",
            new Item(new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.EQUIPPABLE,
                            Equippable.builder(EquipmentSlot.HEAD)
                                    .build())
                    .setId(createItemRegistryKey("cowboy_hat"))));

    public static final Item TOP_HAT = registerItem("top_hat",
            new Item(new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.EQUIPPABLE,
                            Equippable.builder(EquipmentSlot.HEAD)
                                    .build())
                    .setId(createItemRegistryKey("top_hat"))));

    public static final Item SUN_HAT = registerItem("sun_hat",
            new Item(new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.EQUIPPABLE,
                            Equippable.builder(EquipmentSlot.HEAD)
                                    .build())
                    .setId(createItemRegistryKey("sun_hat"))));

    public static final Item CROWN = registerItem("crown",
            new Item(new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.EQUIPPABLE,
                            Equippable.builder(EquipmentSlot.HEAD)
                                    .build())
                    .setId(createItemRegistryKey("crown"))));

    public static final Item PRISMARINE_PIPIS = registerItem("prismarine_pipis",
            new Item(new Item.Properties()
                    .stacksTo(1)
                    .component(DataComponents.EQUIPPABLE,
                            Equippable.builder(EquipmentSlot.HEAD)
                                    .build())
                    .setId(createItemRegistryKey("prismarine_pipis"))));

    public static final Item CACTUS_FLASHLIGHT = registerItem("cactus_flashlight",
            new CactusFlashlightItem(new Item.Properties()
                    .stacksTo(1)
                    .setId(createItemRegistryKey("cactus_flashlight"))));

    public static final Item CACTUS_BATTERY = registerItem("cactus_battery",
            new Item(new Item.Properties()
                    .setId(createItemRegistryKey("cactus_battery"))));

    public static final Item CASSETTE_TAPE_1 = registerItem("cassette_tape_1",
            new Item(new Item.Properties().stacksTo(1).setId(createItemRegistryKey("cassette_tape_1"))));
    public static final Item CASSETTE_TAPE_2 = registerItem("cassette_tape_2",
            new Item(new Item.Properties().stacksTo(1).setId(createItemRegistryKey("cassette_tape_2"))));
    public static final Item CASSETTE_TAPE_3 = registerItem("cassette_tape_3",
            new Item(new Item.Properties().stacksTo(1).setId(createItemRegistryKey("cassette_tape_3"))));
    public static final Item CASSETTE_TAPE_4 = registerItem("cassette_tape_4",
            new Item(new Item.Properties().stacksTo(1).setId(createItemRegistryKey("cassette_tape_4"))));
    public static final Item CASSETTE_TAPE_5 = registerItem("cassette_tape_5",
            new Item(new Item.Properties().stacksTo(1).setId(createItemRegistryKey("cassette_tape_5"))));
    public static final Item CASSETTE_TAPE_6 = registerItem("cassette_tape_6",
            new Item(new Item.Properties().stacksTo(1).setId(createItemRegistryKey("cassette_tape_6"))));
    public static final Item CASSETTE_TAPE_7 = registerItem("cassette_tape_7",
            new Item(new Item.Properties().stacksTo(1).setId(createItemRegistryKey("cassette_tape_7"))));
    public static final Item CASSETTE_TAPE_QUESTION = registerItem("cassette_tape_question",
            new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).setId(createItemRegistryKey("cassette_tape_question"))));

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, createItemRegistryKey(name), item);
    }

    private static ResourceKey<Item> createItemRegistryKey(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, name));
    }

    public static void registerModItems() {
        ArloTheLittleGuy.LOGGER.info("Registering Mod Items for " + ArloTheLittleGuy.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(output -> {
                    output.insertBefore(Items.SADDLE, List.of(
                            new ItemStack(ModItems.BOWLER_HAT)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(output -> {
                    output.insertAfter(ModItems.BOWLER_HAT, List.of(
                            new ItemStack(ModItems.STRAW_HAT),
                            new ItemStack(ModItems.COWBOY_HAT),
                            new ItemStack(ModItems.TRICORN),
                            new ItemStack(ModItems.TOP_HAT),
                            new ItemStack(ModItems.SUN_HAT)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(output -> {
                    output.insertBefore(Items.CARROT_ON_A_STICK, List.of(
                            new ItemStack(ModItems.CROWN),
                            new ItemStack(ModItems.PRISMARINE_PIPIS)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
                .register(output -> {
                    output.insertBefore(Items.REDSTONE, List.of(
                            new ItemStack(ModItems.CACTUS_BATTERY)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(output -> {
                    output.insertBefore(Items.SPYGLASS, List.of(
                            new ItemStack(ModItems.CACTUS_FLASHLIGHT)
                    ), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(output -> {
                    output.accept(new ItemStack(ModItems.CASSETTE_TAPE_1));
                    output.accept(new ItemStack(ModItems.CASSETTE_TAPE_2));
                    output.accept(new ItemStack(ModItems.CASSETTE_TAPE_3));
                    output.accept(new ItemStack(ModItems.CASSETTE_TAPE_4));
                    output.accept(new ItemStack(ModItems.CASSETTE_TAPE_5));
                    output.accept(new ItemStack(ModItems.CASSETTE_TAPE_6));
                    output.accept(new ItemStack(ModItems.CASSETTE_TAPE_7));
                    output.accept(new ItemStack(ModItems.CASSETTE_TAPE_QUESTION));
                });
    }
}