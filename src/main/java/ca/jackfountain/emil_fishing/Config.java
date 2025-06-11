package ca.jackfountain.emil_fishing;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = EmilFishing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    // Color and Rarity Constants
    public static final class Color {
        public static final int WHITE = 0xffffff, RED = 0xfc5454, BLUE = 0x2199f0, PURPLE = 0x8632fc;
        public static final int ORANGE = 0xfc7c3c, GREEN = 0x23c525;
        public static final int STOCK_LOW = 0xf47404, STOCK_MEDIUM = 0xfcfc54, STOCK_HIGH = 0x54fc54;
        public static final int STOCK_VERY_HIGH = 0x68e3ea, STOCK_PLENTIFUL = 0xac6cfc;
        public static final int RARITY_COMMON = 0xfcfcfc, RARITY_UNCOMMON = 0x1efc0c, RARITY_RARE = 0x006ffc;
        public static final int RARITY_EPIC = 0xa134eb, RARITY_LEGENDARY = 0xfc7e00, RARITY_MYTHIC = 0xf64141;
    }

    // Display Keys
    public static final String[] HOOK_KEYS = {"strong hook", "wise hook", "glimmering hook", "greedy hook", "lucky hook"};
    public static final String[] MAGNET_KEYS = {"xp magnet", "fish magnet", "pearl magnet", "treasure magnet", "spirit magnet"};
    public static final String[] CHANCE_KEYS = {"elusive fish chance", "wayfinder data", "pearl chance", "treasure chance", "spirit chance"};
    public static final String[] STABILITY_KEYS = {"yellow", "green", "blue"};
    public static final int[] STABILITY_HEX_KEYS = {Color.STOCK_MEDIUM, Color.STOCK_HIGH, Color.STOCK_VERY_HIGH};

    // Config Spec
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    // Boolean config values
    private static final ForgeConfigSpec.ConfigValue<Boolean> AND_FILTER = bool("andFilter", false, "Enable this to use AND across filter categories instead of OR");
    private static final ForgeConfigSpec.ConfigValue<Boolean> TOTAL_CATCHES_DISPLAY = bool("totalCatchesDisplay", true, "Enable this to display total catches");
    private static final ForgeConfigSpec.ConfigValue<Boolean> FISH_DISPLAY = bool("fishDisplay", true, "Enable this to display fish catches");
    private static final ForgeConfigSpec.ConfigValue<Boolean> ELUSIVE_FISH_DISPLAY = bool("elusiveFishDisplay", true, "Enable this to display elusive fish catches");
    private static final ForgeConfigSpec.ConfigValue<Boolean> SPECIAL_CATCH_DISPLAY = bool("specialDisplay", true, "Enable this to display special catches");

    // Display settings (booleans as lists)
    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> HOOKS_DISPLAY =
            boolList("hooksDisplay", HOOK_KEYS.length, "Display settings for hooks: [Strong, Wise, Glimmering, Greedy, Lucky]");
    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> MAGNETS_DISPLAY =
            boolList("magnetsDisplay", MAGNET_KEYS.length, "Display settings for magnets: [XP, Fish, Pearl, Treasure, Spirit]");
    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> CHANCES_DISPLAY =
            boolList("chancesDisplay", CHANCE_KEYS.length, "Display settings for chances: [Elusive, Wayfinder, Pearl, Treasure, Spirit]");
    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> STABILITIES_DISPLAY =
            boolList("stabilitiesDisplay", STABILITY_KEYS.length, "Display settings for stabilities: [Yellow, Green, Blue]");

    // Catch metrics
    private static final ForgeConfigSpec.ConfigValue<Integer> TOTAL_CATCHES =
            intVal("totalCatches", 0, "Total number of catches");
    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> PEARLS =
            intList("pearls", 3, "Pearls catch data array [Rough, Polished, Pristine]");
    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> TREASURES =
            intList("treasures", 6, "Treasures catch data array [Common, Uncommon, Rare, Epic, Legendary, Mythic]");
    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> SPIRITS =
            intList("spirits", 3, "Spirits catch data array [Regular, Refined, Pure]");
    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> TRASH =
            intList("trash", 5, "Trash catch data array [Common, Uncommon, Rare, Epic, Legendary]");

    // Fish matrices
    private static final ForgeConfigSpec.ConfigValue<List<? extends List<? extends Integer>>> FISH =
            matrix("fish", 6, 4, "Fish catch data matrix (6 rows, 4 columns)");
    private static final ForgeConfigSpec.ConfigValue<List<? extends List<? extends Integer>>> ELUSIVE_FISH =
            matrix("elusiveFish", 6, 4, "Elusive fish catch data matrix (6 rows, 4 columns)");

    // Grotto metrics (repeat of above)
    private static final ForgeConfigSpec.ConfigValue<Integer> TOTAL_CATCHES_GROTTO =
            intVal("totalCatchesGrotto", 0, "Total number of catches (Grotto)");
    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> PEARLS_GROTTO =
            intList("pearlsGrotto", 3, "Pearls catch data array (Grotto)");
    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> TREASURES_GROTTO =
            intList("treasuresGrotto", 6, "Treasures catch data array (Grotto)");
    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> SPIRITS_GROTTO =
            intList("spiritsGrotto", 3, "Spirits catch data array (Grotto)");
    private static final ForgeConfigSpec.ConfigValue<List<? extends List<? extends Integer>>> FISH_GROTTO =
            matrix("fishGrotto", 6, 4, "Fish catch data matrix (Grotto)");
    private static final ForgeConfigSpec.ConfigValue<List<? extends List<? extends Integer>>> ELUSIVE_FISH_GROTTO =
            matrix("elusiveFishGrotto", 6, 4, "Elusive fish catch data matrix (Grotto)");

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    // Public mirrors
    public static boolean andFilter, totalCatchesDisplay, fishDisplay, elusiveFishDisplay, specialDisplay;
    public static List<Boolean> hooksDisplay, magnetsDisplay, chancesDisplay, stabilitiesDisplay;
    public static int totalCatches, totalCatchesGrotto;
    public static List<Integer> pearls, treasures, spirits, trash, pearlsGrotto, treasuresGrotto, spiritsGrotto;
    public static List<List<Integer>> fish, elusiveFish, fishGrotto, elusiveFishGrotto;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        andFilter = AND_FILTER.get();
        totalCatchesDisplay = TOTAL_CATCHES_DISPLAY.get();
        fishDisplay = FISH_DISPLAY.get();
        elusiveFishDisplay = ELUSIVE_FISH_DISPLAY.get();
        specialDisplay = SPECIAL_CATCH_DISPLAY.get();

        hooksDisplay      = toList(HOOKS_DISPLAY.get(), HOOK_KEYS.length, true);
        magnetsDisplay    = toList(MAGNETS_DISPLAY.get(), MAGNET_KEYS.length, true);
        chancesDisplay    = toList(CHANCES_DISPLAY.get(), CHANCE_KEYS.length, true);
        stabilitiesDisplay= toList(STABILITIES_DISPLAY.get(), STABILITY_KEYS.length, true);

        totalCatches = TOTAL_CATCHES.get();
        pearls    = toList(PEARLS.get(), 3, 0);
        treasures = toList(TREASURES.get(), 6, 0);
        spirits   = toList(SPIRITS.get(), 3, 0);
        trash     = toList(TRASH.get(), 5, 0);
        fish        = toMatrix(FISH.get(), 6, 4, 0);
        elusiveFish = toMatrix(ELUSIVE_FISH.get(), 6, 4, 0);

        totalCatchesGrotto = TOTAL_CATCHES_GROTTO.get();
        pearlsGrotto    = toList(PEARLS_GROTTO.get(), 3, 0);
        treasuresGrotto = toList(TREASURES_GROTTO.get(), 6, 0);
        spiritsGrotto   = toList(SPIRITS_GROTTO.get(), 3, 0);
        fishGrotto        = toMatrix(FISH_GROTTO.get(), 6, 4, 0);
        elusiveFishGrotto = toMatrix(ELUSIVE_FISH_GROTTO.get(), 6, 4, 0);
    }

    public static void save() {
        AND_FILTER.set(andFilter);
        TOTAL_CATCHES_DISPLAY.set(totalCatchesDisplay);
        FISH_DISPLAY.set(fishDisplay);
        ELUSIVE_FISH_DISPLAY.set(elusiveFishDisplay);
        SPECIAL_CATCH_DISPLAY.set(specialDisplay);

        HOOKS_DISPLAY.set(hooksDisplay);
        MAGNETS_DISPLAY.set(magnetsDisplay);
        CHANCES_DISPLAY.set(chancesDisplay);
        STABILITIES_DISPLAY.set(stabilitiesDisplay);

        TOTAL_CATCHES.set(totalCatches);
        PEARLS.set(pearls);
        TREASURES.set(treasures);
        SPIRITS.set(spirits);
        TRASH.set(trash);
        FISH.set(fish);
        ELUSIVE_FISH.set(elusiveFish);

        TOTAL_CATCHES_GROTTO.set(totalCatchesGrotto);
        PEARLS_GROTTO.set(pearlsGrotto);
        TREASURES_GROTTO.set(treasuresGrotto);
        SPIRITS_GROTTO.set(spiritsGrotto);
        FISH_GROTTO.set(fishGrotto);
        ELUSIVE_FISH_GROTTO.set(elusiveFishGrotto);
    }

    // --- Helper methods for config spec definitions ---

    private static ForgeConfigSpec.ConfigValue<Boolean> bool(String key, boolean def, String comment) {
        return BUILDER.comment(comment).define(key, def);
    }
    private static ForgeConfigSpec.ConfigValue<Integer> intVal(String key, int def, String comment) {
        return BUILDER.comment(comment).define(key, def);
    }
    private static ForgeConfigSpec.ConfigValue<List<? extends Boolean>> boolList(String key, int size, String comment) {
        return BUILDER.comment(comment).defineList(key, Collections.nCopies(size, true), e -> e instanceof Boolean);
    }
    private static ForgeConfigSpec.ConfigValue<List<? extends Integer>> intList(String key, int size, String comment) {
        return BUILDER.comment(comment).defineList(key, Collections.nCopies(size, 0), e -> e instanceof Integer);
    }
    private static ForgeConfigSpec.ConfigValue<List<? extends List<? extends Integer>>> matrix(String key, int rows, int cols, String comment) {
        return BUILDER.comment(comment).define(key, defaultMatrix(rows, cols), Config::validateMatrix);
    }

    // --- List/Matrix validation and conversion ---

    private static List<List<Integer>> defaultMatrix(int rows, int cols) {
        List<List<Integer>> matrix = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) matrix.add(new ArrayList<>(Collections.nCopies(cols, 0)));
        return matrix;
    }
    private static boolean validateMatrix(Object obj) {
        if (!(obj instanceof List<?> rows) || rows.size() != 6) return false;
        for (Object row : rows) {
            if (!(row instanceof List<?> cells) || cells.size() != 4)
                return false;
            for (Object cell : cells)
                if (!(cell instanceof Integer))
                    return false;
        }
        return true;
    }
    private static <T> List<T> toList(List<?> list, int size, T def) {
        if (list == null || list.size() != size) return new ArrayList<>(Collections.nCopies(size, def));
        @SuppressWarnings("unchecked")
        List<T> typed = (List<T>) list;
        return new ArrayList<>(typed);
    }
    private static List<List<Integer>> toMatrix(List<?> list, int rows, int cols, int def) {
        if (list == null || list.size() != rows) return defaultMatrix(rows, cols);
        List<List<Integer>> matrix = new ArrayList<>(rows);
        for (Object row : list) {
            if (!(row instanceof List<?> cells) || cells.size() != cols)
                return defaultMatrix(rows, cols);
            List<Integer> intRow = new ArrayList<>(cols);
            for (Object cell : cells)
                intRow.add(cell instanceof Integer ? (Integer) cell : def);
            matrix.add(intRow);
        }
        return matrix;
    }
}
