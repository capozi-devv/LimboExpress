package net.capozi.limbo_express.common.game.mapeffect;

import dev.doctor4t.wathe.cca.TrainWorldComponent;
import dev.doctor4t.wathe.game.mapeffect.HarpyExpressTrainMapEffect;
import dev.doctor4t.wathe.index.WatheItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class LimboMapEffect extends HarpyExpressTrainMapEffect {
    public LimboMapEffect(Identifier identifier) {
        super(identifier);
    }
    @Override
    public void initializeMapEffects(ServerWorld serverWorld, List<ServerPlayerEntity> players) {
        TrainWorldComponent trainWorldComponent = TrainWorldComponent.KEY.get(serverWorld);
        trainWorldComponent.setSnow(false);
        trainWorldComponent.setFog(false);
        trainWorldComponent.setHud(true);
        trainWorldComponent.setSpeed(130);
        trainWorldComponent.setTime(0);
        Collections.shuffle(players);
        int roomNumber = 0;
        for (ServerPlayerEntity serverPlayerEntity : players) {
            ItemStack itemStack = new ItemStack(WatheItems.KEY);
            roomNumber = roomNumber % 7 + 1;
            int finalRoomNumber = roomNumber;
            itemStack.apply(DataComponentTypes.LORE, LoreComponent.DEFAULT, component -> new LoreComponent(Text.literal(switch (finalRoomNumber) {
                case 1 -> "Grand Suite";
                case 2 -> "Master Suite";
                case 3 -> "Panoramic Suite";
                case 4 -> "Silk Suite";
                case 5 -> "Aqua Suite";
                default -> "Room " + (finalRoomNumber - 5);
            }).getWithStyle(Style.EMPTY.withItalic(false).withColor(0xFF8C00))));
            serverPlayerEntity.giveItemStack(itemStack);

            // give letter
            ItemStack letter = new ItemStack(WatheItems.LETTER);

            letter.set(DataComponentTypes.ITEM_NAME, Text.translatable(letter.getTranslationKey()));
            int letterColor = 0xC5AE8B;
            String tipString = "tip.letter.";
            letter.apply(DataComponentTypes.LORE, LoreComponent.DEFAULT, component -> {
                        List<Text> text = new ArrayList<>();
                        UnaryOperator<Style> stylizer = style -> style.withItalic(false).withColor(letterColor);

                        Text displayName = serverPlayerEntity.getDisplayName();
                        String string = displayName != null ? displayName.getString() : serverPlayerEntity.getName().getString();
                        if (string.charAt(string.length() - 1) == '\uE780') { // remove ratty supporter icon
                            string = string.substring(0, string.length() - 1);
                        }

                        text.add(Text.translatable(tipString + "name", string).styled(style -> style.withItalic(false).withColor(0xFFFFFF)));
                        text.add(Text.translatable(tipString + "room").styled(stylizer));
                        text.add(Text.translatable(tipString + "tooltip1",
                                Text.translatable(tipString + "room." + switch (finalRoomNumber) {
                                    case 1 -> "grand_suite";
                                    case 2 -> "master_suite";
                                    case 3 -> "panoramic_suite";
                                    case 4 -> "silk_suite";
                                    case 5 -> "aqua_suite";
                                    default -> "twin_cabin";
                                }).getString()
                        ).styled(stylizer));
                        text.add(Text.translatable(tipString + "tooltip2").styled(stylizer));
                        return new LoreComponent(text);
                    }
            );
            serverPlayerEntity.giveItemStack(letter);
        }
    }
}
