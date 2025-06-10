package ca.jackfountain.emil_fishing.data;

import java.util.Map;
import java.util.Set;

public class CatchData {
    public static final Map<String, Integer> trashTypeMap = Map.of(
            "Rusted Can", 0,
            "Tangled Kelp", 1,
            "Lost Shoe", 2,
            "Royal Residue", 3,
            "Forgotten Crown", 4
    );

    public static final Map<String, Integer> pearlTypeMap = Map.of(
            "Rough Pearl", 0,
            "Polished Pearl", 1,
            "Pristine Pearl", 2
    );

    public static final Map<String, Integer> spiritTypeMap = Map.ofEntries(
            Map.entry("Strong Spirit", 0),
            Map.entry("Wise Spirit", 0),
            Map.entry("Glimmering Spirit", 0),
            Map.entry("Greedy Spirit", 0),
            Map.entry("Lucky Spirit", 0),
            Map.entry("Refined Strong Spirit", 1),
            Map.entry("Refined Wise Spirit", 1),
            Map.entry("Refined Glimmering Spirit", 1),
            Map.entry("Refined Greedy Spirit", 1),
            Map.entry("Refined Lucky Spirit", 1),
            Map.entry("Pure Strong Spirit", 2),
            Map.entry("Pure Wise Spirit", 2),
            Map.entry("Pure Glimmering Spirit", 2),
            Map.entry("Pure Greedy Spirit", 2),
            Map.entry("Pure Lucky Spirit", 2)
    );

    public static final Map<String, Integer> treasureTypeMap = Map.of(
            "Common Treasure", 0,
            "Uncommon Treasure", 1,
            "Rare Treasure", 2,
            "Epic Treasure", 3,
            "Legendary Treasure", 4,
            "Mythic Treasure", 5
    );

    private static final Set<String> COMMON_FISH = Set.of(
            "Plain Goby", "Forest Cod", "Stone Salmon",
            "Rose Anchovy", "Daisy Pike", "Poppy Clownfish",
            "Grove Cod", "Hedgehog Fish", "Fungal Salmon",
            "Pale Minnow", "Boggy Bass", "Gloopy Gar",
            "Daintree Guppy", "Sinharaja Salmon", "Jungle Clownfish",
            "Reef Anchovy", "Hammerhead Haddock", "Coral Beauty",
            "Mud Minnow", "Twisted Sturgeon", "Grave Gudgeon",
            "Midnight Sprat", "Faint Pike", "Coral Cod",
            "Gobi Goby", "Sand Snook", "Sandy Salmon",
            "Sunburnt Goby", "Golden Snapper", "Barrockuda",
            "Hot Cod", "Blackmouth Salmon", "Ashen Gourami",
            "Molten Anchovy", "Lava Goby", "Basalt Discus"
    );

    private static final Set<String> UNCOMMON_FISH = Set.of(
            "Spring Sprat", "Stringfish", "Stone Carp",
            "Temperate Remora", "Maple Fangtooth", "Clover Koi",
            "Walking Catfish", "Frilled Mackerel", "Agaric Tarpon",
            "Fern Flounder", "Greengill", "Overgrown Grouper",
            "Tiger Mackerel", "Viperfish", "Jungle Fangtooth",
            "Neon Tetra", "Serpae Tetra", "Staghorn Flounder",
            "Autumn Sprat", "Twisted Fangtooth", "Mud Pike",
            "Ruby Remora", "Silver Snook", "Glass Pike",
            "Summer Sprat", "Desert Clownfish", "Desert Mackerel",
            "Mesa Flounder", "Orange Angelfish", "Congo Tetra",
            "Wailing Walleye", "Charred Char", "Boiling Boxfish",
            "Geyser Gudgeon", "Ashen Koi", "Heated Haddock"
    );

    private static final Set<String> RARE_FISH = Set.of(
            "Tree Perch", "Shade Bass", "Tarnished Goldfish",
            "Olive Flounder", "Daliafish", "Lavender Leatherneck",
            "Grove Barracuda", "Fungal Flounder", "Emperor Angelfish",
            "Sunken Knifejaw", "Sunken Koi", "Shardine",
            "Red Snapper", "Midnight Minnow", "Emerald Jewelfish",
            "Tropical Barracuda", "Midnight Mbuna", "Pearlescent Gourami",
            "Gloopy Goldfish", "Snake Mackerel", "Mudfin Catfish",
            "Crystalline Cod", "Midnight Koi", "Treasured Tilapia",
            "Sunny Sardine", "Moonfin Flounder", "Royal Gramma",
            "Blazing Gourami", "Painted Discus", "Canyon Perch",
            "Volcanic Spikefish", "Ashen Grouper", "Volcanic Goldfish",
            "Coal Cod", "Shadow Salmon", "Flying Flashfish"
    );

    private static final Set<String> EPIC_FISH = Set.of(
            "Mossy Mackerel", "Branch Snapper", "Foxfish",
            "Lime Sole", "Blossom Betta", "Rainbow Trout",
            "Sprout Trout", "Midnight Tang", "Snaggletooth Gar",
            "Ancient Snapper", "Quagmire Catfish", "Lemon Sole",
            "Bluegill", "Surgeonfish", "Butterfly Fish",
            "Rainbow Kribensis", "Banggai Cardinalfish", "Coral Angelfish",
            "Twisted Koi", "Gloom Muffet", "Alligator Gar",
            "Mosaic Guppy", "Golden Tuna", "Rose Goldfish",
            "Speckled Grouper", "Ancient Fangtooth", "Clown Triggerfish",
            "Goldfish", "Scorpionfish", "Golden Trout",
            "Volcanic Remora", "Black Velvetfish", "Emberpike",
            "Wobbegong Fish", "Pompeii Pampano", "Ashen Goliath"
    );

