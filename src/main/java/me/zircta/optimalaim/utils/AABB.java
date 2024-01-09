package me.zircta.optimalaim.utils;

import net.minecraft.util.AxisAlignedBB;

public class AABB extends AxisAlignedBB {
    public AABB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        super(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
