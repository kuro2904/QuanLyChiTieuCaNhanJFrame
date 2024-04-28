package config;

import java.io.IOException;
import java.util.Properties;

public class MailConfig {
    private final String CONFIG_FILE = "/mailConfig.properties"; // Note the leading slash

    public Properties loadMailProperties() throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(MailConfig.class.getResourceAsStream(CONFIG_FILE));
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Re-throw the exception to handle it elsewhere if needed
        }
        return properties;
    }
}
