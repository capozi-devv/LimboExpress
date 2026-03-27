package net.capozi.limbo_express.foundation;

import dev.doctor4t.wathe.block.OrnamentBlock;
import dev.doctor4t.wathe.index.WatheBlocks;
import net.capozi.limbo_express.LimboExpress;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BlockInit {
    public static void init() {}
    private static Item blockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(LimboExpress.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }
    private static Block block(String name, Block block, boolean registerBlockItem) {
        if (registerBlockItem) {
            blockItem(name, block);
        }
        return Registry.register(Registries.BLOCK, Identifier.of(LimboExpress.MOD_ID, name), block);
    }
    public static final Block SILVER_ORNAMENT = block("silver_ornament", new OrnamentBlock(AbstractBlock.Settings.create().nonOpaque().noCollision().strength(0.25f).sounds(BlockSoundGroup.COPPER)), true);
    public static final Block GREEN_MOQUETTE = block("green_moquette", new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOL)), true);
}
