package com.hunt.result;

import lombok.Data;

import java.io.Serializable;

/**
 * BackEnd response result
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; //1 means success，0 means failure
    private String msg; // error message
    private T data; // response data

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }
    public static <T> Result<T> success(String msg) {
        Result<T> result = new Result<>();
        result.code = 1;
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> Result<T> success(T object, String msg) {
        Result<T> result = new Result<>();
        result.data = object;
        result.code = 1;
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}
