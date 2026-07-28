package net.capozi.limbo_express;

import eu.midnightdust.lib.config.MidnightConfig;

public class LimboExpressConfig extends MidnightConfig {
    @Entry @Client
    public static boolean enableBackgroundMusic = true;
    @Entry
    public static boolean enableRevolverSuicide = false;
    @Entry
    public static boolean enhancedGrenadePhysics = true;
    @Entry(min = 0, max = 1000)
    public static int knifePrice = 100;
    @Entry(min = 0, max = 1000) 
    public static int swapPrice = 300;
    @Entry(min = 0, max = 1000) 
    public static int grenadePrice = 350;
    @Entry(min = 0, max = 1000) 
    public static int scorpionPrice = 50;
    @Entry(min = 0, max = 1000) 
    public static int poisonPrice = 75;
    @Entry(min = 0, max = 1000) 
    public static int notePrice = 25;
    @Entry(min = 0, max = 1000) 
    public static int anonymityPrice = 200;
    @Entry(min = 0, max = 1000) 
    public static int lockpickPrice = 50;
    @Entry(min = 0, max = 1000) 
    public static int blackoutPrice = 250;
    @Entry(min = 0, max = 1000) 
    public static int derringerPrice = 450;
    @Entry(min = 0, max = 1000)
    public static int killerDerringerPrice = 150;
    @Entry(min = 0, max = 1000) 
    public static int pillsPrice = 150;
    @Entry(min = 0, max = 1000) 
    public static int clairvoyancePrice = 250;
    @Entry(min = 0, max = 1000) 
    public static  int crowbarPrice = 150;
    public static int encode() {
        StringBuilder builder = new StringBuilder();
        String encoding = builder.toString() +
                enableRevolverSuicide +
                enhancedGrenadePhysics +
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
                killerDerringerPrice +
                pillsPrice +
                clairvoyancePrice +
                crowbarPrice;
        return encoding.hashCode();
    }
}
