package net.capozi.limbo_express.foundation;

import devv.capozi.zip.common.index.Registrar;
import net.capozi.limbo_express.LimboExpress;
import net.capozi.limbo_express.common.item.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

public class ItemInit {
    private static Registrar<Item> itemRegistrar = new Registrar<Item>(LimboExpress.MOD_ID, Registries.ITEM);
    public static void init() {
        itemRegistrar.setRegistries();
    }
    public static final Item SANITY_PILLS = itemRegistrar.add("sanity_pills", new SanityPillsItem(new Item.Settings().maxCount(4)));
    public static final Item INSANITY_PILLS = itemRegistrar.add("insanity_pills", new Item(new Item.Settings().maxCount(1)));
    public static final Item SWAP = itemRegistrar.add("swap", new SwapItem(new Item.Settings().maxCount(1)));
    public static final Item MATCHSTICK = itemRegistrar.add("matchstick", new MatchstickItem(new Item.Settings().maxCount(1)));
    public static final Item CIVILIAN_SIGHT = itemRegistrar.add("civilian_sight", new CivilianSightItem(new Item.Settings().maxCount(1)));
    public static final Item COIN = itemRegistrar.add("coin", new CoinItem(new Item.Settings().maxCount(1)));
    public static final Item ANONYMITY = itemRegistrar.add("anonymity", new AnonymityItem(new Item.Settings().maxCount(1)));
}
