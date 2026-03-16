package net.capozi.limbo_express.foundation;

import dev.doctor4t.wathe.index.WatheBlocks;
import net.capozi.limbo_express.LimboExpress;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockInit {
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(LimboExpress.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }
    private static Block registerBlock(String name, Block block, boolean registerBlockItem) {
        if (registerBlockItem) {
            registerBlockItem(name, block);
        }
        return Registry.register(Registries.BLOCK, Identifier.of(LimboExpress.MOD_ID, name), block);
    }
}
