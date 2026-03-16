package net.capozi.limbo_express.mixin.client;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.gui.screen.ingame.LimitedHandledScreen;
import dev.doctor4t.wathe.client.gui.screen.ingame.LimitedInventoryScreen;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.util.ShopEntry;
import net.capozi.limbo_express.common.ModifiedGameConstants;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static dev.doctor4t.wathe.client.gui.screen.ingame.LimitedInventoryScreen.BACKGROUND_TEXTURE;
import static dev.doctor4t.wathe.client.gui.screen.ingame.LimitedInventoryScreen.ID;

@Mixin(LimitedInventoryScreen.class)
public class LimitedInventoryScreenMixin extends LimitedHandledScreen<PlayerScreenHandler> {
    private LimitedInventoryScreenMixin(PlayerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    protected void init(CallbackInfo ci) {
        super.init();
        PlayerEntity objectPlayer = ((LimitedInventoryScreen)(Object)this).player;
        if (GameWorldComponent.KEY.get(objectPlayer.getWorld()).isInnocent(objectPlayer) || GameWorldComponent.KEY.get(objectPlayer.getWorld()).isInnocent(objectPlayer)) {
            List<ShopEntry> entries = ModifiedGameConstants.CIVILIAN_SHOP_ENTRIES;
            int apart = 38;
            int x = this.width / 2 - entries.size() * apart / 2 + 9;
            int y = this.y - 46;
            for (int i = 0; i < entries.size(); i++) {
                this.addDrawableChild(new LimitedInventoryScreen.StoreItemWidget(((LimitedInventoryScreen)(Object)this), x + apart * i, y, entries.get(i), i));
            }
        } else if (GameWorldComponent.KEY.get(objectPlayer.getWorld()).canUseKillerFeatures(objectPlayer)) {
            List<ShopEntry> entries = ModifiedGameConstants.MODIFIED_KILLER_SHOP_ENTRIES;
            int apart = 38;
            int x = this.width / 2 - entries.size() * apart / 2 + 9;
            int y = this.y - 46;
            for (int i = 0; i < entries.size(); i++) {
                this.addDrawableChild(new LimitedInventoryScreen.StoreItemWidget(((LimitedInventoryScreen)(Object)this), x + apart * i, y, entries.get(i), i));
            }
        }
        ci.cancel();
    }
    @Overwrite
    protected void drawBackground(DrawContext context, float v, int i, int i1) {
        context.drawTexture(BACKGROUND_TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        context.getMatrices().push();
        context.getMatrices().translate(context.getScaledWindowWidth() / 2f, context.getScaledWindowHeight(), 0);
        float scale = 0.28f;
        context.getMatrices().scale(scale, scale, 1f);
        int height = 254;
        int width = 497;
        context.getMatrices().translate(0, -230, 0);
        int xOffset = 0;
        int yOffset = 0;
        context.drawTexturedQuad(ID, (int) (xOffset - width / 2f), (int) (xOffset + width / 2f), (int) (yOffset - height / 2f), (int) (yOffset + height / 2f), 0, 0, 1f, 0, 1f, 1f, 1f, 1f, 1f);
        context.getMatrices().pop();
    }
}
