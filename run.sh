#!/bin/bash

# JavaFX Image Manager Runner
# This script runs the JavaFX Image Manager with proper JavaFX module configuration

JAVAFX_PATH="$HOME/Downloads/javafx-sdk-24.0.1/lib"

# Check if JavaFX SDK exists
if [ ! -d "$JAVAFX_PATH" ]; then
    echo "Error: JavaFX SDK not found at $JAVAFX_PATH"
    echo "Please download JavaFX SDK and update the JAVAFX_PATH variable in this script"
    exit 1
fi

# Compile Java files
echo "Compiling Java files..."
javac --module-path "$JAVAFX_PATH" \
      --add-modules javafx.controls,javafx.fxml \
      -cp "lib/*" \
      -d . \
      src/*.java

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi
echo "Compilation successful!"

# Run the application
echo "Starting JavaFX Image Manager..."
java --module-path "$JAVAFX_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -cp ".:lib/*" \
     MainApp

# Clean up compiled class files
echo "Cleaning up compiled class files..."
rm -f *.class

echo "Application finished and class files cleaned up."
