package net.capozi.limbo_express.foundation;

import devv.capozi.zip.common.api.index.Registrar;
import net.capozi.limbo_express.LimboExpress;
import net.capozi.limbo_express.common.item.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface ItemInit {
    Registrar<Item> itemRegistrar = new Registrar<Item>((identifier, block) -> Registry.register(Registries.ITEM, identifier, block));
    static void init() {
        itemRegistrar.setRegistries(itemRegistrar.entries, itemRegistrar.registry_consumer);
    }
    Item SANITY_PILLS = itemRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "sanity_pills"), new SanityPillsItem(new Item.Settings().maxCount(4)));
    Item INSANITY_PILLS = itemRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "insanity_pills"), new Item(new Item.Settings().maxCount(1)));
    Item SWAP = itemRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "swap"), new SwapItem(new Item.Settings().maxCount(1)));
    Item MATCHSTICK = itemRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "matchstick"), new MatchstickItem(new Item.Settings().maxCount(1)));
    Item CIVILIAN_SIGHT = itemRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "civilian_sight"), new CivilianSightItem(new Item.Settings().maxCount(1)));
    Item COIN = itemRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "coin"), new CoinItem(new Item.Settings().maxCount(5)));
    Item ANONYMITY = itemRegistrar.add(Identifier.of(LimboExpress.MOD_ID, "anonymity"), new AnonymityItem(new Item.Settings()));
}
