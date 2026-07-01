package potatowolfie.arlo_the_little_guy.client.light;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import potatowolfie.arlo_the_little_guy.item.custom.CactusFlashlightItem;

public class FlashlightDynamicLight {
    public static BlockPos flashlightLightPos = null;

    public static void register() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.level == null || client.player == null) {
                flashlightLightPos = null;
                return;
            }

            LocalPlayer player = client.player;
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();

            boolean holdingActiveFlashlight =
                    (mainHand.getItem() instanceof CactusFlashlightItem && CactusFlashlightItem.isOn(mainHand)) ||
                            (offHand.getItem() instanceof CactusFlashlightItem && CactusFlashlightItem.isOn(offHand));

            BlockPos currentPos = player.blockPosition();

            if (holdingActiveFlashlight) {
                if (flashlightLightPos != null && !flashlightLightPos.equals(currentPos)) {
                    BlockPos oldPos = flashlightLightPos;
                    flashlightLightPos = currentPos;
                    client.level.getChunkSource().getLightEngine().checkBlock(oldPos);
                }

                flashlightLightPos = currentPos;
                client.level.getChunkSource().getLightEngine().checkBlock(flashlightLightPos);
            } else if (flashlightLightPos != null) {
                BlockPos oldPos = flashlightLightPos;
                flashlightLightPos = null;
                client.level.getChunkSource().getLightEngine().checkBlock(oldPos);
            }
        });
    }
}