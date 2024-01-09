package me.zircta.optimalaim.utils;

import me.zircta.optimalaim.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.weavemc.loader.api.event.RenderLivingEvent;

public class PairedRenderEvent {
    private final RenderLivingEvent wrappedEvent;
    private final double offset, eyeHeight, halfWidth, widthA, heightA, lerpedX, lerpedY, lerpedZ;

    public PairedRenderEvent(RenderLivingEvent event, EntityPlayer entityPlayer) {
        wrappedEvent = event;
        offset = Main.config.radius;
        eyeHeight = entityPlayer.getEyeHeight();
        halfWidth = (entityPlayer.width / 2.0F + 0.1F);
        widthA = (entityPlayer.width + 0.2F);
        heightA = (entityPlayer.height + 0.2F);
        lerpedX = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * event.getPartialTicks();
        lerpedY = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * event.getPartialTicks();
        lerpedZ = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * event.getPartialTicks();
    }

    public AABB getAABB(PairedRenderEvent e) {
        double x = boxIt(wrappedEvent.getX() - e.lerpedX, lerpedX, e.lerpedX - halfWidth, widthA, offset);
        double y = boxIt(wrappedEvent.getY() - e.lerpedY, lerpedY + eyeHeight, e.lerpedY - 0.10000000149011612D, heightA, offset);
        double z = boxIt(wrappedEvent.getZ() - e.lerpedZ, lerpedZ, e.lerpedZ - halfWidth, widthA, offset);
        return new AABB(x - offset, y - offset, z - offset, x + offset, y + offset, z + offset);
    }

    private double boxIt(double a, double b, double c, double d, double e) {
        if (b <= c + e) {
            return c + a + e;
        } else {
            return b >= c + d - e ? c + d + a - e : b + a;
        }
    }
}
