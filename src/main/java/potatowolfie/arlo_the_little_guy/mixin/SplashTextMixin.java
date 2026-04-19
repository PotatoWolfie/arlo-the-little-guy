package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.resources.SplashManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(SplashManager.class)
public class SplashTextMixin {

    @Shadow
    private List<Component> splashes;

    @Inject(method = "apply*",
            at = @At("TAIL"))
    private void addArloTheLittleGuySplashes(CallbackInfo ci) {
        splashes = new ArrayList<>(splashes);

        Style yellowStyle = Style.EMPTY.withColor(0xFFFF00);

        splashes.add(Component.translatable("splash.arlo-the-little-guy.102_arlo").setStyle(yellowStyle));
        splashes.add(Component.translatable("splash.arlo-the-little-guy.us_in_cactus").setStyle(yellowStyle));
        splashes.add(Component.translatable("splash.arlo-the-little-guy.we_have_hats").setStyle(yellowStyle));
        splashes.add(Component.translatable("splash.arlo-the-little-guy.squared").setStyle(yellowStyle));
        splashes.add(Component.translatable("splash.arlo-the-little-guy.you_wouldnt").setStyle(yellowStyle));
        splashes.add(Component.translatable("splash.arlo-the-little-guy.low").setStyle(yellowStyle));
    }
}