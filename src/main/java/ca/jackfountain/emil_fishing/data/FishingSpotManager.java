package ca.jackfountain.emil_fishing.data;

import java.time.LocalDateTime;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
}
