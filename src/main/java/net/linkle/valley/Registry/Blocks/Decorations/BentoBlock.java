package net.linkle.valley.Registry.Blocks.Decorations;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.linkle.valley.Registry.Commons.DirectionBlockWithWater;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BentoBlock extends DirectionBlockWithWater {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5, 0, 5, 11, 4, 11);

    public BentoBlock() {
        super(FabricBlockSettings.of(Material.WOOD)
                .breakByHand(true)
                .sounds(BlockSoundGroup.WOOD)
                .strength(0.5f, 2.5f));
        setDefaultState();
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}

