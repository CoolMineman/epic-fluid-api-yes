package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {
	public static BlockEntityType<BlackHoleEntity> BLACK_HOLE_ENTITY;
	public static final BlackHole BLACK_HOLE = new BlackHole(Block.Settings.of(Material.STONE));

	public static BlockEntityType<FreeWaterEntity> FREE_WATER_ENTITY;
	public static final FreeWater FREE_WATER = new FreeWater(Block.Settings.of(Material.STONE));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");
		Registry.register(Registry.BLOCK, new Identifier("tutorial", "black_hole"), BLACK_HOLE);
		BLACK_HOLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "tutorial:black_hole", BlockEntityType.Builder.create(BlackHoleEntity::new, BLACK_HOLE).build(null));
		Registry.register(Registry.ITEM, new Identifier("tutorial", "black_hole"), new BlockItem(BLACK_HOLE, new Item.Settings().group(ItemGroup.MISC)));

		Registry.register(Registry.BLOCK, new Identifier("tutorial", "free_water"), FREE_WATER);
		FREE_WATER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "tutorial:free_water", BlockEntityType.Builder.create(FreeWaterEntity::new, FREE_WATER).build(null));
		Registry.register(Registry.ITEM, new Identifier("tutorial", "free_water"), new BlockItem(FREE_WATER, new Item.Settings().group(ItemGroup.MISC)));
	}
}
