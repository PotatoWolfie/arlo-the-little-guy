package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void cancelBlockPlacement(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (player != null && !player.isCreative() && !player.isSpectator()) {
            String dimensionId = level.dimension().identifier().toString();

            if (dimensionId.equals("arlo-the-little-guy:arlrooms")) {
                cir.setReturnValue(InteractionResult.FAIL);
            }
        }
    }
}