package com.jingqi.mineralseeds.block;

import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

/**
 * A crop block that grows mineral seeds.
 * Grows through 4 stages (age 0-3) on farmland, like vanilla wheat.
 * Each instance is tied to a specific mineral's seed item via the supplier.
 *
 * Overrides render type to CUTOUT so transparent pixels in the crop
 * texture are properly alpha-blended instead of rendered as black.
 */
public class MineralCropBlock extends CropBlock {

    private final Supplier<ItemConvertible> seedSupplier;

    public MineralCropBlock(Supplier<ItemConvertible> seedSupplier) {
        super(AbstractBlock.Settings.copy(Blocks.WHEAT).nonOpaque());
        this.seedSupplier = seedSupplier;
    }

    @Override
    public int getMaxAge() {
        return 3; // 4 stages: 0, 1, 2, 3
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return seedSupplier.get();
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return state.get(AGE) < getMaxAge() || super.isTransparent(state, world, pos);
    }
}
