package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextMixin {

    @Shadow
    private List<Text> splashTexts;

    @Inject(method = "apply*",
            at = @At("TAIL"))
    private void addArloTheLittleGuySplashes(CallbackInfo ci) {
        splashTexts = new ArrayList<>(splashTexts);

        Style yellowStyle = Style.EMPTY.withColor(0xFFFF00);

        splashTexts.add(Text.translatable("splash.arlo-the-little-guy.102_arlo").setStyle(yellowStyle));
        splashTexts.add(Text.translatable("splash.arlo-the-little-guy.us_in_cactus").setStyle(yellowStyle));
        splashTexts.add(Text.translatable("splash.arlo-the-little-guy.we_have_hats").setStyle(yellowStyle));
        splashTexts.add(Text.translatable("splash.arlo-the-little-guy.squared").setStyle(yellowStyle));
        splashTexts.add(Text.translatable("splash.arlo-the-little-guy.you_wouldnt").setStyle(yellowStyle));
        splashTexts.add(Text.translatable("splash.arlo-the-little-guy.low").setStyle(yellowStyle));
    }
}