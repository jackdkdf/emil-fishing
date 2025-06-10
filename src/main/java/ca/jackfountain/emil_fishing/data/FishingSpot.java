package ca.jackfountain.emil_fishing.data;

import ca.jackfountain.emil_fishing.Config;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FishingSpot {
    private final BlockPos pos;
    private String stock;
    private String lowerStability;
    private String upperStability;
    private final ArrayList<Quantifier> quantifiers = new ArrayList<>();

    public FishingSpot(BlockPos pos, String rawText) {
        this.pos = pos;
        String[] lines = rawText.split("\n");
        for(String line : lines){
            line = line.toLowerCase();
            if(line.contains("stock: ")) {
                this.stock = line.replace("stock: ", "").trim();
            } else if (line.contains("stability cost: ")) {
                Pattern pattern = Pattern.compile("stability cost: (\\d+)-(\\d+)%");
                Matcher matcher = pattern.matcher(line);

                if (matcher.matches()) {
                    this.lowerStability = matcher.group(1).trim();  // The digits
                    this.upperStability = matcher.group(2).trim();
                }
            } else if (line.contains("+") || line.contains("%")){
                Pattern pattern = Pattern.compile(".\\+?(\\d+)%? (.*)");
                Matcher matcher = pattern.matcher(line);

                if (matcher.matches()) {
                    String percent = matcher.group(1).trim();  // The digits
                    String type = matcher.group(2).trim();

                    quantifiers.add(new Quantifier(type, Integer.parseInt(percent)));
                }
            }
        }
    }

    public BlockPos getPos() {
        return pos;
    }

    public String getStock() {
        return stock;
    }

    public ArrayList<Quantifier> getQuantifiers() {
        return quantifiers;
    }

    @Override
    public String toString() {
        String sQuantifiers = "";
        for(Quantifier quantifier : quantifiers){
            sQuantifiers = sQuantifiers + " " + quantifier;
        }
        String coordinates = String.format("XYZ: %.1f / %.1f / %.1f", (double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
        return coordinates + " " + stock + sQuantifiers + (lowerStability != null && upperStability != null ? " " + lowerStability + "-" + upperStability : "");

    }

    public Integer getStabilityColor() {
        List<String> quantifierTypeList = quantifiers.stream()
                .map(Quantifier::type)
                .toList();
        List<Integer> quantifierPercentList = quantifiers.stream()
                .map(Quantifier::percent)
                .toList();

        Map<String, Integer> treasureStabilityMap = Map.of(
                "10-20", Config.STOCK_COLOR_MEDIUM,
                "7-13", Config.STOCK_COLOR_HIGH,
                "5-9", Config.STOCK_COLOR_VERY_HIGH
        );

        Map<String, Integer> spiritStabilityMap = Map.of(
                "5-10", Config.STOCK_COLOR_MEDIUM,
                "3-7", Config.STOCK_COLOR_HIGH,
                "2-5", Config.STOCK_COLOR_VERY_HIGH
        );

        Map<String, Integer> pearlStabilityMap = Map.of(
                "3-9", Config.STOCK_COLOR_MEDIUM,
                "2-7", Config.STOCK_COLOR_HIGH,
                "1-5", Config.STOCK_COLOR_VERY_HIGH
        );

        Map<String, Integer> elusiveStabilityMap = Map.of(
                "3-9", Config.STOCK_COLOR_MEDIUM,
                "2-7", Config.STOCK_COLOR_HIGH,
                "1-5", Config.STOCK_COLOR_VERY_HIGH
        );

        Map<String, Integer> highMagnetStabilityMap = Map.of(
                "3-7", Config.STOCK_COLOR_MEDIUM,
                "2-5", Config.STOCK_COLOR_HIGH,
                "1-4", Config.STOCK_COLOR_VERY_HIGH
        );

        Map<String, Integer> fishStabilityMap = Map.of(
                "1-3", Config.STOCK_COLOR_MEDIUM,
                "0-3", Config.STOCK_COLOR_HIGH,
                "0-2", Config.STOCK_COLOR_VERY_HIGH
        );

        String stabilityRange = lowerStability + "-" + upperStability;

        if (quantifierTypeList.contains("treasure chance")) {
            return treasureStabilityMap.getOrDefault(stabilityRange, Config.WHITE);
        }
        else if (quantifierTypeList.contains("spirit chance")) {
            return spiritStabilityMap.getOrDefault(stabilityRange, Config.WHITE);
        }
        else if (quantifierTypeList.contains("pearl chance")) {
            return pearlStabilityMap.getOrDefault(stabilityRange, Config.WHITE);
        }
        else if (quantifierTypeList.contains("elusive fish chance")) {
            return elusiveStabilityMap.getOrDefault(stabilityRange, Config.WHITE);
        }
        else if (quantifierPercentList.contains(200)) {
            return highMagnetStabilityMap.getOrDefault(stabilityRange, Config.WHITE);
        }
        else if (quantifierTypeList.contains("fish chance")) {
            return fishStabilityMap.getOrDefault(stabilityRange, Config.WHITE);
        }
        return Config.WHITE;
    }

}

