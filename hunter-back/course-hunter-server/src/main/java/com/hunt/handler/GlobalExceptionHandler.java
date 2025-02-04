package com.hunt.handler;

import com.hunt.exception.BaseException;
import com.hunt.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result ex(BaseException ex) {
        log.error("error message：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result ex(SQLException ex) {
        return Result.error(ex.getMessage());
    }
}
