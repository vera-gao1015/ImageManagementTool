

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.io.File;

// Implementation of ConverterService using ImageIO. 
// Convert images to PNG, JPG, JPEG, and BMP.

public class BasicConverter implements ConverterService {

    private final File sourceFile;

    public BasicConverter(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    //Converts the image to the given format and saves it to the target file.

    @Override
    public boolean convert(File targetFile, String format) throws Exception {
        BufferedImage original = ImageIO.read(sourceFile);
        if (original == null) {
            throw new Exception("Failed to read image.");
        }

        if (format.equalsIgnoreCase("gif")) {
            throw new Exception("GIF format is not supported for output. Please choose PNG or JPG.");
        }

        BufferedImage imageToSave;

        // Only convert GIF to RGB if image uses indexed color
        ColorModel colorModel = original.getColorModel();
        if (colorModel instanceof IndexColorModel) {
            imageToSave = new BufferedImage(
                    original.getWidth(),
                    original.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            imageToSave.getGraphics().drawImage(original, 0, 0, null);
        } else {
            imageToSave = original;
        }

        boolean result = ImageIO.write(imageToSave, format, targetFile);
        if (!result) {
            throw new Exception("Image saving failed. Format may not be supported.");
        }

        return true;
    }
}
