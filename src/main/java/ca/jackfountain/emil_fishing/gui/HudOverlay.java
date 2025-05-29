package ca.jackfountain.emil_fishing.gui;

import ca.jackfountain.emil_fishing.Config;
import ca.jackfountain.emil_fishing.EmilFishing;
import ca.jackfountain.emil_fishing.data.FishingSpot;
import ca.jackfountain.emil_fishing.data.FishingSpotManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = EmilFishing.MODID, value = Dist.CLIENT)
public class HudOverlay {
    private static final Map<String, Integer> COLOR_MAPPING = Map.ofEntries(
            Map.entry("Fishing Spots", 0xfce464),
            Map.entry("XYZ:", 0xADD8E6),
            Map.entry("blocks", 0x38BCAE),

            Map.entry("strong hook", Config.RED),
            Map.entry("xp magnet", Config.RED),
            Map.entry("elusive fish chance", Config.RED),

            Map.entry("wise hook", Config.BLUE),
            Map.entry("fish magnet", Config.BLUE),
            Map.entry("fish chance", Config.BLUE),
            Map.entry("wayfinder data", Config.BLUE),

            Map.entry("glimmering hook", Config.PURPLE),
            Map.entry("pearl magnet", Config.PURPLE),
            Map.entry("pearl chance", Config.PURPLE),

            Map.entry("greedy hook", Config.ORANGE),
            Map.entry("treasure magnet", Config.ORANGE),
            Map.entry("treasure chance", Config.ORANGE),

            Map.entry("lucky hook", Config.GREEN),
            Map.entry("spirit magnet", Config.GREEN),
            Map.entry("spirit chance", Config.GREEN),

            Map.entry("low", Config.STOCK_COLOR_LOW),
            Map.entry("medium", Config.STOCK_COLOR_MEDIUM),
            Map.entry("high", Config.STOCK_COLOR_HIGH),
            Map.entry("very high", Config.STOCK_COLOR_VERY_HIGH),
            Map.entry("plentiful", Config.STOCK_COLOR_PLENTIFUL)
    );

    @SubscribeEvent
    public static void onChatOverlay(CustomizeGuiOverlayEvent.Chat event) {
        Minecraft mc = Minecraft.getInstance();
        GuiGraphics guiGraphics = event.getGuiGraphics();

        int x = event.getPosX();
        int y = event.getPosY() - 30;

        Collection<FishingSpot> filteredSpots = FishingSpotManager.getInstance().getFilteredSpots();
        String coordinates = String.format("XYZ: %.1f / %.1f / %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());

        List<String> lines = new ArrayList<>();

        // List to allow access to stability colors later on, a little goofy but I need to somehow access the spot
        // stability which is lost info once I convert the spot into a string line to be displayed
        List<Integer> lineStabilityColor = new ArrayList<>();

        lines.add(coordinates);
        lines.add("----Fishing Spots----");
        lineStabilityColor.add(0xFFFFFF);
        lineStabilityColor.add(0xFFFFFF); // Adding this twice to offset the lines we added above that
        filteredSpots.forEach(
                spot -> {
                        lines.add(spot + calcEuclideanDistance(spot.getPos()));
                        lineStabilityColor.add(spot.getStabilityColor());
                }

        );

        float scale = 0.75f;
        int padding = 1;
        int yOffset = y;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            int textWidth = (int) (mc.font.width(line) * scale);
            int textHeight = (int) (mc.font.lineHeight * scale);

            // Draw translucent background with scaled width/height
            guiGraphics.fill(
                    x - padding,
                    yOffset - padding,
                    x + textWidth + padding,
                    yOffset + textHeight + padding,
                    0x88000000 // black, 53% opacity
            );

            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(scale, scale, 1.0f);

            int currentX = (int) (x / scale);

            String remainingText = line;
            int stabilityColor = lineStabilityColor.get(i);

            while (!remainingText.isEmpty()) {
                String match = null;
                int matchLength = 0;
                int color = 0xFFFFFF; // default color

                for (Map.Entry<String, Integer> entry : COLOR_MAPPING.entrySet()) {
                    if (remainingText.startsWith(entry.getKey()) && entry.getKey().length() > matchLength) {
                        match = entry.getKey();
                        matchLength = entry.getKey().length();
                        color = entry.getValue();
                    }
                }

                if (match != null) {
                    drawTextSegment(guiGraphics, mc.font, match, currentX, (int) (yOffset / scale), color);
                    currentX += mc.font.width(match);
                    remainingText = remainingText.substring(matchLength);
                } else {
                    int nextSpace = remainingText.indexOf(' ');
                    String segment;
                    if (nextSpace == 0) {
                        segment = " ";
                        remainingText = remainingText.substring(1);
                    } else if (nextSpace > 0) {
                        segment = remainingText.substring(0, nextSpace);
                        remainingText = remainingText.substring(nextSpace);
                    } else {
                        segment = remainingText;
                        remainingText = "";
                    }

                    int segColor = segment.contains("-") ? stabilityColor : 0xFFFFFF;
                    drawTextSegment(guiGraphics, mc.font, segment, currentX, (int) (yOffset / scale), segColor);
                    currentX += mc.font.width(segment);
                }
            }

            guiGraphics.pose().popPose();

            yOffset += textHeight + 2; // Move down for the next line
        }
    }

    private static void drawTextSegment(GuiGraphics gui, Font font, String text, int x, int y, int color) {
        gui.drawString(
                font,
                text,
                x,
                y,
                color
        );
    }

    private static String calcEuclideanDistance(BlockPos spotPos) {
        Minecraft mc = Minecraft.getInstance();
        double dx = mc.player.getX() - spotPos.getX();
        double dy = mc.player.getY() - spotPos.getY();
        double dz = mc.player.getZ() - spotPos.getZ();
        return String.format(" %.1f blocks", Math.sqrt(dx * dx + dy * dy + dz * dz));
    }
}


