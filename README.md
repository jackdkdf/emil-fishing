# Emil Fishing

A Minecraft Forge mod that tracks fishing spots and displays optimum fish island locations on a HUD overlay.

## Features

- **Fishing Spot Tracking** — Detects and displays nearby fishing spots with their distance
- **Filterable Spots** — Filter by hook types, magnets, chances, and stability levels
- **Catch Statistics** — Tracks fish catches, elusive fish, pearls, spirits, treasures, and trash
- **Grotto Support** — Separate stats for grotto vs. island fishing
- **HUD Overlay** — Shows coordinates and fishing spot info on-screen
- **Configurable** — Toggle display options via the in-game config screen

## Requirements

- Minecraft 1.21.4
- Minecraft Forge 54.1.3+
- [Cloth Config](https://modrinth.com/mod/cloth-config) (included as dependency)

## Installation

1. Install [Minecraft Forge](https://files.minecraftforge.net/) for 1.21.4
2. Download the latest Emil Fishing JAR
3. Place the JAR in your `mods` folder
4. Launch Minecraft

## Usage

- Use `/emil` to open the mod configuration screen
- The HUD automatically displays when fishing spots are detected
- Configure filters in Mod Settings → Emil Fishing to show only the spots you care about

## Building

```bash
./gradlew build
```

The built JAR will be in `build/libs/`.

## Authors

JustPressPlay, Laiier

## License

All Rights Reserved
