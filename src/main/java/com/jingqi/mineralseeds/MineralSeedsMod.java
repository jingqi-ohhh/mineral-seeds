package com.jingqi.mineralseeds;

import com.jingqi.mineralseeds.block.MineralCropBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Mineral Seeds Mod — plant mineral seeds on farmland and harvest ores!
 *
 * 12 mineral crops: Coal, Iron, Gold, Copper, Redstone, Lapis Lazuli,
 * Diamond, Emerald, Nether Quartz, Ancient Debris, Netherite, Glowstone.
 */
public class MineralSeedsMod implements ModInitializer {

    public static final String MOD_ID = "mineralseeds";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * Registers a crop block and its seed item together, resolving the
     * circular dependency (crop needs seed reference, seed needs crop reference)
     * via a mutable holder.
     *
     * @param name  base name, e.g. "coal" → coal_crop + coal_seed
     * @return the registered seed item (for creative tab / further use)
     */
    private static BlockItem registerCrop(String name) {
        // Mutable holder — resolves the circular CropBlock ↔ BlockItem reference
        var holder = new Object() { BlockItem seed = null; };

        MineralCropBlock crop = new MineralCropBlock(() -> holder.seed);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name + "_crop"), crop);

        BlockItem seed = new BlockItem(crop, new Item.Settings());
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, name + "_seed"), seed);

        holder.seed = seed;
        return seed;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("🌱 Mineral Seeds initializing...");

        // Register all 12 crops + seeds
        BlockItem coalSeed          = registerCrop("coal");
        BlockItem ironSeed          = registerCrop("iron");
        BlockItem goldSeed          = registerCrop("gold");
        BlockItem copperSeed        = registerCrop("copper");
        BlockItem redstoneSeed      = registerCrop("redstone");
        BlockItem lapisSeed         = registerCrop("lapis");
        BlockItem diamondSeed       = registerCrop("diamond");
        BlockItem emeraldSeed       = registerCrop("emerald");
        BlockItem quartzSeed        = registerCrop("quartz");
        BlockItem ancientDebrisSeed = registerCrop("ancient_debris");
        BlockItem netheriteSeed     = registerCrop("netherite");
        BlockItem glowstoneSeed     = registerCrop("glowstone");

        // Collect seeds for creative tab
        List<Item> allSeeds = List.of(
                coalSeed, ironSeed, goldSeed, copperSeed,
                redstoneSeed, lapisSeed, diamondSeed, emeraldSeed,
                quartzSeed, ancientDebrisSeed, netheriteSeed, glowstoneSeed
        );

        // Add all seeds to the NATURAL creative tab (alongside vanilla seeds)
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            for (Item seed : allSeeds) {
                entries.add(seed);
            }
        });

        LOGGER.info("🌱 Mineral Seeds ready! 12 crops registered.");
    }
}
