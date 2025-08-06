

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;



public class ImageManagerController {

    @FXML
    private TilePane imagePane;         // Container for image thumbnails

    @FXML
    private TextArea infoArea;          // Text area to display image properties

    private File currentSelectedFile;               // Currently selected image file
    private StackPane selectedContainer = null;          // Highlighted container of selected image
    private Stage loadingStage;                          // Loading dialog window

    private final AlertService alert = new BasicAlert(); // Alert message service


    // Handle Upload Image button click
    @FXML
    public void handleUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image Files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.bmp","*.gif")
        );

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                addImageThumbnail(file);
            }
        }
    }

    // Display image properties in the text area
    private void showImageInfo(File file) {
        currentSelectedFile = file;

        // ExifImageReader is a subclass of ImageInfoReader ----> Polymorphism
        ImageInfoReader reader = new ExifImageReader(file);
        String info = reader.getInfo();

        infoArea.setText(info);
    }

    // Get file extension for format conversion
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "png" : fileName.substring(dotIndex + 1).toLowerCase();
    }

    // Show loading window during image conversion
    private void showLoadingWindow() {
        Platform.runLater(() -> {
            Label label = new Label("Saving image...");
            ProgressIndicator spinner = new ProgressIndicator();
            VBox root = new VBox(10, spinner, label);
            root.setStyle("-fx-padding: 20; -fx-alignment: center;");

            Scene scene = new Scene(root, 200, 120);
            loadingStage = new Stage();
            loadingStage.setTitle("Please wait");
            loadingStage.setScene(scene);
            loadingStage.setResizable(false);
            loadingStage.initOwner(null);       
            loadingStage.setAlwaysOnTop(true);
            loadingStage.show();
        });
    }

    // Close loading window
    private void closeLoadingWindow() {
        Platform.runLater(() -> {
            if (loadingStage != null) {
                loadingStage.close();
                loadingStage = null;
            }
        });
    }

    // Handle Convert Format button click
    @FXML
    public void handleConvert() {
        if (currentSelectedFile == null) {
            alert.showWarning("No Image Selected","Please click an image first❗ ");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Converted Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PNG", "*.png"),
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
            new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );

        File targetFile = fileChooser.showSaveDialog(null);
        if (targetFile == null) return;

        // Automatically append extension if not present
        if (!targetFile.getName().contains(".")) {
            String selectedExt = fileChooser.getSelectedExtensionFilter().getExtensions().get(0); 
            String ext = selectedExt.replace("*", ""); 
            targetFile = new File(targetFile.getAbsolutePath() + ext);
        }

        final File finalTargetFile = targetFile;
        showLoadingWindow();        // Show loading window during task


        // Perform conversion in background thread
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    String format = getFileExtension(finalTargetFile.getName());
                    ConverterService converter = new BasicConverter(currentSelectedFile);
                    boolean result = converter.convert(finalTargetFile, format);

                    if (!result) {
                        throw new Exception("Image saving failed. Format may not be supported.");
                    }

                    Platform.runLater(() -> alert.showInfo( "Saved Successfully", "Image saved to:\n" + finalTargetFile.getAbsolutePath()));
                } catch (Exception e) {
                    Platform.runLater(() -> alert.showError( "Save Failed", e.getMessage()));
                } finally {
                    closeLoadingWindow();       // Close loading window after task
                }
                return null;
            }
        };

        new Thread(task).start();
    }

    // Create image thumbnail and attach single and double click behaviour
    private void addImageThumbnail(File file) {
        Image image = new Image(file.toURI().toString(), 100, 100, true, true);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setStyle("-fx-padding: 5; -fx-border-color: transparent; -fx-border-width: 2;-fx-border-radius: 4");

        Button deleteButton = new Button("Delete");
        VBox container = new VBox(imageContainer, deleteButton);
        container.setSpacing(5);
        container.setAlignment(Pos.CENTER);

        // Handle delete action
        deleteButton.setOnAction(e -> {
            imagePane.getChildren().remove(container);

            // if delete the current seletec file, clean the seleted mode
            if (currentSelectedFile != null && currentSelectedFile.equals(file)) {
                currentSelectedFile = null;
                selectedContainer = null;
                infoArea.clear();
            }
        });



        // Single click = show image info; Double click = show full image
        imageView.setOnMouseClicked(e -> {
            if (selectedContainer != null) {
                selectedContainer.setStyle("-fx-padding: 5; -fx-border-color: transparent;-fx-border-width: 2; -fx-border-radius: 4");
            }
            imageContainer.setStyle("-fx-padding: 5; -fx-border-color:rgb(152, 202, 242); -fx-border-width: 2; -fx-border-radius: 4;");
            selectedContainer = imageContainer;

            if (e.getClickCount() == 1) {
                showImageInfo(file);
            } else if (e.getClickCount() == 2) {
                showFullImage(file);
            }
        });

        imagePane.getChildren().add(container);
    }



    // Show full-size image in new popup window
    private void showFullImage(File file) {
        Image fullImage = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(fullImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);

        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle(file.getName());
        stage.setScene(scene);
        stage.setWidth(820);
        stage.setHeight(640);
        stage.show();
    }

    // Handle Exit button to exit app
    public void handleExit() {
        System.exit(0);
    }
}
