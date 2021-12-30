package io.github.linkle.valleycraft.blocks.plants;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import io.github.linkle.valleycraft.blocks.HorizontalBlock;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public class CactusBlock extends HorizontalBlock {
    private static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 15, 15);
    private static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);

    public CactusBlock() {
        super(FabricBlockSettings.of(Material.CACTUS)
                .nonOpaque()
                .breakByHand(true)
                .sounds(BlockSoundGroup.WOOL)
                .strength(0.4f, 0.2f));
        setDefaultState();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    protected Direction getFacing(ItemPlacementContext ctx) {
        return super.getFacing(ctx).getOpposite();
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (!state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }

        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        var iterator = Direction.Type.HORIZONTAL.iterator();

        Direction direction;
        Material material;
        do {
            if (!iterator.hasNext()) {
                BlockState blockState2 = world.getBlockState(pos.down());
                return (blockState2.isOf(Blocks.SAND) || blockState2.isOf(Blocks.RED_SAND)) && !world.getBlockState(pos.up()).getMaterial().isLiquid();
            }

            direction = iterator.next();
            BlockState blockState = world.getBlockState(pos.offset(direction));
            material = blockState.getMaterial();
        } while(!material.isSolid() && !world.getFluidState(pos.offset(direction)).isIn(FluidTags.LAVA));

        return false;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.damage(DamageSource.CACTUS, 1);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}
