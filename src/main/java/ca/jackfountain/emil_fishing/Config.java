package ca.jackfountain.emil_fishing;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = EmilFishing.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    // Color values for text, used internally; no need to export to config file
    public static final Integer WHITE = 0xffffff;
    public static final Integer RED = 0xfc5454;
    public static final Integer BLUE = 0x2199f0;
    public static final Integer PURPLE = 0x8632fc;
    public static final Integer ORANGE = 0xfc7c3c;
    public static final Integer GREEN = 0x23c525;

    public static final Integer STOCK_COLOR_LOW = 0xf47404;
    public static final Integer STOCK_COLOR_MEDIUM = 0xfcfc54;
    public static final Integer STOCK_COLOR_HIGH = 0x54fc54;
    public static final Integer STOCK_COLOR_VERY_HIGH = 0x68e3ea;
    public static final Integer STOCK_COLOR_PLENTIFUL = 0xac6cfc;

    public static final Integer RARITY_COMMON = 0xfcfcfc;
    public static final Integer RARITY_UNCOMMON = 0x1efc0c;
    public static final Integer RARITY_RARE = 0x006ffc;
    public static final Integer RARITY_EPIC = 0xa134eb;
    public static final Integer RARITY_LEGENDARY = 0xfc7e00;
    public static final Integer RARITY_MYTHIC = 0xf64141;

    // Translation keys for each display list, used internally; no need to export to config file
    public static final String[] HOOK_KEYS = {"strong hook", "wise hook", "glimmering hook", "greedy hook", "lucky hook"};
    public static final String[] MAGNET_KEYS = {"xp magnet", "fish magnet", "pearl magnet", "treasure magnet", "spirit magnet"};
    public static final String[] CHANCE_KEYS = {"elusive fish chance", "wayfinder data", "pearl chance", "treasure chance", "spirit chance"};
    public static final String[] STABILITY_KEYS = {"yellow", "green", "blue"};
    public static final Integer[] STABILITY_HEX_KEYS = {STOCK_COLOR_MEDIUM, STOCK_COLOR_HIGH, STOCK_COLOR_VERY_HIGH};

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<Boolean> AND_FILTER = BUILDER
            .comment("Enable this to use AND across filter categories instead of OR")
            .define("andFilter", false);

    private static final ForgeConfigSpec.ConfigValue<Boolean> TOTAL_CATCHES_DISPLAY = BUILDER
            .comment("Enable this to display total catches")
            .define("totalCatchesDisplay", true);

    private static final ForgeConfigSpec.ConfigValue<Boolean> FISH_DISPLAY = BUILDER
            .comment("Enable this to display fish catches")
            .define("fishDisplay", true);

    private static final ForgeConfigSpec.ConfigValue<Boolean> ELUSIVE_FISH_DISPLAY = BUILDER
            .comment("Enable this to display elusive fish catches")
            .define("elusiveFishDisplay", true);

    private static final ForgeConfigSpec.ConfigValue<Boolean> SPECIAL_CATCH_DISPLAY = BUILDER
            .comment("Enable this to display special catches")
            .define("specialDisplay", true);

    // Fishing spot display configs are each stored in a boolean array
    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> HOOKS_DISPLAY = BUILDER
            .comment("Display settings for hooks: [Strong, Wise, Glimmering, Greedy, Lucky]")
            .defineList("hooksDisplay", List.of(true, true, true, true, true), entry -> entry instanceof Boolean);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> MAGNETS_DISPLAY = BUILDER
            .comment("Display settings for magnets: [Strong, Wise, Glimmering, Greedy, Lucky]")
            .defineList("magnetsDisplay", List.of(true, true, true, true, true), entry -> entry instanceof Boolean);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> CHANCES_DISPLAY = BUILDER
            .comment("Display settings for chances: [Elusive, Wayfinder, Pearl, Treasure, Spirit]")
            .defineList("chancesDisplay", List.of(true, true, true, true, true), entry -> entry instanceof Boolean);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Boolean>> STABILITIES_DISPLAY = BUILDER
            .comment("Display settings for stabilities: [Yellow, Green, Blue]")
            .defineList("stabilitiesDisplay", List.of(true, true, true), entry -> entry instanceof Boolean);

    // Fishing catch metrics
    private static final ForgeConfigSpec.ConfigValue<Integer> TOTAL_CATCHES = BUILDER
            .comment("Total number of catches")
            .define("totalCatches", 0);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> PEARLS = BUILDER
            .comment("Pearls catch data array [Rough, Polished, Pristine]")
            .defineList("pearls", List.of(0, 0, 0), entry -> entry instanceof Integer);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> TREASURES = BUILDER
            .comment("Treasures catch data array [Common, Uncommon, Rare, Epic, Legendary, Mythic]")
            .defineList("treasures", List.of(0, 0, 0, 0, 0, 0), entry -> entry instanceof Integer);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> SPIRITS = BUILDER
            .comment("Spirits catch data array [Regular, Refined, Pure]")
            .defineList("spirits", List.of(0, 0, 0), entry -> entry instanceof Integer);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> TRASH = BUILDER
            .comment("Trash catch data array [Common, Uncommon, Rare, Epic, Legendary, Mythic]")
            .defineList("trash", List.of(0, 0, 0, 0, 0), entry -> entry instanceof Integer);

    // Fishes are stored in a matrix to categorize rarity and weight
    private static final ForgeConfigSpec.ConfigValue<List<List<Integer>>> FISH = BUILDER
            .comment("Fish catch data matrix (6 rows, 4 columns)")
            .define("fish",
                    defaultFishMatrix(),
                    entry -> validateFishMatrix(entry)
            );
    private static final ForgeConfigSpec.ConfigValue<List<List<Integer>>> ELUSIVE_FISH = BUILDER
            .comment("Elusive fish catch data matrix (6 rows, 4 columns)")
            .define("elusiveFish",
                    defaultFishMatrix(),
                    entry -> validateFishMatrix(entry)
            );

    // Grotto catch metrics
    private static final ForgeConfigSpec.ConfigValue<Integer> TOTAL_CATCHES_GROTTO = BUILDER
            .comment("Total number of catches")
            .define("totalCatchesGrotto", 0);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> PEARLS_GROTTO = BUILDER
            .comment("Pearls catch data array [Rough, Polished, Pristine]")
            .defineList("pearlsGrotto", List.of(0, 0, 0), entry -> entry instanceof Integer);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> TREASURES_GROTTO = BUILDER
            .comment("Treasures catch data array [Common, Uncommon, Rare, Epic, Legendary, Mythic]")
            .defineList("treasuresGrotto", List.of(0, 0, 0, 0, 0, 0), entry -> entry instanceof Integer);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> SPIRITS_GROTTO = BUILDER
            .comment("Spirits catch data array [Regular, Refined, Pure]")
            .defineList("spiritsGrotto", List.of(0, 0, 0), entry -> entry instanceof Integer);

    // Fishes are stored in a matrix to categorize rarity and weight
    private static final ForgeConfigSpec.ConfigValue<List<List<Integer>>> FISH_GROTTO = BUILDER
            .comment("Fish catch data matrix (6 rows, 4 columns)")
            .define("fishGrotto",
                    defaultFishMatrix(),
                    entry -> validateFishMatrix(entry)
            );
    private static final ForgeConfigSpec.ConfigValue<List<List<Integer>>> ELUSIVE_FISH_GROTTO = BUILDER
            .comment("Elusive fish catch data matrix (6 rows, 4 columns)")
            .define("elusiveFishGrotto",
                    defaultFishMatrix(),
                    entry -> validateFishMatrix(entry)
            );

    static final ForgeConfigSpec SPEC = BUILDER.build();

    // Config values (Lists, no arrays)
    public static boolean andFilter;
    public static boolean totalCatchesDisplay;
    public static boolean fishDisplay;
    public static boolean elusiveFishDisplay;
    public static boolean specialDisplay;

    public static List<Boolean> hooksDisplay = new ArrayList<>();
    public static List<Boolean> magnetsDisplay = new ArrayList<>();
    public static List<Boolean> chancesDisplay = new ArrayList<>();
    public static List<Boolean> stabilitiesDisplay = new ArrayList<>();

    public static int totalCatches;
    public static int totalCatchesGrotto;

    public static List<Integer> pearls = new ArrayList<>();
    public static List<Integer> treasures = new ArrayList<>();
    public static List<Integer> spirits = new ArrayList<>();
    public static List<Integer> trash = new ArrayList<>();
    public static List<List<Integer>> fish = new ArrayList<>();
    public static List<List<Integer>> elusiveFish = new ArrayList<>();

    public static List<Integer> pearlsGrotto = new ArrayList<>();
    public static List<Integer> treasuresGrotto = new ArrayList<>();
    public static List<Integer> spiritsGrotto = new ArrayList<>();
    public static List<List<Integer>> fishGrotto = new ArrayList<>();
    public static List<List<Integer>> elusiveFishGrotto = new ArrayList<>();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        andFilter = AND_FILTER.get();
        totalCatchesDisplay = TOTAL_CATCHES_DISPLAY.get();
        fishDisplay = FISH_DISPLAY.get();
        elusiveFishDisplay = ELUSIVE_FISH_DISPLAY.get();
        specialDisplay = SPECIAL_CATCH_DISPLAY.get();

        hooksDisplay      = validateList((List<Boolean>) HOOKS_DISPLAY.get(),      HOOK_KEYS.length,      true);
        magnetsDisplay    = validateList((List<Boolean>) MAGNETS_DISPLAY.get(),    MAGNET_KEYS.length,    true);
        chancesDisplay    = validateList((List<Boolean>) CHANCES_DISPLAY.get(),    CHANCE_KEYS.length,    true);
        stabilitiesDisplay= validateList((List<Boolean>) STABILITIES_DISPLAY.get(),STABILITY_KEYS.length, true);

        totalCatches = TOTAL_CATCHES.get();
        totalCatchesGrotto = TOTAL_CATCHES_GROTTO.get();

        pearls    = validateList((List<Integer>) PEARLS.get(),    3, 0);
        treasures = validateList((List<Integer>) TREASURES.get(), 6, 0);
        spirits   = validateList((List<Integer>) SPIRITS.get(),   3, 0);
        trash     = validateList((List<Integer>) TRASH.get(),     5, 0);

        fish        = validate2DList(FISH.get(), 6, 4, 0);
        elusiveFish = validate2DList(ELUSIVE_FISH.get(), 6, 4, 0);

        pearlsGrotto    = validateList((List<Integer>) PEARLS_GROTTO.get(),    3, 0);
        treasuresGrotto = validateList((List<Integer>) TREASURES_GROTTO.get(), 6, 0);
        spiritsGrotto   = validateList((List<Integer>) SPIRITS_GROTTO.get(),   3, 0);

        fishGrotto        = validate2DList(FISH_GROTTO.get(), 6, 4, 0);
        elusiveFishGrotto = validate2DList(ELUSIVE_FISH_GROTTO.get(), 6, 4, 0);

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

    private static List<List<Integer>> defaultFishMatrix() {
        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            matrix.add(new ArrayList<>(List.of(0, 0, 0, 0)));
        }
        return matrix;
    }

    private static boolean validateFishMatrix(Object entry) {
        if (!(entry instanceof List<?> rows)) return false;
        if (rows.size() != 6) return false;

        for (Object row : rows) {
            if (!(row instanceof List<?> cells)) return false;
            if (cells.size() != 4) return false;
            for (Object cell : cells) {
                if (!(cell instanceof Integer)) return false;
            }
        }
        return true;
    }

    private static <T> List<T> validateList(List<T> list, int expectedSize, T defaultValue) {
        if (list == null || list.size() != expectedSize) {
            List<T> newList = new ArrayList<>(expectedSize);
            for (int i = 0; i < expectedSize; i++) newList.add(defaultValue);
            return newList;
        }
        return list;
    }

    private static List<List<Integer>> validate2DList(List<List<Integer>> list, int rows, int cols, int defaultValue) {
        if (list == null || list.size() != rows || list.stream().anyMatch(row -> row == null || row.size() != cols)) {
            List<List<Integer>> newMatrix = new ArrayList<>(rows);
            for (int i = 0; i < rows; i++) {
                List<Integer> row = new ArrayList<>(cols);
                for (int j = 0; j < cols; j++) row.add(defaultValue);
                newMatrix.add(row);
            }
            return newMatrix;
        }
        return list;
    }

}
