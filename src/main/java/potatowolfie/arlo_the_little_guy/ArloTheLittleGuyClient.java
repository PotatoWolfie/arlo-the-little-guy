package potatowolfie.arlo_the_little_guy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import potatowolfie.arlo_the_little_guy.block.ModBlocks;
import potatowolfie.arlo_the_little_guy.block.entity.ArloBlockEntityRenderer;
import potatowolfie.arlo_the_little_guy.block.entity.ModBlockEntities;
import potatowolfie.arlo_the_little_guy.entity.bowler_hat.BowlerHatArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.bowler_hat.BowlerHatModel;
import potatowolfie.arlo_the_little_guy.entity.client.ModEntityModelLayers;
import potatowolfie.arlo_the_little_guy.entity.cowboy_hat.CowboyHatArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.cowboy_hat.CowboyHatModel;
import potatowolfie.arlo_the_little_guy.entity.crown.CrownArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.crown.CrownModel;
import potatowolfie.arlo_the_little_guy.entity.crown.PigCrownModel;
import potatowolfie.arlo_the_little_guy.entity.straw_hat.StrawHatArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.straw_hat.StrawHatModel;
import potatowolfie.arlo_the_little_guy.entity.sun_hat.SunHatArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.sun_hat.SunHatModel;
import potatowolfie.arlo_the_little_guy.entity.top_hat.SnowGolemTopHatModel;
import potatowolfie.arlo_the_little_guy.entity.top_hat.TopHatArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.top_hat.TopHatModel;
import potatowolfie.arlo_the_little_guy.entity.tricorn.TricornHatArmorRenderer;
import potatowolfie.arlo_the_little_guy.entity.tricorn.TricornHatModel;
import potatowolfie.arlo_the_little_guy.item.ModItems;

public class ArloTheLittleGuyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModEntityModelLayers.registerModelLayers();

        BlockRenderLayerMap.putBlock(ModBlocks.MINI_CACTUS, BlockRenderLayer.CUTOUT);

        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.BOWLER_HAT,
                BowlerHatModel::getTexturedModelData
        );
        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.TRICORN,
                TricornHatModel::getTexturedModelData
        );
        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.STRAW_HAT,
                StrawHatModel::getTexturedModelData
        );
        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.COWBOY_HAT,
                CowboyHatModel::getTexturedModelData
        );
        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.TOP_HAT,
                TopHatModel::getTexturedModelData
        );
        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.SUN_HAT,
                SunHatModel::getTexturedModelData
        );
        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.CROWN,
                CrownModel::getTexturedModelData
        );
        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.PIG_CROWN,
                PigCrownModel::getTexturedModelData
        );
        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.SNOW_GOLEM_TOP_HAT,
                SnowGolemTopHatModel::getTexturedModelData
        );

        BlockEntityRendererFactories.register(
                ModBlockEntities.ARLO_BLOCK_ENTITY,
                ArloBlockEntityRenderer::new
        );

        ArmorRenderer.register(new BowlerHatArmorRenderer(), ModItems.BOWLER_HAT);
        ArmorRenderer.register(new TricornHatArmorRenderer(), ModItems.TRICORN);
        ArmorRenderer.register(new StrawHatArmorRenderer(), ModItems.STRAW_HAT);
        ArmorRenderer.register(new CowboyHatArmorRenderer(), ModItems.COWBOY_HAT);
        ArmorRenderer.register(new TopHatArmorRenderer(), ModItems.TOP_HAT);
        ArmorRenderer.register(new SunHatArmorRenderer(), ModItems.SUN_HAT);
        ArmorRenderer.register(new CrownArmorRenderer(), ModItems.CROWN);
    }
}