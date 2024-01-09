package me.zircta.optimalaim.listeners;

import me.zircta.optimalaim.Main;
import me.zircta.optimalaim.config.OptimalAimConfig;
import me.zircta.optimalaim.utils.AABB;
import me.zircta.optimalaim.utils.PairedRenderEvent;
import me.zircta.optimalaim.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.weavemc.loader.api.event.RenderLivingEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderLivingListener {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final OptimalAimConfig config = Main.config;
    private EntityPlayer player;

    @SubscribeEvent
    public void onRenderPlayerPost(RenderLivingEvent ev) {
        if (config.enabled) {
            if (ev.getEntity() instanceof EntityPlayer en) {
                handlePlayer(en);
                if (player != null && player == en) {
                    PairedRenderEvent mcWrapped = new PairedRenderEvent(ev, mc.thePlayer);
                    PairedRenderEvent handledPlayer = new PairedRenderEvent(ev, en);
                    render(mcWrapped.getAABB(handledPlayer), config.cubeColor.toJavaColor(), config.outlineColor.toJavaColor());
                }
            }
        }
    }

    private void handlePlayer(EntityPlayer en) {
        if (!en.isUser() && !en.isInvisible()) {
            float f = en.getDistanceToEntity(mc.thePlayer);
            if (player == null) {
                if (en.deathTime <= 0 && f <= config.distance) {
                    player = en;
                }
            } else {
                float c = player.getDistanceToEntity(mc.thePlayer);
                if (player != en) {
                    if (f < c) {
                        player = en;
                    }
                } else if (c > config.distance || player.deathTime > 0) {
                    player = null;
                }
            }
        }
    }

    private void render(AABB aabb, Color cube, Color outline) {
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.disableLighting();
        GlStateManager.enableCull();

        if (cube.getAlpha() > 0.0F) {
            GlStateManager.color(cube.getRed(), cube.getGreen(), cube.getBlue(), cube.getAlpha());
            RenderUtils.drawBox(aabb);
        }

        if (outline.getAlpha() > 0.0F) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
            GL11.glLineWidth(2.0F);
            GlStateManager.color(outline.getRed(), outline.getGreen(), outline.getBlue(), outline.getAlpha());
            RenderGlobal.drawSelectionBoundingBox(aabb);
        }

        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
    }
}
