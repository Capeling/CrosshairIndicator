package com.capeling.crosshairindicator.client.mixin;

import com.capeling.crosshairindicator.Crosshairindicator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Shadow @Final private MinecraftClient client;
    @Unique private static final Identifier INDICATOR_TEXTURE = Identifier.of(Crosshairindicator.MOD_ID, "hud/indicator");

    @Inject(method = "renderCrosshair", at = @At("TAIL"))
    private void renderIndicator(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        boolean bl = false;
        if (this.client.targetedEntity != null && this.client.targetedEntity instanceof LivingEntity) {
            bl = this.client.targetedEntity.isAlive();
        }
        if (bl) context.drawGuiTexture(RenderLayer::getCrosshair, INDICATOR_TEXTURE, (context.getScaledWindowWidth() - 15) / 2, (context.getScaledWindowHeight() - 15) / 2, 15, 15);
    }
}