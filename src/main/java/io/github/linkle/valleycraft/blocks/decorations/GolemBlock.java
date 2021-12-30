package io.github.linkle.valleycraft.blocks.decorations;

import java.util.List;
import java.util.Random;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import io.github.linkle.valleycraft.blocks.HorizontalWithWaterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GolemBlock extends HorizontalWithWaterBlock {
    private static final VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 12, 12);

    public GolemBlock() {
        super(FabricBlockSettings.of(Material.METAL)
                .breakByHand(true)
                .sounds(BlockSoundGroup.CHAIN).luminance(4)
                .strength(1.5f, 2.5f));
        setDefaultState();
    }
    
    @Override
    protected Direction getFacing(ItemPlacementContext ctx) {
        return super.getFacing(ctx).getOpposite();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_IRON_GOLEM_REPAIR, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
        }
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add( new TranslatableText("block.valley.golem_idling.tooltip") );
        tooltip.add( new TranslatableText("block.valley.golem_idling.tooltip_2") );
    }
}

