package com.github.lsjunior.sbk8stest.web.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.lsjunior.sbk8stest.Log;
import com.github.lsjunior.sbk8stest.web.utils.ResponseUtils;

@ControllerAdvice
public class SbK8sTestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  public SbK8sTestResponseEntityExceptionHandler() {
    super();
  }

  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public ResponseEntity<Object> handleThrowable(final Exception ex, final WebRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    return this.handleExceptionInternal(ex, null, new HttpHeaders(), status, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(final Exception ex, final Object body, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    Log.getLog().error(ex.getMessage());
    Log.getLog().debug(ex.getMessage(), ex);
    return ResponseUtils.error(ex);
  }

}
