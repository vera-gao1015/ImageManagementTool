

import java.io.File;

public interface ConverterService {
    boolean convert(File targetFile, String format) throws Exception;
    
}
