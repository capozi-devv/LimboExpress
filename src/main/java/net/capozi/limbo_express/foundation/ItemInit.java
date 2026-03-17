package net.capozi.limbo_express.foundation;

import net.capozi.limbo_express.LimboExpress;
import net.capozi.limbo_express.common.item.SanityPillsItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemInit {
    public static void init() {}
    public static Item item(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(LimboExpress.MOD_ID, name), item);
    }
    public static final Item SANITY_PILLS = item("sanity_pills", new SanityPillsItem(new Item.Settings().maxCount(1)));
    public static final Item INSANITY_PILLS = item("insanity_pills", new SanityPillsItem(new Item.Settings().maxCount(1)));
}
