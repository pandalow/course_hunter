package com.hunt.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtils {

    private static final Gson gson;

    // Static block to initialize the Gson instance
    static {
        gson = new GsonBuilder().create();
    }

    /**
     * Convert an object to a JSON string.
     *
     * @param src the object to be converted to JSON
     * @return the JSON string representation of the object
     * <p>
     * Example usage:
     * <pre>{@code
     * MyClass object = new MyClass();
     * String json = GsonUtils.toJson(object);
     * }</pre>
     */
    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    /**
     * Convert a JSON string to an object of the specified class.
     *
     * @param json     the JSON string
     * @param classOfT the class of the desired object
     * @param <T>      the type of the desired object
     * @return an object of type T from the JSON string
     * <p>
     * Example usage:
     * <pre>{@code
     * String jsonString = "{\"name\":\"John\", \"age\":30}";
     * MyClass object = GsonUtils.fromJson(jsonString, MyClass.class);
     * }</pre>
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /**
     * Convert a JSON string to an object of the specified type.
     *
     * @param json    the JSON string
     * @param typeOfT the type of the desired object
     * @param <T>     the type of the desired object
     * @return an object of type T from the JSON string
     * <p>
     * Example usage:
     * <pre>{@code
     * String jsonString = "[{\"name\":\"John\", \"age\":30}, {\"name\":\"Jane\", \"age\":25}]";
     * Type listType = new TypeToken<List<MyClass>>() {}.getType();
     * List<MyClass> list = GsonUtils.fromJson(jsonString, listType);
     * }</pre>
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    /**
     * Convert a JSON string to a list of objects of the specified type.
     *
     * @param json    the JSON string
     * @param typeOfT the type of the desired list of objects
     * @param <T>     the type of the desired objects
     * @return a list of objects of type T from the JSON string
     * <p>
     * Example usage:
     * <pre>{@code
     * String jsonString = "[{\"name\":\"John\", \"age\":30}, {\"name\":\"Jane\", \"age\":25}]";
     * Type listType = new TypeToken<List<MyClass>>() {}.getType();
     * List<MyClass> list = GsonUtils.fromJsonToList(jsonString, listType);
     * }</pre>
     */
    public static <T> List<T> fromJsonToList(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    /**
     * Convert an object to a pretty-printed JSON string.
     *
     * @param src the object to be converted to pretty-printed JSON
     * @return the pretty-printed JSON string representation of the object
     * <p>
     * Example usage:
     * <pre>{@code
     * MyClass object = new MyClass();
     * String prettyJson = GsonUtils.toPrettyJson(object);
     * }</pre>
     */
    public static String toPrettyJson(Object src) {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        return prettyGson.toJson(src);
    }
}

