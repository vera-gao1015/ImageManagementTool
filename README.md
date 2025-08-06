# JavaFX Image Manager

A JavaFX application for managing and viewing images with EXIF data support.

## Prerequisites

- Java 11 or higher
- JavaFX SDK (currently configured for ~/Downloads/javafx-sdk-24.0.1/lib)

## Quick Start

### Method 1: Using the Run Script (Easiest)
```bash
./run.sh
```

### Method 2: Using VS Code
1. Open `ImageManager.code-workspace` in VS Code
2. Press F5 or use Run > Start Debugging
3. Select "Run JavaFX Image Manager"

### Method 3: Manual Command Line
```bash
# Compile
javac --module-path ~/Downloads/javafx-sdk-24.0.1/lib \
      --add-modules javafx.controls,javafx.fxml \
      -cp "lib/*" \
      *.java

# Run
java --module-path ~/Downloads/javafx-sdk-24.0.1/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp ".:lib/*" \
     MainApp
```

## Configuration

If your JavaFX SDK is in a different location, update the path in:
- `run.sh` (line 5: `JAVAFX_PATH=...`)
- `ImageManager.code-workspace` (JavaFX path in settings and launch config)

## Features

- Image viewing and management
- EXIF data extraction and display
- Image format conversion
- Thumbnail generation
- GPS location data support

## Dependencies

Before running the application, download the required JAR files and place them in the `lib/` directory:

- [metadata-extractor-2.19.0.jar](https://github.com/drewnoakes/metadata-extractor/releases)
- [xmpcore-6.1.11.jar](https://mvnrepository.com/artifact/com.adobe.xmp/xmpcore/6.1.11)

Create a `lib/` directory in the project root and place these JAR files there.
