package net.capozi.limbo_express;

import net.capozi.limbo_express.foundation.BlockInit;
import net.capozi.limbo_express.foundation.ItemInit;
import net.fabricmc.api.ModInitializer;

public class LimboExpress implements ModInitializer {
    public static final String MOD_ID = "limbo_express";
    @Override
    public void onInitialize() {
        ItemInit.init();
        BlockInit.init();
    }
}
