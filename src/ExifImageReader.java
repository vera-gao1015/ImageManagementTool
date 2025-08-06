

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.lang.GeoLocation;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.io.File;

//the subclass that extracts EXIF metadata information from the image

public class ExifImageReader extends ImageInfoReader {

    public ExifImageReader(File file) {
        super(file);
    }

    @Override
    public String getInfo() {
        // Start with basic info (name, path, size, height, width)
        String info = getBasicInfo();

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);

            // Read EXIF standard directory
            ExifIFD0Directory exif = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (exif != null) {
                String make = exif.getString(ExifIFD0Directory.TAG_MAKE);
                String model = exif.getString(ExifIFD0Directory.TAG_MODEL);
                String date = exif.getString(ExifIFD0Directory.TAG_DATETIME);

                if (make != null) info += "\nCamera: " + make;
                if (model != null) info += "\nModel: " + model;

                if (date != null) {
                    try {
                        SimpleDateFormat exifFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                        SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date parsedDate = exifFormat.parse(date);
                        info += "\nDate: " + displayFormat.format(parsedDate);
                    } catch (ParseException e) {
                        info += "\nDate: " + date;          // back to original if parsing fails
                    }
                }
            }

            // Read GPS information
            GpsDirectory gpsInfo = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            if (gpsInfo != null) {
                GeoLocation geoLocation = gpsInfo.getGeoLocation();
                if (geoLocation != null && !geoLocation.isZero()) {
                    info += String.format("\nLatitude: %.6f", geoLocation.getLatitude());
                    info += String.format("\nLongitude: %.6f", geoLocation.getLongitude());
                }
            }

        } catch (Exception e) {
            info += "\nFailed to read EXIF metadata.";
        }

        return info;
    }
}
