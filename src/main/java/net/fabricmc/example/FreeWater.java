package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class FreeWater extends Block implements BlockEntityProvider {

    public FreeWater(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new FreeWaterEntity();
    }
    
}