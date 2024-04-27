package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MailConfig {
    private static final String CONFIG_FILE = "D:\\Work\\QuanLyChiTieuV3\\src\\main\\resources\\mailConfig.properties";

    public static Properties loadMailProperties() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        }
        return properties;
    }


}
