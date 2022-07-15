package com.github.lsjunior.sbk8stest.jpa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.github.lsjunior.sbk8stest.Constants;

@Entity
@Table(name = "tb_person")
public class Person implements Serializable {

  private static final long serialVersionUID = Constants.VERSION;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 100)
  @NotNull
  @NotBlank
  @Size(min = 3, max = 100)
  private String name;

  @Column(length = 100)
  @NotNull
  @NotBlank
  @Email
  @Size(min = 3, max = 100)
  private String email;

  @Column(length = 20)
  @Size(min = 7, max = 20)
  private String phone;

  public Person() {
    super();
  }

  public Long getId() {
    return this.id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(final String phone) {
    this.phone = phone;
  }

}
