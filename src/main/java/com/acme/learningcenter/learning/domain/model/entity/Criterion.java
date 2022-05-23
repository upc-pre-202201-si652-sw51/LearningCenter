package com.acme.learningcenter.learning.domain.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "criteria")
public class Criterion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @NotBlank
  private String name;

  // Relationships
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "skill_id", nullable = false)
  private Skill skill;
}
