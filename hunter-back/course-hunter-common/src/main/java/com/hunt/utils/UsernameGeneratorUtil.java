package com.hunt.utils;

/**
 * For generating username according to user email address.
 */
public class UsernameGeneratorUtil {

    public static String generateUniqueUsername(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        int atIndex = email.indexOf('@');
        if (atIndex <= 0) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }

        String prefix = email.substring(0, atIndex); // Get email prefix
        String suffix = email.substring(atIndex + 1, email.lastIndexOf('.')); // Get domain name before dot

        String uniqueName = prefix + '_' + suffix; // combine prefix and domain as unique username

        return uniqueName;
    }
}
