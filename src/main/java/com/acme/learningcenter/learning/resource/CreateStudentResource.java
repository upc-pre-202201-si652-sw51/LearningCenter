package com.acme.learningcenter.learning.resource;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentResource {
  @NotNull
  @NotBlank
  @Size(max = 60)
  private String name;

  private int age;

  @Size(max = 240)
  private String address;
}
