package com.lefei.demo1.exception;

/**
 * @author le
 * date:    2020/12/25
 * describe：
 */
public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException(String message) {
        super(message);
    }
}
