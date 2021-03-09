package com.lefei.demo1.aop;

import com.lefei.demo1.exception.AbnormalAccessException;
import com.lefei.demo1.exception.PermissionDeniedException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author le
 * date:    2020/12/25
 * describe：
 */
@ControllerAdvice
public class GlobalExceptionAdvice {
    @Autowired
    Logger logger;

    @ExceptionHandler(value = PermissionDeniedException.class)
    public void permissionDeniedException(
            HttpServletResponse response,
            PermissionDeniedException permissionDeniedException)
            throws IOException {
        response.sendRedirect("index");
    }

    @ExceptionHandler(value = AbnormalAccessException.class)
    public void abnormalAccessException(
            HttpServletResponse response,
            AbnormalAccessException permissionDeniedException)
            throws IOException {
        response.sendRedirect("index");
    }

    @ExceptionHandler(value = Exception.class)
    public void illegalStateException(
            HttpServletResponse response,
            Exception exception)
            throws IOException {
        exception.printStackTrace();
        response.sendRedirect("index");
    }
}
