package net.fabricmc.example;

import fluidapi.TheFluidAPI;
import fluidapi.VirtualFluid;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;
import stolenfromfablabs.Fraction;

public class BlackHoleEntity extends BlockEntity implements Tickable {
    public BlackHoleEntity() {
        super(ExampleMod.BLACK_HOLE_ENTITY);
    }

    private int tickCount = 0;

    @Override
    public void tick() {
        if (tickCount == 100) {
            VirtualFluid epic = TheFluidAPI.requestFluid(Fraction.ONE, world, pos.up(), pos);
            if (epic != null)
                System.out.println("Drained Flud: " + epic.type.toString() + " " + epic.amount.toString());
            tickCount = 0;
        } else {
            tickCount++;
        }
    }
}