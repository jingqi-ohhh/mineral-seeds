package com.jingqi.mineralseeds;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/**
 * Client-side setup: registers all crop blocks for cutout rendering
 * so transparent pixels are properly handled.
 */
public class MineralSeedsModClient implements ClientModInitializer {

    private static final String[] CROPS = {
        "coal", "iron", "gold", "copper", "redstone", "lapis",
        "diamond", "emerald", "quartz", "ancient_debris", "netherite", "glowstone"
    };

    @Override
    public void onInitializeClient() {
        for (String name : CROPS) {
            Block block = Registries.BLOCK.get(new Identifier(MineralSeedsMod.MOD_ID, name + "_crop"));
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }
    }
}
