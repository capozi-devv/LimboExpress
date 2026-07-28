package net.capozi.limbo_express.common;

import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.index.WatheItems;
import dev.doctor4t.wathe.util.ShopEntry;
import net.capozi.limbo_express.LimboExpressConfig;
import net.capozi.limbo_express.common.cca.PlayerSwapComponent;
import net.capozi.limbo_express.common.game.function.ModifiedGameFunctions;
import net.capozi.limbo_express.common.game.function.ShopFunctions;
import net.capozi.limbo_express.foundation.ItemInit;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.datafixer.fix.ItemCustomNameToComponentFix;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface ModifiedGameConstants {
    ItemStack stack = new ItemStack(ItemInit.ANONYMITY);
    static ItemStack potionStack(ItemStack original) {
        ItemStack copy = original.copy();
        PotionContentsComponent contents = copy.get(DataComponentTypes.POTION_CONTENTS);
        if (contents == null) {
            copy.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Potions.INVISIBILITY));
            return copy;
        }
        List<StatusEffectInstance> effects = new ArrayList<>(contents.customEffects());
        effects.add(new StatusEffectInstance(StatusEffects.INVISIBILITY));
        copy.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(contents.potion(), contents.customColor(), effects));
        return copy;
    }
    List<ShopEntry> MODIFIED_KILLER_SHOP_ENTRIES = Util.make(new ArrayList<>(), entries -> {
        entries.add(new ShopEntry(WatheItems.KNIFE.getDefaultStack(), LimboExpressConfig.knifePrice, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(new ItemStack(WatheItems.DERRINGER, 1), LimboExpressConfig.killerDerringerPrice, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(ItemInit.SWAP.getDefaultStack(), LimboExpressConfig.swapPrice, ShopEntry.Type.WEAPON) {
            @Override
            public boolean onBuy(@NotNull PlayerEntity player) {
                if (PlayerSwapComponent.KEY.get(player).getCooldown() != 0) return false;
                return ModifiedGameFunctions.triggerSwap(player.getWorld(), player);
            }
        });
        entries.add(new ShopEntry(WatheItems.GRENADE.getDefaultStack(), LimboExpressConfig.grenadePrice, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(WatheItems.SCORPION.getDefaultStack(), LimboExpressConfig.scorpionPrice, ShopEntry.Type.POISON));
        entries.add(new ShopEntry(WatheItems.POISON_VIAL.getDefaultStack(), LimboExpressConfig.poisonPrice, ShopEntry.Type.POISON));
        entries.add(new ShopEntry(new ItemStack(WatheItems.NOTE, 4), LimboExpressConfig.notePrice, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(potionStack(stack), LimboExpressConfig.anonymityPrice, ShopEntry.Type.TOOL) {
            @Override
            public boolean onBuy(@NotNull PlayerEntity player) {
                return ModifiedGameFunctions.anonymize(player);
            }
        });
        entries.add(new ShopEntry(WatheItems.LOCKPICK.getDefaultStack(), LimboExpressConfig.lockpickPrice, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.CROWBAR.getDefaultStack(), LimboExpressConfig.crowbarPrice, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.BLACKOUT.getDefaultStack(), LimboExpressConfig.blackoutPrice, ShopEntry.Type.TOOL) {
            @Override
            public boolean onBuy(@NotNull PlayerEntity player) {
                return PlayerShopComponent.useBlackout(player);
            }
        });
    });
    List<ShopEntry> CIVILIAN_SHOP_ENTRIES = Util.make(new ArrayList<>(), entries -> {
        entries.add(new ShopEntry(new ItemStack(WatheItems.DERRINGER, 1), LimboExpressConfig.derringerPrice, ShopEntry.Type.WEAPON) {
            @Override
            public boolean onBuy(@NotNull PlayerEntity player) {
                return ShopFunctions.onBuy(player, this);
            }
        });
        entries.add(new ShopEntry(new ItemStack(ItemInit.SANITY_PILLS, 1), LimboExpressConfig.pillsPrice, ShopEntry.Type.POISON) {
            @Override
            public boolean onBuy(@NotNull PlayerEntity player) {
                return ShopFunctions.onBuy(player, this);
            }
        });
        entries.add(new ShopEntry(new ItemStack(WatheItems.NOTE, 4), LimboExpressConfig.notePrice, ShopEntry.Type.TOOL) {
            @Override
            public boolean onBuy(@NotNull PlayerEntity player) {
                return ShopFunctions.onBuy(player, this);
            }
        });
        entries.add(new ShopEntry(new ItemStack(ItemInit.CIVILIAN_SIGHT), LimboExpressConfig.clairvoyancePrice, ShopEntry.Type.TOOL) {
            @Override
            public boolean onBuy(@NotNull PlayerEntity player) {
                return ModifiedGameFunctions.activateCivilianSight(player);
            }
        });
        entries.add(new ShopEntry(potionStack(stack), LimboExpressConfig.anonymityPrice, ShopEntry.Type.TOOL) {
            @Override
            public boolean onBuy(@NotNull PlayerEntity player) {
                return ModifiedGameFunctions.anonymize(player);
            }
        });
        entries.add(new ShopEntry(WatheItems.CROWBAR.getDefaultStack(), LimboExpressConfig.crowbarPrice, ShopEntry.Type.TOOL) {
            @Override
            public boolean onBuy(@NotNull PlayerEntity player) {
                return ShopFunctions.onBuy(player, this);
            }
        });
    });
    float MOOD_GAIN = 0.25f;
    float OVERDOSE_MOOD_DRAIN = 2.5f / GameConstants.getInTicks(4, 0);
    int swapCooldown = 6000;
    int civilianSightCooldown = 3600;
}
