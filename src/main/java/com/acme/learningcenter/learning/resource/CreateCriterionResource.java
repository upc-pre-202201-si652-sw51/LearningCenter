package com.acme.learningcenter.learning.resource;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class CreateCriterionResource {
  @NotNull
  @NotBlank
  private String name;
}
