package ca.jackfountain.emil_fishing.events;

import ca.jackfountain.emil_fishing.Config;
import ca.jackfountain.emil_fishing.EmilFishing;
import ca.jackfountain.emil_fishing.data.CatchData;
import ca.jackfountain.emil_fishing.data.FishingSpotManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.SystemMessageReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Mod.EventBusSubscriber(modid = EmilFishing.MODID, value = Dist.CLIENT)
public class SystemMessage {

    @SubscribeEvent
    public static void onSystemMessageReceivedEventEvent(SystemMessageReceivedEvent event) {
        if (FMLEnvironment.dist != Dist.CLIENT) return;
        if (Minecraft.getInstance().level == null) return;

        String msg = event.getMessage().getString();
        appendTextToFile(msg);
        if (msg.contains("You've discovered a")) {
            Config.totalGrottos = Config.totalGrottos + 1;
            Config.save();
        }
        if (!msg.contains("(\uE138) You caught:")) return;

        if (FishingSpotManager.getInstance().isGrotto()) Config.totalCatchesGrotto = Config.totalCatchesGrotto + 1;
        else Config.totalCatches = Config.totalCatches + 1;

        Pattern catchPattern = Pattern.compile(".*\\(\uE138\\) You caught: \\[(.*?)]\\.*");
        Matcher matcher = catchPattern.matcher(msg);
        if (!matcher.find()) return;
        String catchName = matcher.group(1);

        if (CatchData.isFish(catchName)) {
            String fishName = CatchData.extractFishName(catchName);
            int rarity = CatchData.getFishRarity(fishName);
            int weight = CatchData.getFishWeight(catchName);
            List<Integer> fishWeights = getFishWeights(fishName, rarity);
            fishWeights.set(weight, fishWeights.get(weight) + 1);
        }
        else {
            if (FishingSpotManager.getInstance().isGrotto()) {
                incrementIfPresent(CatchData.pearlTypeMap, Config.pearlsGrotto, catchName);
                incrementIfPresent(CatchData.spiritTypeMap, Config.spiritsGrotto, catchName);
                incrementIfPresent(CatchData.treasureTypeMap, Config.treasuresGrotto, catchName);
            }
            else {
                incrementIfPresent(CatchData.trashTypeMap, Config.trash, catchName);
                incrementIfPresent(CatchData.pearlTypeMap, Config.pearls, catchName);
                incrementIfPresent(CatchData.spiritTypeMap, Config.spirits, catchName);
                incrementIfPresent(CatchData.treasureTypeMap, Config.treasures, catchName);
            }
        }

        Config.save();
    }

    private static List<Integer> getFishWeights(String fishName, int rarity) {
        List<List<Integer>> fishMap;

        if (FishingSpotManager.getInstance().isGrotto()) {
            fishMap = CatchData.isElusiveFish(fishName)
                    ? Config.elusiveFishGrotto
                    : Config.fishGrotto;
        }
        else {
            fishMap = CatchData.isElusiveFish(fishName)
                    ? Config.elusiveFish
                    : Config.fish;
        }
        return fishMap.get(rarity);
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
