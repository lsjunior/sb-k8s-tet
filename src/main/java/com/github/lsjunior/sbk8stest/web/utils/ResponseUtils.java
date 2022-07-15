package com.github.lsjunior.sbk8stest.web.utils;

import java.io.IOException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import com.github.lsjunior.sbk8stest.web.model.MessageRepresentation;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.io.ByteSource;

public abstract class ResponseUtils {

  private ResponseUtils() {
    //
  }

  public static <T> ResponseEntity<MessageRepresentation<T>> okOrNotFound(final T content) {
    if (content == null) {
      return ResponseUtils.notFound();
    }
    MessageRepresentation<T> obj = new MessageRepresentation<>();
    obj.setCode(Integer.valueOf(HttpStatus.OK.value()));
    obj.setContent(content);
    obj.setStatus(Boolean.TRUE);
    return ResponseEntity.status(HttpStatus.OK).body(obj);
  }

  public static ResponseEntity<MessageRepresentation<String>> ok() {
    MessageRepresentation<String> obj = new MessageRepresentation<>();
    obj.setCode(Integer.valueOf(HttpStatus.OK.value()));
    obj.setStatus(Boolean.TRUE);
    return ResponseEntity.status(HttpStatus.OK).body(obj);
  }

  public static <T> ResponseEntity<MessageRepresentation<T>> ok(final T content) {
    MessageRepresentation<T> obj = new MessageRepresentation<>();
    obj.setCode(Integer.valueOf(HttpStatus.OK.value()));
    obj.setContent(content);
    obj.setStatus(Boolean.TRUE);
    return ResponseEntity.status(HttpStatus.OK).body(obj);
  }

  public static ResponseEntity<InputStreamResource> download(final ByteSource bytes, final String fileName, final String contentType, boolean inline) throws IOException {
    return ResponseUtils.download(bytes, fileName, MediaType.valueOf(contentType), inline);
  }

  public static ResponseEntity<InputStreamResource> download(final ByteSource bytes, final String fileName, final MediaType contentType, boolean inline) throws IOException {
    String mode = inline ? "inline" : "attachment";
    InputStreamResource resource = new InputStreamResource(bytes.openStream());
    return ResponseEntity.ok().header("Content-Disposition", mode + "; filename=" + fileName).header("Content-Size", Long.valueOf(bytes.size()).toString()).contentType(contentType).body(resource);
  }

  public static ResponseEntity<Object> error(final Exception e) {
    Throwable cause = Throwables.getRootCause(e);
    String message = cause.getMessage();
    if (Strings.isNullOrEmpty(message)) {
      message = Throwables.getRootCause(e).getClass().getName();
    }

    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    if (e instanceof HttpStatusCodeException) {
      httpStatus = ((HttpStatusCodeException) e).getStatusCode();
    } else if (cause instanceof HttpStatusCodeException) {
      httpStatus = ((HttpStatusCodeException) cause).getStatusCode();
    } else if (cause instanceof ConstraintViolationException) {
      ConstraintViolationException cve = (ConstraintViolationException) cause;
      StringBuilder builder = new StringBuilder();
      for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
        if (builder.length() > 0) {
          builder.append(", ");
        }
        String cvProperty = cv.getPropertyPath().toString();
        String cvMessage = cv.getMessage();

        if (cvProperty.contains(".")) {
          cvProperty = cvProperty.substring(cvProperty.lastIndexOf(".") + 1);
        }

        builder.append("'" + cvProperty + "' " + cvMessage);
      }
      message = builder.toString();
    }

    MessageRepresentation<?> obj = new MessageRepresentation<>();
    obj.setCode(Integer.valueOf(httpStatus.value()));
    obj.setMessage(message);
    obj.setStatus(Boolean.FALSE);
    return ResponseEntity.status(httpStatus).body(obj);
  }

  public static <T> ResponseEntity<MessageRepresentation<T>> notFound() {
    MessageRepresentation<T> message = new MessageRepresentation<>();
    message.setCode(Integer.valueOf(HttpStatus.NOT_FOUND.value()));
    message.setMessage("Not found");
    message.setStatus(Boolean.FALSE);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
  }

}
