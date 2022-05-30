package com.acme.learningcenter.learning.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateSkillResource {
  private Long id;

  @NotNull
  @NotBlank
  @Size(max = 60)
  private String name;
}
