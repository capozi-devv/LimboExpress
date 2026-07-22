package net.capozi.limbo_express;

import eu.midnightdust.lib.config.MidnightConfig;

public class LimboExpressConfig extends MidnightConfig {
    @Entry @Client
    public static boolean enableBackgroundMusic = true;
    @Entry(min = 0, max = 1000) @Server
    public static final int knifePrice = 100;
    @Entry(min = 0, max = 1000) @Server
    public static final int swapPrice = 300;
    @Entry(min = 0, max = 1000) @Server
    public static final int grenadePrice = 200;
    @Entry(min = 0, max = 1000) @Server
    public static final int scorpionPrice = 50;
    @Entry(min = 0, max = 1000) @Server
    public static final int poisonPrice = 75;
    @Entry(min = 0, max = 1000) @Server
    public static final int notePrice = 25;
    @Entry(min = 0, max = 1000) @Server
    public static final int anonymityPrice = 200;
    @Entry(min = 0, max = 1000) @Server
    public static final int lockpickPrice = 50;
    @Entry(min = 0, max = 1000) @Server
    public static final int blackoutPrice = 250;
    @Entry(min = 0, max = 1000) @Server
    public static final int derringerPrice = 350;
    @Entry(min = 0, max = 1000) @Server
    public static final int pillsPrice = 150;
    @Entry(min = 0, max = 1000) @Server
    public static final int clairvoyancePrice = 250;
    @Entry(min = 0, max = 1000) @Server
    public static final int crowbarPrice = 175;
    public static int encode() {
        StringBuilder builder = new StringBuilder();
        String encoding = builder.toString() +
                knifePrice +
                swapPrice +
                grenadePrice +
                scorpionPrice +
                poisonPrice +
                notePrice +
                anonymityPrice +
                lockpickPrice +
                blackoutPrice +
                derringerPrice +
                pillsPrice +
                clairvoyancePrice +
                crowbarPrice;
        return encoding.hashCode();
    }
}
