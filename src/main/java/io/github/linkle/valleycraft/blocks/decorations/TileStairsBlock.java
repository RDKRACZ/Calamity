package io.github.linkle.valleycraft.blocks.decorations;

import io.github.linkle.valleycraft.init.Furniture;
import net.minecraft.block.Material;
import net.minecraft.block.StairsBlock;
import net.minecraft.sound.BlockSoundGroup;

public class TileStairsBlock extends StairsBlock {
    public TileStairsBlock() {
        super(Furniture.SMOOTH_STONE_TILE.getDefaultState(), Settings.of(Material.STONE)
                .requiresTool()
                .sounds(BlockSoundGroup.STONE)
                .strength(1, 2));
    }
}