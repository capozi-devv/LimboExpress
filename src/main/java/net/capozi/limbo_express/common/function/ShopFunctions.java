package net.capozi.limbo_express.common.function;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.index.WatheSounds;
import dev.doctor4t.wathe.util.ShopEntry;
import net.capozi.limbo_express.common.ModifiedGameConstants;
import net.capozi.limbo_express.mixin.access.PlayerShopComponentAccessor;
import net.capozi.limbo_express.mixin.access.ShopEntryAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import static dev.doctor4t.wathe.util.ShopEntry.insertStackInFreeSlot;

public class ShopFunctions {
    public static boolean onBuy(@NotNull PlayerEntity player, ShopEntry shop) {
        if (GameWorldComponent.KEY.get(player.getWorld()).isInnocent(player)) {
            ShopEntryAccessor accessor = ((ShopEntryAccessor)shop);
            return insertStackInFreeSlot(player, accessor.getStack().copy());
        } else return false;
    }
    public static void civilianTryBuy(int index, PlayerShopComponent shop) {
        PlayerShopComponentAccessor accessor = ((PlayerShopComponentAccessor)shop);
        if (index < 0 || index >= ModifiedGameConstants.CIVILIAN_SHOP_ENTRIES.size()) return;
        ShopEntry entry = ModifiedGameConstants.CIVILIAN_SHOP_ENTRIES.get(index);
        if (FabricLoader.getInstance().isDevelopmentEnvironment() && shop.balance < entry.price())
            shop.balance = entry.price() * 10;
        if (shop.balance >= entry.price() && !accessor.player().getItemCooldownManager().isCoolingDown(entry.stack().getItem()) && entry.onBuy(accessor.player())) {
            shop.balance -= entry.price();
            if (accessor.player() instanceof ServerPlayerEntity player) {
                player.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY), SoundCategory.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0f, 0.9f + accessor.player().getRandom().nextFloat() * 0.2f, player.getRandom().nextLong()));
            }
        } else {
            accessor.player().sendMessage(Text.literal("Purchase Failed").formatted(Formatting.DARK_RED), true);
            if (accessor.player() instanceof ServerPlayerEntity player) {
                player.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY_FAIL), SoundCategory.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0f, 0.9f + accessor.player().getRandom().nextFloat() * 0.2f, player.getRandom().nextLong()));
            }
        }
        shop.sync();
    }
    public static void modifiedKillerTryBuy(int index, PlayerShopComponent shop) {
        PlayerShopComponentAccessor accessor = ((PlayerShopComponentAccessor)shop);
        if (index >= 0 && index < ModifiedGameConstants.MODIFIED_KILLER_SHOP_ENTRIES.size()) {
            ShopEntry entry = ModifiedGameConstants.MODIFIED_KILLER_SHOP_ENTRIES.get(index);
            if (FabricLoader.getInstance().isDevelopmentEnvironment() && shop.balance < entry.price()) {
                shop.balance = entry.price() * 10;
            }
            if (shop.balance >= entry.price() && !accessor.player().getItemCooldownManager().isCoolingDown(entry.stack().getItem()) && entry.onBuy(accessor.player())) {
                shop.balance -= entry.price();
                PlayerEntity var6 = accessor.player();
                if (var6 instanceof ServerPlayerEntity player) {
                    player.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY), SoundCategory.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 0.9F + accessor.player().getRandom().nextFloat() * 0.2F, player.getRandom().nextLong()));
                }
            } else {
                accessor.player().sendMessage(Text.literal("Purchase Failed").formatted(Formatting.DARK_RED), true);
                PlayerEntity var4 = accessor.player();
                if (var4 instanceof ServerPlayerEntity player) {
                    player.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY_FAIL), SoundCategory.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 0.9F + accessor.player().getRandom().nextFloat() * 0.2F, player.getRandom().nextLong()));
                }
            }
            shop.sync();
        }
    }

}
