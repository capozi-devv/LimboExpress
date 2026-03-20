package net.capozi.limbo_express.common;

import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.index.WatheItems;
import dev.doctor4t.wathe.util.ShopEntry;
import net.capozi.limbo_express.common.cca.PlayerSwapComponent;
import net.capozi.limbo_express.common.function.ModifiedGameFunctions;
import net.capozi.limbo_express.foundation.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface ModifiedGameConstants {
    List<ShopEntry> MODIFIED_KILLER_SHOP_ENTRIES = Util.make(new ArrayList<>(), entries -> {
        entries.add(new ShopEntry(WatheItems.KNIFE.getDefaultStack(), 100, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(ItemInit.SWAP.getDefaultStack(), 350, ShopEntry.Type.WEAPON) {
            @Override
            public boolean onBuy(PlayerEntity player) {
                if (PlayerSwapComponent.KEY.get(player).getCooldown() != 0) return false;
                if (player != null) {
                    player.getItemCooldownManager().set(ItemInit.SWAP, ModifiedGameConstants.swapCooldown);
                    return ModifiedGameFunctions.triggerSwap(player.getServer().getOverworld(), player);
                }
                return false;
            }
        });
        entries.add(new ShopEntry(WatheItems.GRENADE.getDefaultStack(), 200, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(ItemInit.INSANITY_PILLS.getDefaultStack(), 25, ShopEntry.Type.POISON));
        entries.add(new ShopEntry(WatheItems.SCORPION.getDefaultStack(), 75, ShopEntry.Type.POISON));
        entries.add(new ShopEntry(WatheItems.LOCKPICK.getDefaultStack(), 50, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.BLACKOUT.getDefaultStack(), 300, ShopEntry.Type.TOOL) {
            @Override
            public boolean onBuy(@NotNull PlayerEntity player) {
                return PlayerShopComponent.useBlackout(player);
            }
        });
        entries.add(new ShopEntry(WatheItems.BODY_BAG.getDefaultStack(), 150, ShopEntry.Type.TOOL));
    });
    List<ShopEntry> CIVILIAN_SHOP_ENTRIES = Util.make(new ArrayList<>(), entries -> {
        entries.add(new ShopEntry(new ItemStack(WatheItems.NOTE, 4), 25, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(new ItemStack(ItemInit.SANITY_PILLS, 1), 150, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(new ItemStack(WatheItems.DERRINGER, 1), 500, ShopEntry.Type.WEAPON));
    });
    float MOOD_GAIN = 0.25f;
    float OVERDOSE_MOOD_DRAIN = 0.35f;
    int swapCooldown = 6000;
}
