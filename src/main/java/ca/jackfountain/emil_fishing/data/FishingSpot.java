package ca.jackfountain.emil_fishing.data;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;
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
}

