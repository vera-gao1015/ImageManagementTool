# JavaFX Image Manager

A comprehensive standalone JavaFX application for managing and viewing images with advanced EXIF data extraction and format conversion capabilities.

## Features

- **Image Management**: Load, view, and organize multiple image formats (JPEG, PNG, GIF, BMP)
- **EXIF Data Extraction**: Display detailed metadata including camera settings, GPS coordinates, and technical specifications
- **Format Conversion**: Convert images between PNG, JPG, JPEG, and BMP formats
- **Thumbnail Generation**: Automatic thumbnail creation for efficient browsing
- **GPS Support**: Extract and display location data from images
- **Asynchronous Processing**: Non-blocking UI operations for responsive user experience

## Prerequisites

- **Java 11 or higher**
- **JavaFX SDK** (currently configured for `~/Downloads/javafx-sdk-24.0.1/lib`)
- **Required Dependencies** (see Dependencies section below)

## Dependencies

Before running the application, download the required JAR files and place them in a `lib/` directory:

1. **Create lib directory**: `mkdir lib`
2. **Download dependencies**:
   - [metadata-extractor-2.19.0.jar](https://github.com/drewnoakes/metadata-extractor/releases)
   - [xmpcore-6.1.11.jar](https://mvnrepository.com/artifact/com.adobe.xmp/xmpcore/6.1.11)
3. **Place JAR files** in the `lib/` directory

## Quick Start

### Method 1: Using the Run Script (Recommended)
```bash
./run.sh
```
*Automatically compiles, runs, and cleans up class files*

### Method 2: Using VS Code
1. Open `ImageManager.code-workspace` in VS Code
2. Press `F5` or use Run > Start Debugging
3. Select "Run JavaFX Image Manager"

### Method 3: Manual Command Line
```bash
# Compile
javac --module-path ~/Downloads/javafx-sdk-24.0.1/lib \
      --add-modules javafx.controls,javafx.fxml \
      -cp "lib/*" \
      -d . \
      src/*.java

# Run
java --module-path ~/Downloads/javafx-sdk-24.0.1/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp ".:lib/*" \
     MainApp

# Clean up (optional)
rm *.class
```

## Configuration

If your JavaFX SDK is in a different location, update the path in:
- `run.sh` (line 5: `JAVAFX_PATH=...`)
- `.vscode/launch.json` (vmArgs section)
- `ImageManager.code-workspace` (JavaFX path in settings and launch config)

## Project Structure

```
MyImageManager/
├── src/
│   ├── MainApp.java              # Application entry point
│   ├── ImageManagerController.java # Main UI controller
│   ├── ExifImageReader.java      # EXIF metadata extraction
│   ├── BasicConverter.java       # Image format conversion
│   └── ...                       # Other service classes
├── Main.fxml                     # JavaFX UI layout
├── run.sh                        # Automated build/run script
├── .vscode/                      # VS Code configuration
│   ├── launch.json
│   └── settings.json
└── lib/                          # Dependencies (user-provided)
    ├── metadata-extractor-2.19.0.jar
    └── xmpcore-6.1.11.jar
```

## Architecture

- **MVC Pattern**: Separation of concerns with dedicated Model, View, and Controller components
- **Service Layer**: Modular services for image processing, conversion, and metadata extraction
- **Asynchronous Operations**: JavaFX Task API for non-blocking file operations
- **Interface-Based Design**: Extensible architecture with service interfaces

## Technical Stack

- **Language**: Java 11+
- **UI Framework**: JavaFX with FXML
- **Build Tools**: Shell scripting, JavaFX modules
- **Libraries**: metadata-extractor, XMP Core
- **IDE Support**: VS Code with Java extensions

## Troubleshooting

### Common Issues

1. **"JavaFX runtime components are missing"**
   - Ensure JavaFX SDK path is correct in configuration files
   - Use the provided run script or VS Code launch configuration

2. **"NoClassDefFoundError: Stage"**
   - Missing JavaFX modules in classpath
   - Run with proper `--module-path` and `--add-modules` arguments

3. **"metadata-extractor not found"**
   - Download required JAR files to `lib/` directory
   - Check classpath includes `lib/*`

### Performance Tips

- Use thumbnail view for browsing large image collections
- EXIF extraction is performed asynchronously to maintain UI responsiveness
- Large image files are processed in background threads

## License

This project is for educational and portfolio purposes.

## Contributing

This is a personal portfolio project. Feel free to fork and modify for your own use.

---

*Built with JavaFX • Demonstrates MVC architecture, asynchronous programming, and third-party library integration*
