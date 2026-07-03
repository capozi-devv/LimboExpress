package net.capozi.limbo_express.foundation;

import dev.doctor4t.wathe.block.OrnamentBlock;
import devv.capozi.zip.common.index.Registrar;
import net.capozi.limbo_express.LimboExpress;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public interface BlockInit {
    Registrar<Block> blockRegistrar = new Registrar<Block>(LimboExpress.MOD_ID, Registries.BLOCK);
    Registrar<Item> blockItemRegistrar = new Registrar<Item>(LimboExpress.MOD_ID, Registries.ITEM);
    static void init() {
        blockRegistrar.setRegistries(blockRegistrar.entries, blockRegistrar.registry_consumer);
        blockItemRegistrar.setRegistries(blockItemRegistrar.entries, blockItemRegistrar.registry_consumer);
    }
    Block SILVER_ORNAMENT = blockRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "silver_ornament"), new OrnamentBlock(AbstractBlock.Settings.create().nonOpaque().noCollision().strength(0.25f).sounds(BlockSoundGroup.COPPER)));
    Block GREEN_MOQUETTE = blockRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "green_moquette"), new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOL)));
    Item SILVER_ORNAMENT_ITEM = blockItemRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "silver_ornament"), new BlockItem(SILVER_ORNAMENT, new Item.Settings()));
    Item GREEN_MOQUETTE_ITEM = blockItemRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "green_moquette"), new BlockItem(GREEN_MOQUETTE, new Item.Settings()));
}
