package com.acme.learningcenter.learning.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("learningMappingConfiguration")
public class MappingConfiguration {
  @Bean
  public StudentMapper studentMapper() {
    return new StudentMapper();
  }

}
