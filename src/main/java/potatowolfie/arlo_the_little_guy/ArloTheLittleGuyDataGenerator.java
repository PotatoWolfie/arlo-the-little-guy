package potatowolfie.arlo_the_little_guy;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import potatowolfie.arlo_the_little_guy.datagen.*;
import potatowolfie.arlo_the_little_guy.world.dimension.ModDimensions;
import potatowolfie.arlo_the_little_guy.world.feature.ModConfiguredFeatures;
import potatowolfie.arlo_the_little_guy.world.feature.ModPlacedFeatures;

public class ArloTheLittleGuyDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModLootTableGenerator::new);
		pack.addProvider(ModWorldGenerator::new);
		pack.addProvider(ModRecipeGenerator::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModEntityTagProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);

		registryBuilder.add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem);
		registryBuilder.add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapType);
	}
}