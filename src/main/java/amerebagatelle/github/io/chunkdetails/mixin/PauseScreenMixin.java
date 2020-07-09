package amerebagatelle.github.io.chunkdetails.mixin;

import amerebagatelle.github.io.chunkdetails.client.gui.ChunkLoadedListScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GameMenuScreen.class)
public class PauseScreenMixin extends Screen {
    public PauseScreenMixin() {
        super(new LiteralText(""));
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void addScreenButton(CallbackInfo ci) {
        this.addButton(new ButtonWidget(10, 10, 80, 20, "Chunk Details", (onPress) -> {
            MinecraftClient.getInstance().openScreen(new ChunkLoadedListScreen());
        }));
    }
}
