package com.github.lsjunior.sbk8stest.web.model;

import java.io.Serializable;

import com.github.lsjunior.sbk8stest.Constants;

public class MessageRepresentation<T> implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  private Boolean status;

  private Integer code;

  private String message;

  private T content;

  public MessageRepresentation() {
    super();
  }

  public Boolean getStatus() {
    return this.status;
  }

  public void setStatus(final Boolean status) {
    this.status = status;
  }

  public Integer getCode() {
    return this.code;
  }

  public void setCode(final Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public T getContent() {
    return this.content;
  }

  public void setContent(final T content) {
    this.content = content;
  }

}
