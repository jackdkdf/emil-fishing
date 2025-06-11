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
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = EmilFishing.MODID, value = Dist.CLIENT)
public class HudOverlay {

    private static final Map<String, Integer> COLOR_MAPPING = Map.ofEntries(
            Map.entry("Fishing Spots", 0xfce464),
            Map.entry("XYZ:", 0xADD8E6),
            Map.entry("blocks", 0x38BCAE),

            Map.entry("strong hook", Config.Color.RED),
            Map.entry("xp magnet", Config.Color.RED),
            Map.entry("elusive fish chance", Config.Color.RED),

            Map.entry("wise hook", Config.Color.BLUE),
            Map.entry("fish magnet", Config.Color.BLUE),
            Map.entry("fish chance", Config.Color.BLUE),
            Map.entry("wayfinder data", Config.Color.BLUE),

            Map.entry("glimmering hook", Config.Color.PURPLE),
            Map.entry("pearl magnet", Config.Color.PURPLE),
            Map.entry("pearl chance", Config.Color.PURPLE),

            Map.entry("greedy hook", Config.Color.ORANGE),
            Map.entry("treasure magnet", Config.Color.ORANGE),
            Map.entry("treasure chance", Config.Color.ORANGE),

            Map.entry("lucky hook", Config.Color.GREEN),
            Map.entry("spirit magnet", Config.Color.GREEN),
            Map.entry("spirit chance", Config.Color.GREEN),

            Map.entry("low", Config.Color.STOCK_LOW),
            Map.entry("medium", Config.Color.STOCK_MEDIUM),
            Map.entry("high", Config.Color.STOCK_HIGH),
            Map.entry("very high", Config.Color.STOCK_VERY_HIGH),
            Map.entry("plentiful", Config.Color.STOCK_PLENTIFUL),

            Map.entry("Total catches", 0x38BCAE),
            Map.entry("Fish Catches", 0xfce464),
            Map.entry("Elusive Fish Catches", 0xfce464),
            Map.entry("Special Catches", 0xfce464),
            Map.entry("Common", Config.Color.RARITY_COMMON),
            Map.entry("Uncommon", Config.Color.RARITY_UNCOMMON),
            Map.entry("Rare", Config.Color.RARITY_RARE),
            Map.entry("Epic", Config.Color.RARITY_EPIC),
            Map.entry("Legendary", Config.Color.RARITY_LEGENDARY),
            Map.entry("Mythic", Config.Color.RARITY_MYTHIC),

            Map.entry("Trash", 0x737373),
            Map.entry("Pearls", Config.Color.PURPLE),
            Map.entry("Spirits", Config.Color.GREEN),
            Map.entry("Treasures", Config.Color.ORANGE)
    );

    /**
     * Displays fishing spots and player coordinates on the HUD.
     */
    @SubscribeEvent
    public static void displayFishSpots(CustomizeGuiOverlayEvent.Chat event) {
        final Minecraft mc = Minecraft.getInstance();
        final GuiGraphics guiGraphics = event.getGuiGraphics();
        final int x = event.getPosX();
        int y = event.getPosY() - 30;

        final Collection<FishingSpot> spots = FishingSpotManager.getInstance().getFilteredSpots();
        final String coordinates = String.format("XYZ: %.1f / %.1f / %.1f",
                mc.player.getX(), mc.player.getY(), mc.player.getZ());

        final List<String> lines = new ArrayList<>();
        final List<Integer> stabilityColors = new ArrayList<>();

        lines.add(coordinates);
        lines.add("--- Fishing Spots ---");
        stabilityColors.add(Config.Color.WHITE);
        stabilityColors.add(Config.Color.WHITE);

        spots.forEach(spot -> {
            lines.add(spot + calcDistanceString(spot.getPos()));
            stabilityColors.add(spot.getStabilityColor());
        });

        renderLines(guiGraphics, mc.font, lines, stabilityColors, x, y, 0.75f, true);
    }

