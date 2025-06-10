package ca.jackfountain.emil_fishing.events;

import ca.jackfountain.emil_fishing.Config;
import ca.jackfountain.emil_fishing.data.CatchData;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.SystemMessageReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Map;
import java.util.regex.*;


@Mod.EventBusSubscriber(Dist.CLIENT)
public class SystemMessage {

    @SubscribeEvent
    public static void onSystemMessageReceivedEventEvent(SystemMessageReceivedEvent event) {
        String msg = event.getMessage().getString();
        if (!msg.contains("(\uE138) You caught:")) return;

        Config.totalCatches = Config.totalCatches + 1;
        Pattern catchPattern = Pattern.compile("(\uE138) You caught: \\[(.*?)]");
        Matcher matcher = catchPattern.matcher(msg);
        String catchName = matcher.group(1);

        if (CatchData.isFish(catchName)) {
            String fishName = CatchData.extractFishName(catchName);
            int rarity = CatchData.getFishRarity(fishName);
            int weight = CatchData.getFishWeight(catchName);

            List<List<Integer>> fishMap = CatchData.isElusiveFish(fishName)
                    ? Config.elusiveFish
                    : Config.fish;

            List<Integer> fishWeights = fishMap.get(rarity);
            fishWeights.set(weight, fishWeights.get(weight) + 1);
        }
        else {
            incrementIfPresent(CatchData.trashTypeMap, Config.trash, catchName);
            incrementIfPresent(CatchData.pearlTypeMap, Config.pearls, catchName);
            incrementIfPresent(CatchData.spiritTypeMap, Config.spirits, catchName);
            incrementIfPresent(CatchData.treasureTypeMap, Config.treasures, catchName);
        }

        Config.save();
        appendTextToFile(msg);
    }


    private static void incrementIfPresent(Map<String, Integer> typeMap, List<Integer> list, String catchName) {
        Integer index = typeMap.get(catchName);
        if (index != null) {
            list.set(index, list.get(index) + 1);
        }
    }
    private static void appendTextToFile(String text) {
        String userHome = System.getProperty("user.home");
        Path filePath = Paths.get(userHome, "Downloads", "chats.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write fishing spot to Downloads folder: " + e.getMessage());
        }
    }
}
