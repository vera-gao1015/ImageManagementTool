

import javafx.scene.image.Image;
import java.io.File;

/**
 * Abstract base class for reading basic image information.
 */
public abstract class ImageInfoReader {
    protected File file;

    public ImageInfoReader(File file) {
        this.file = file;
    }

    //Return full information string (basic + extended info).
    public abstract String getInfo();

    //Return basic information: name, path, size, height, width.
    protected String getBasicInfo() {
        Image image = new Image(file.toURI().toString());
        return "Name: " + file.getName() +
               "\nPath: " + file.getAbsolutePath() +
               "\nSize: " + file.length() / 1024 + " KB" +
               "\nHeight: " + (int) image.getHeight() + " px" +
               "\nWidth: " + (int) image.getWidth() + " px";
    }
}