    private static final Set<String> LEGENDARY_FISH = Set.of(
            "Sunset Fish", "Fairy Goldfish", "Mossy Mullet",
            "Peacock Cichlid", "Flowerhown Cichlid", "Tulapia",
            "Portobello Grouper", "Midnight Gourami", "Queenfish",
            "Everglade Gourami", "Swampy Betta", "Glowberry Guppy",
            "Pineapple Piranha", "Jagged Zebrafish", "Zebra Turkeyfish",
            "Ocean Sunfish", "Braincoral Betta", "Gorgonian Grouper",
            "Tiny Mudskipper", "Ocean Moonfish", "Goliath Tigerfish",
            "Obsidian Guppy", "Sapphire Salmon", "Pearlescent Betta",
            "Cacti Pufferfish", "Bone Swordfish", "Ancient Trevally",
            "Sunburnt Surgeonfish", "Golden Cichlid", "Burnt Basa",
            "Lava Piranha", "Ashen Sturgeon", "Volcanic Butterflyfish",
            "Molten Triggerfish", "Scalding Mullet",  "Molten Goldfish"
    );

    private static final Set<String> MYTHIC_FISH = Set.of(
            "Arowana", "Tilapia", "Dorado",
            "Giant Treevalley", "Lotus Tuna", "Napoleonfish",
            "Arapaima", "Nightmare Marlin", "Troutarium",
            "Rusted Pike", "Floodfish", "Mutated Marlin",
            "Viney Perch", "Parrotfish", "Binding Jellyfish",
            "Sugarload Pufferfish", "Disco Discus", "Mahi Mahi",
            "Barred Knifejaw", "Mimifry", "Green Terror Cichlid",
            "Diamond Oarfish", "Mirrored Mahi", "Cosmic Cod",
            "Baked Sunfish", "Ghostfin", "Wreckfish",
            "Blazing Betta", "Solar Mackerel", "Phoenix Angelfish",
            "Volcanic Arowana", "Ashen Tuna", "Driftfish Raider",
            "Volcanic Surgeonfish", "Ashen Tilapia", "Torch Tarpon"
    );

    public static final Set<String> ELUSIVE_FISH = Set.of(
            "Ancient Trevally", "Clown Triggerfish", "Desert Mackerel", "Royal Gramma", "Sandy Salmon", "Wreckfish",
            "Ashen Gourami", "Boiling Boxfish", "Driftfish Raider", "Emberpike", "Volcanic Butterflyfish", "Volcanic Goldfish",
            "Barrockuda", "Burnt Basa", "Canyon Perch", "Congo Tetra", "Golden Trout", "Phoenix Angelfish",
            "Coral Angelfish", "Coral Beauty", "Gorgonian Grouper", "Mahi Mahi", "Pearlescent Gourami", "Staghorn Flounder",
            "Emperor Angelfish", "Fungal Salmon", "Queenfish", "Snaggletooth Gar", "Troutarium", "Agaric Tarpon",
            "Clover Koi", "Lavender Leatherneck", "Napoleonfish", "Poppy Clownfish", "Rainbow Trout", "Tulapia",
            "Coral Cod", "Cosmic Cod", "Glass Pike", "Pearlescent Betta", "Rose Goldfish", "Treasured Tilapia",
            "Gloopy Gar", "Glowberry Guppy", "Lemon Sole", "Mutated Marlin", "Overgrown Grouper", "Shardine",
            "Binding Jellyfish", "Butterfly Fish", "Emerald Jewelfish", "Jungle Clownfish", "Jungle Fangtooth", "Zebra Turkeyfish",
            "Alligator Gar", "Goliath Tigerfish", "Grave Gudgeon", "Green Terror Cichlid", "Mud Pike", "Mudfin Catfish",
            "Dorado", "Foxfish", "Mossy Mullet", "Stone Carp", "Stone Salmon", "Tarnished Goldfish",
            "Ashen Goliath", "Basalt Discus", "Flying Flashfish", "Heated Haddock", "Molten Goldfish", "Torch Tarpon"
    );

    public static int getFishRarity(String fishName) {
        if (COMMON_FISH.contains(fishName)) return 0;
        if (UNCOMMON_FISH.contains(fishName)) return 1;
        if (RARE_FISH.contains(fishName)) return 2;
        if (EPIC_FISH.contains(fishName)) return 3;
        if (LEGENDARY_FISH.contains(fishName)) return 4;
        if (MYTHIC_FISH.contains(fishName)) return 5;
        else return 5;
    }

    public static boolean isFish(String catchName) {
        return catchName.matches(".*[\uE09C\uE0A2\uE0A4\uE09E].*");
    }

    public static int getFishWeight(String catchName) {
        if (catchName.contains("\uE09C")) return 0;
        if (catchName.contains("\uE0A2")) return 1;
        if (catchName.contains("\uE0A4")) return 2;
        if (catchName.contains("\uE09E")) return 3;
        else return 3;
    }

    public static String extractFishName(String catchName) {
        return catchName.replaceAll("[^\\p{L} ]+", "").trim();
    }

    public static boolean isElusiveFish(String fishName) {
        return ELUSIVE_FISH.contains(fishName);
    }


}
