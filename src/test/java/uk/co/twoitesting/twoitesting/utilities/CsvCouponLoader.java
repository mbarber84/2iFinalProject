// This is the folder (package) where this class is stored
package uk.co.twoitesting.twoitesting.utilities;

// Import classes to read files line by line
import java.io.BufferedReader; // Reads text from a file efficiently
import java.io.FileReader;     // Reads bytes from a file and converts to characters
import java.io.IOException;    // Handles errors if reading fails
import java.util.ArrayList;    // List that can grow in size
import java.util.List;         // Interface for lists

// Define a class to load coupon data from a CSV file
public class CsvCouponLoader {

    // Method to load coupons from a CSV file
    public static List<CouponData> loadCoupons(String filePath) {
        // Create an empty list to store coupons
        List<CouponData> coupons = new ArrayList<>();

        // Try-with-resources ensures the file is closed automatically
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;                 // To store each line of the file
            boolean firstLine = true;    // Flag to skip the header row (first line)

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                if (firstLine) {         // Skip the header line
                    firstLine = false;
                    continue;            // Skip to the next line
                }

                // Split the line into parts using comma as separator
                String[] parts = line.split(",");
                // Make sure the line has exactly 3 columns: key, code, discount
                if (parts.length == 3) {
                    String key = parts[0].trim();                       // Coupon name
                    String code = parts[1].trim();                      // Coupon code
                    double discount = Double.parseDouble(parts[2].trim()); // Discount value as number

                    // Create a new CouponData object and add it to the list
                    coupons.add(new CouponData(key, code, discount));
                }
            }
        } catch (IOException e) { // If the file cannot be read
            throw new RuntimeException("Failed to load coupons from CSV", e); // Stop program and show error
        }

        // Return the list of coupons that were loaded
        return coupons;
    }
}
