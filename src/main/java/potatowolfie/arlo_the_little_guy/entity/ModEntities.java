package potatowolfie.arlo_the_little_guy.entity;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import potatowolfie.arlo_the_little_guy.ArloTheLittleGuy;
import potatowolfie.arlo_the_little_guy.entity.cactus_horse.CactusHorseEntity;

public class ModEntities {

    public static final EntityType<CactusHorseEntity> CACTUS_HORSE = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "cactus_horse"),
            EntityType.Builder.of(CactusHorseEntity::new, MobCategory.CREATURE)
                    .clientTrackingRange(84).sized(1.3964844F, 1.6F)
                    .eyeHeight(1.52F).passengerAttachments(new float[]{1.44375F}).clientTrackingRange(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ArloTheLittleGuy.MOD_ID, "cactus_horse"))));

    public static void registerModEntities() {
        ArloTheLittleGuy.LOGGER.info("Registering Mod Entities for " + ArloTheLittleGuy.MOD_ID);
    }
}
