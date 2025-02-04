package com.hunt.utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Map;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Base64Util {
    public static String encodeMap(Map<String, Object> data) {
        String json = new Gson().toJson(data);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        return Base64.getUrlEncoder().encodeToString(bytes);
    }

    public static Map<String, Object> decodeMap(String encodedString) {
        byte[] bytes = Base64.getUrlDecoder().decode(encodedString);
        String json = new String(bytes, StandardCharsets.UTF_8);

        // use the TypeToken class provided by Gson to get the type of the Map<String, Object> class
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> result = new Gson().fromJson(json, type);
        log.info("Decoded map: {}", result);
        return result;
    }
}
