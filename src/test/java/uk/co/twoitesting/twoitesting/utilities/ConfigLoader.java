// This is the folder (package) where this class is stored
package uk.co.twoitesting.twoitesting.utilities;

// Import tools to read files and handle exceptions
import java.io.FileInputStream; // Lets us read files from disk
import java.io.IOException;      // Handles errors if file reading fails
import java.util.Properties;    // Stores key-value pairs from a config file

// Define a class to load configuration values
public class ConfigLoader {

    // Create a Properties object to hold config values (like username, password, URLs)
    private static Properties properties = new Properties();

    // This block runs automatically when the class is first used
    static {
        try {
            // Open the config.properties file so we can read it
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");

            // Load all key-value pairs from the file into our properties object
            properties.load(fis);

        } catch (IOException e) { // If something goes wrong (file missing, etc.)
            e.printStackTrace(); // Print the error details to help debugging
            throw new RuntimeException("Failed to load config.properties");
            // Stop the program because we can't continue without the config
        }
    }

    // Method to get a value from the config file using its key
    public static String get(String key) {
        return properties.getProperty(key); // Return the value associated with the key
    }
}
