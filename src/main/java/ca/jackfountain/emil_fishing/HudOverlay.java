package ca.jackfountain.emil_fishing;

import ca.jackfountain.emil_fishing.data.FishingSpot;
import ca.jackfountain.emil_fishing.data.FishingSpotManager;
import ca.jackfountain.emil_fishing.data.Quantifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = EmilFishing.MODID, value = Dist.CLIENT)
public class HudOverlay {
    @SubscribeEvent
    public static void onChatOverlay(CustomizeGuiOverlayEvent.Chat event) {
        Minecraft mc = Minecraft.getInstance();
        GuiGraphics guiGraphics = event.getGuiGraphics();

        // Adjust text position
        int x = event.getPosX();
        int y = event.getPosY() - 30;

        Collection<FishingSpot> filteredSpots = FishingSpotManager.getInstance().getFilteredSpots();
        String coordinates = String.format("XYZ: %.1f / %.1f / %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());

        List<String> linesList = new ArrayList<>();
        linesList.add(coordinates);
        linesList.add("----Fishing Spots----");
        filteredSpots.forEach(spot -> linesList.add(String.valueOf(spot)));
        String[] lines = linesList.toArray(new String[0]);

        for (String line : lines) {
            guiGraphics.drawString(mc.font, line, x, y, 0x00FFAA);
            y += mc.font.lineHeight + 2; // Move down for the next line
        }
    }

    private static Collection<FishingSpot> filterSpots(Collection<FishingSpot> spots) {
        // Get all enabled filter keywords from config
        Set<String> enabledKeywords = Stream.of(
                        getEnabledKeywords(Config.hooksDisplay, Config.HOOK_KEYS),
                        getEnabledKeywords(Config.magnetsDisplay, Config.MAGNET_KEYS),
                        getEnabledKeywords(Config.chancesDisplay, Config.CHANCE_KEYS)
                )
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        return spots.stream()
                .filter(spot -> spot.getQuantifiers() != null && !spot.getQuantifiers().isEmpty())
                .filter(spot -> spot.getQuantifiers().stream()
                        .filter(Objects::nonNull)
                        .map(Quantifier::type)
                        .filter(Objects::nonNull)
                        .map(String::toLowerCase)
                        .anyMatch(type ->
                                enabledKeywords.stream()
                                        .anyMatch(type::contains)
                        )
                )
                .collect(Collectors.toList());
    }

    private static Collection<String> getEnabledKeywords(boolean[] displaySettings, String[] keys) {
        return IntStream.range(0, displaySettings.length)
                .filter(i -> displaySettings[i])
                .mapToObj(i -> keys[i])
                .collect(Collectors.toList());
    }





}
