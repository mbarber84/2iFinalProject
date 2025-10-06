// This is the folder (package) where this class is stored
package uk.co.twoitesting.twoitesting.utilities;

// Define a record class to store information about a coupon
// A record is a simple, immutable class that automatically gives you a constructor and getters
public record CouponData(String key, String code, double discount) {

    // Override the default toString() method to control how this object is printed
    @Override
    public String toString() {
        // Return a readable string showing the coupon key, the code, and the discount
        return key + " (" + code + " : " + discount + ")";
    }
}
