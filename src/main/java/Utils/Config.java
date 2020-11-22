package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    Properties configFile;

    public Config() {
        configFile = new Properties();
        try {
            InputStream in = getClass().getResourceAsStream("/config.properties");
            configFile.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return this.configFile.getProperty(key);
    }
}
