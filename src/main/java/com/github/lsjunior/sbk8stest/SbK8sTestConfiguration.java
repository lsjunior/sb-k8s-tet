package com.github.lsjunior.sbk8stest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = {"com.github.lsjunior.sbk8stest"})
@EnableWebMvc
public class SbK8sTestConfiguration {

  public SbK8sTestConfiguration() {
    super();
  }

}