    /**
     * Displays fish catch statistics on the HUD.
     */
    @SubscribeEvent
    public static void displayFishCatches(CustomizeGuiOverlayEvent.Chat event) {
        final Minecraft mc = Minecraft.getInstance();
        final GuiGraphics guiGraphics = event.getGuiGraphics();
        final int screenWidth = mc.getWindow().getGuiScaledWidth();
        int y = event.getPosY() - 20;

        final List<String> lines = new ArrayList<>();

        if (FishingSpotManager.getInstance().isGrotto()) {
            if (Config.totalCatchesDisplay) lines.add(String.format("Total Grotto Catches: %d", Config.totalCatchesGrotto));
            if (Config.fishDisplay) addFishCatchLines(lines, "Fish Catches ----------", Config.fishGrotto);
            if (Config.elusiveFishDisplay) addFishCatchLines(lines, "Elusive Fish Catches ---", Config.elusiveFishGrotto);

            if (Config.specialDisplay) {
                lines.add("Special Catches -------");
                lines.add("Pearls: " + joinInts(Config.pearlsGrotto));
                lines.add("Spirits: " + joinInts(Config.spiritsGrotto));
                lines.add("Treasures: " + joinInts(Config.treasuresGrotto));
            }
        }
        else {
            if (Config.totalCatchesDisplay) lines.add(String.format("Total Catches: %d", Config.totalCatches));
            if (Config.fishDisplay) addFishCatchLines(lines, "Fish Catches ----------", Config.fish);
            if (Config.elusiveFishDisplay) addFishCatchLines(lines, "Elusive Fish Catches ---", Config.elusiveFish);

            if (Config.specialDisplay) {
                lines.add("Special Catches -------");
                lines.add("Trash: " + joinInts(Config.trash));
                lines.add("Pearls: " + joinInts(Config.pearls));
                lines.add("Spirits: " + joinInts(Config.spirits));
                lines.add("Treasures: " + joinInts(Config.treasures));
            }
        }

        renderLines(guiGraphics, mc.font, lines, null, screenWidth, y, 0.6f, false);
    }

    private static String joinInts(List<Integer> list) {
        return list.stream().map(String::valueOf).collect(Collectors.joining("/"));
    }

    private static void addFishCatchLines(List<String> lines, String header, List<List<Integer>> fishData) {
        final String[] rarities = {"Common", "Uncommon", "Rare", "Epic", "Legendary", "Mythic"};
        lines.add(header);
        for (int i = 0; i < rarities.length; i++) {
            lines.add(rarities[i] + ": " + joinInts(fishData.get(i)));
        }
    }

    /**
     * Renders multiple lines of text with optional stability colors and color keyword highlighting.
     *
     * @param guiGraphics      The GUI graphics context.
     * @param font             The font renderer.
     * @param lines            The lines to render.
     * @param stabilityColors  List of stability colors per line, or null if not applicable.
     * @param baseX            The base X position (left or right aligned).
     * @param baseY            The base Y position.
     * @param scale            The scale factor for text.
     * @param leftAlign        True if left aligned, false if right aligned.
     */
    private static void renderLines(GuiGraphics guiGraphics, Font font, List<String> lines,
                                    List<Integer> stabilityColors, int baseX, int baseY,
                                    float scale, boolean leftAlign) {
        final int padding = 1;
        int yOffset = baseY;

        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            final int textWidth = (int) (font.width(line) * scale);
            final int textHeight = (int) (font.lineHeight * scale);
            final int x = leftAlign ? baseX : baseX - textWidth - padding;

            // Draw translucent background
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
            final int stabilityColor = stabilityColors != null && i < stabilityColors.size()
                    ? stabilityColors.get(i) : Config.Color.WHITE;

            while (!remainingText.isEmpty()) {
                final ColorMatch match = findLongestColorMatch(remainingText);
                if (match != null) {
                    drawTextSegment(guiGraphics, font, match.text, currentX, (int) (yOffset / scale), match.color);
                    currentX += font.width(match.text);
                    remainingText = remainingText.substring(match.text.length());
                } else {
                    final int nextSpace = remainingText.indexOf(' ');
                    final String segment;
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
                    final int segColor = segment.contains("-") ? stabilityColor : Config.Color.WHITE;
                    drawTextSegment(guiGraphics, font, segment, currentX, (int) (yOffset / scale), segColor);
                    currentX += font.width(segment);
                }
            }

            guiGraphics.pose().popPose();
            yOffset += textHeight + 2;
        }
    }

    /**
     * Finds the longest matching color keyword at the start of the text.
     *
     * @param text The text to check.
     * @return A ColorMatch object if found, otherwise null.
     */
    private static ColorMatch findLongestColorMatch(String text) {
        String matchedKey = null;
        int maxLength = 0;
        int color = Config.Color.WHITE;

        for (Map.Entry<String, Integer> entry : COLOR_MAPPING.entrySet()) {
            final String key = entry.getKey();
            if (text.startsWith(key) && key.length() > maxLength) {
                matchedKey = key;
                maxLength = key.length();
                color = entry.getValue();
            }
        }

        return matchedKey == null ? null : new ColorMatch(matchedKey, color);
    }

    private static void drawTextSegment(GuiGraphics gui, Font font, String text, int x, int y, int color) {
        gui.drawString(font, text, x, y, color);
    }

    private static String calcDistanceString(BlockPos spotPos) {
        final Minecraft mc = Minecraft.getInstance();
        final double dx = mc.player.getX() - spotPos.getX();
        final double dy = mc.player.getY() - spotPos.getY();
        final double dz = mc.player.getZ() - spotPos.getZ();
        final double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        return String.format(" %.1f blocks", distance);
    }

    private record ColorMatch(String text, int color) {
    }
}
