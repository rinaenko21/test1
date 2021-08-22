package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    static Properties properties;
    static final String FILE_PATH;

    static {
        FILE_PATH = "src/test/resources/configurations.properties";
        FileInputStream input;

        try {
            input = new FileInputStream(FILE_PATH);
            properties = new Properties();
            properties.load(input);
            input.close();

        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace ();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key).trim();
    }

}
