package potatowolfie.arlo_the_little_guy;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import potatowolfie.arlo_the_little_guy.datagen.ModLootTableGenerator;
import potatowolfie.arlo_the_little_guy.datagen.ModModelProvider;
import potatowolfie.arlo_the_little_guy.datagen.ModRecipeGenerator;
import potatowolfie.arlo_the_little_guy.datagen.ModWorldGenerator;
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
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}