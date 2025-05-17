package ca.jackfountain.emil_fishing.data;

import ca.jackfountain.emil_fishing.Config;

import java.time.LocalDateTime;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FishingSpotManager {

    private static final FishingSpotManager INSTANCE = new FishingSpotManager();

    private int lastClearedHour = -1;

    public static FishingSpotManager getInstance() {
        return INSTANCE;
    }

    // Uses textDisplay ID as key
    private final Map<Integer, FishingSpot> spots = new HashMap<>();

    public void addSpot(int textDisplayId, FishingSpot spot) {
        if (spots.containsKey(textDisplayId)) {
            System.out.println("Spot with ID " + textDisplayId + " already exists. Skipping.");
            return;
        }

        spots.put(textDisplayId, spot);
        System.out.println("Added: " + spot);
        appendSpotToFile(spot);
    }


    public void clearSpots() {
        spots.clear();
        System.out.println("Cleared fishing spots.");
    }

    public Collection<FishingSpot> getSpots() {
        return spots.values();
    }

    public Collection<FishingSpot> getFilteredSpots() {return filterSpots(spots.values());}

    private void appendSpotToFile(FishingSpot spot) {
        String userHome = System.getProperty("user.home");
        Path filePath = Paths.get(userHome, "Downloads", "fishing_spots.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            writer.write(spot.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write fishing spot to Downloads folder: " + e.getMessage());
        }
    }

    public void clearIfNewHour() {
        int currentHour = LocalDateTime.now().getHour();
        if (currentHour != lastClearedHour) {
            lastClearedHour = currentHour;
            clearSpots();
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
