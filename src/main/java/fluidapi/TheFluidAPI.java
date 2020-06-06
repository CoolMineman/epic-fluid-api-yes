package fluidapi;

import stolenfromfablabs.Fraction;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class TheFluidAPI {
    private TheFluidAPI(){}
    private static final Fraction onethird = Fraction.of(1l, 3l);
    private static final Fraction twothird = Fraction.of(1l, 3l);

    /**
     * Request an amount of fluid from a block
     * @param amount How much fluid to try to try to get from a block
     * @param world The world the block you want to request from is in
     * @param blockToRequestFrom The position of the block you want fluid from
     * @param requestingBlockPos The block position you are requesting from
     * @return The amount of fluid the block was able to provide or null
     */
    public static VirtualFluid requestFluid(Fraction amount, World world, BlockPos blockToRequestFrom, BlockPos requestingBlockPos) {
        if (world.getBlockState(blockToRequestFrom).getBlock() instanceof CauldronBlock) {
            Fraction i = amount;
            Fraction returnamount = null;
            while (i.isGreaterThanOrEqualTo(onethird)) {
                int level = world.getBlockState(blockToRequestFrom).get(CauldronBlock.LEVEL);
                if (level >= 1) {
                    setCauldronLevel(level - 1, world, blockToRequestFrom);
                    if (returnamount == null) {
                        returnamount = Fraction.of(1, 3);
                        i = i.subtract(onethird);
                    } else {
                        returnamount = returnamount.add(onethird);
                        i = i.subtract(onethird);
                    }
                } else {
                    break;
                }
            }
            if (returnamount != null)
                return new VirtualFluid(new Identifier("minecraft:water"), returnamount);
        } else if (world.getBlockState(blockToRequestFrom).getBlock() instanceof FluidDrainable) {
            if (amount.isGreaterThanOrEqualTo(Fraction.ONE)) {
                Fluid fluid = ((FluidDrainable) world.getBlockState(blockToRequestFrom).getBlock()).tryDrainFluid(world, blockToRequestFrom, world.getBlockState(blockToRequestFrom));
                if (fluid != Fluids.EMPTY) {
                    return new VirtualFluid(Registry.FLUID.getId(fluid), Fraction.ONE);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    private static void setCauldronLevel(int level, World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        CauldronBlock block = (CauldronBlock)state.getBlock();
        block.setLevel(world, pos, state, level);
    }

    /**
     * Attempts to put fluid into a block
     * @param fluid The liquid you are trying to push
     * @param world The world the block you want to push to is in
     * @param blockToPushTo The position of the block you want to push fluid to
     * @param requestingBlockPos The block position you are requesting from
     * @return The fluid that couldn't be pushed
     */
    public static VirtualFluid pushFluid(VirtualFluid fluid, World world, BlockPos blockToPushTo, BlockPos requestingBlockPos) {
        if (world.getBlockState(blockToPushTo).getBlock() instanceof CauldronBlock) {
            if (fluid.type.toString().equals("minecraft:water")) {
                Fraction i = fluid.amount;
                while (i.isGreaterThanOrEqualTo(onethird)) {
                    int level = world.getBlockState(blockToPushTo).get(CauldronBlock.LEVEL);
                    if (level < 3) {
                        setCauldronLevel(level + 1, world, blockToPushTo);
                        i = i.subtract(onethird);
                    } else {
                        break;
                    }
                }
                return new VirtualFluid(fluid.type, i);
            }
        } else if (world.getBlockState(blockToPushTo).getBlock() instanceof FluidFillable) {
            if (fluid.type.toString().equals("minecraft:water") && fluid.amount.isGreaterThanOrEqualTo(Fraction.ONE)) {
                if (((Waterloggable) world.getBlockState(blockToPushTo).getBlock()).tryFillWithFluid(world, blockToPushTo, world.getBlockState(blockToPushTo), Fluids.WATER.getDefaultState())) {
                    return new VirtualFluid(fluid.type, fluid.amount.subtract(Fraction.ONE));
                } else {
                    return fluid;
                }
            } else {
                return fluid;
            }
        }
        return fluid;
    }

    /**
     * WIP: Returns the maximum fluid that requestFluid could recieve that are in a block
     * @return Array of fluids
     */
    public static VirtualFluid getFluids(World world, BlockPos block) {
        return null;
    }
}