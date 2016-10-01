package notjoe.pointersmod.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class PlayerWrapper extends EntityPlayer {
    private EntityPlayer target;
    private BlockPos pos;

    public PlayerWrapper(EntityPlayer target, BlockPos pos) {
        super(target.getEntityWorld(), target.getGameProfile());
        this.target = target;
        this.pos = pos;
    }

    @Override public BlockPos getPosition() {
        return this.pos;
    }

    @Override public double getDistance(double x, double y, double z) {
        return 0;
    }

    @Override public double getDistanceSq(BlockPos pos) {
        return 0;
    }

    @Override public double getDistanceSq(double x, double y, double z) {
        return 0;
    }

    @Override public double getDistanceSqToCenter(BlockPos pos) {
        return 0;
    }

    @Override public double getDistanceSqToEntity(Entity entityIn) {
        return 0;
    }

    @Override public float getDistanceToEntity(Entity entityIn) {
        return 0;
    }

    @Override public void openGui(Object mod, int modGuiId, World world, int x, int y, int z) {
        System.out.println("Wrapped player has opened GUI " + modGuiId);
        this.inventory = target.inventory;
        FMLNetworkHandler.openGui(this, mod, modGuiId, world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override public boolean isSpectator() {
        return target.isSpectator();
    }

    @Override public boolean isCreative() {
        return target.isCreative();
    }
}
