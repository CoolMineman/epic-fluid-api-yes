package net.fabricmc.example;

import fluidapi.TheFluidAPI;
import fluidapi.VirtualFluid;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import stolenfromfablabs.Fraction;

public class FreeWaterEntity extends BlockEntity implements Tickable {
    public FreeWaterEntity() {
        super(ExampleMod.FREE_WATER_ENTITY);
    }

    private int tickCount = 0;

    @Override
    public void tick() {
        if (tickCount == 100) {
            VirtualFluid epic = TheFluidAPI.pushFluid(new VirtualFluid(new Identifier("minecraft:water"), Fraction.of(2, 3)), world, pos.up(), pos);
            if (epic != null)
                System.out.println("Couldn't Push Flud: " + epic.type.toString() + " " + epic.amount.toString());
            tickCount = 0;
        } else {
            tickCount++;
        }
    }
}