package com.hunt.constant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InterceptorExcludePathConstant {
    private static final String[] SWAGGER_PATHS ={
            "/swagger**/**",
            "/swagger-ui/**",
            "/doc.html",
            "/v3/**",
            "/webjars/**"
    };

    // User related paths
    private static final String[] USER_PATHS = {
            "/user/register",
            "/user/login",
            "/user/verify/**"
    };
    // HomePage Course relate paths
    private static final String[] COURSE_PATHS ={
            "/course/**",
            "/filter/**",
            "/test/**"
    };

    /**
     * Method to concatenate all path arrays into one using reflection.
     *
     * @return a single array containing all paths from all public static final String[] fields.
     */
    public static String[] getExcludePaths() {
        List<String> combinedPaths = new ArrayList<>();

        // Get all fields of this class
        Field[] fields = InterceptorExcludePathConstant.class.getDeclaredFields();

        for (Field field : fields) {
            if (field.getType().isArray() && field.getType().getComponentType() == String.class) {
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) && java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
                    try {
                        String[] paths = (String[]) field.get(null); // Get the value of the static field
                        for (String path : paths) {
                            combinedPaths.add(path);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace(); // Handle the exception as needed
                    }
                }
            }
        }

        return combinedPaths.toArray(new String[0]);
    }

}
