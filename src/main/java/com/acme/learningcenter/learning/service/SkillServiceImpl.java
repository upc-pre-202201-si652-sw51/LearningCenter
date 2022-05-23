package com.acme.learningcenter.learning.service;

import com.acme.learningcenter.learning.domain.model.entity.Skill;
import com.acme.learningcenter.learning.domain.persistence.SkillRepository;
import com.acme.learningcenter.learning.domain.service.SkillService;
import com.acme.learningcenter.shared.exception.ResourceNotFoundException;
import com.acme.learningcenter.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SkillServiceImpl implements SkillService {

  private static final String ENTITY = "Skill";

  private final SkillRepository skillRepository;

  private final Validator validator;

  public SkillServiceImpl(SkillRepository skillRepository, Validator validator) {
    this.skillRepository = skillRepository;
    this.validator = validator;
  }

  @Override
  public List<Skill> getAll() {
    return skillRepository.findAll();
  }

  @Override
  public Page<Skill> getAll(Pageable pageable) {
    return skillRepository.findAll(pageable);
  }

  @Override
  public Skill getById(Long skillId) {
    return skillRepository.findById(skillId)
      .orElseThrow(() -> new ResourceNotFoundException(ENTITY, skillId));
  }

  @Override
  public Skill create(Skill skill) {
    Set<ConstraintViolation<Skill>> violations = validator.validate(skill);

    if(!violations.isEmpty())
      throw new ResourceValidationException(ENTITY, violations);

    Optional<Skill> skillWithName = skillRepository.findByName(skill.getName());

    if(skillWithName.isPresent())
      throw new ResourceValidationException(ENTITY, "An skill with the same name already exists.");

    return skillRepository.save(skill);
  }

  @Override
  public Skill update(Long skillId, Skill skill) {
    Set<ConstraintViolation<Skill>> violations = validator.validate(skill);

    if(!violations.isEmpty())
      throw new ResourceValidationException(ENTITY, violations);

    Optional<Skill> skillWithName = skillRepository.findByName(skill.getName());

    if(skillWithName.isPresent() && !skillWithName.get().getId().equals(skillId))
      throw new ResourceValidationException(ENTITY, "An skill with the same name already exists.");

    return skillRepository.findById(skillId).map(existingSkill ->
      skillRepository.save(existingSkill.withName(skill.getName())))
      .orElseThrow(() -> new ResourceNotFoundException(ENTITY, skillId));
  }

  @Override
  public ResponseEntity<?> delete(Long skillId) {
    return skillRepository.findById(skillId).map(skill -> {
      skillRepository.delete(skill);
      return ResponseEntity.ok().build();
    }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, skillId));
  }
}
