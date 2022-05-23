package com.acme.learningcenter.learning.service;

import com.acme.learningcenter.learning.domain.model.entity.Criterion;
import com.acme.learningcenter.learning.domain.persistence.CriterionRepository;
import com.acme.learningcenter.learning.domain.persistence.SkillRepository;
import com.acme.learningcenter.learning.domain.service.CriterionService;
import com.acme.learningcenter.shared.exception.ResourceNotFoundException;
import com.acme.learningcenter.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.List;
import java.util.Set;

@Service
public class CriterionServiceImpl implements CriterionService {

  private static final String ENTITY = "Criterion";

  private final CriterionRepository criterionRepository;

  private final Validator validator;

  private final SkillRepository skillRepository;

  public CriterionServiceImpl(CriterionRepository criterionRepository, Validator validator, SkillRepository skillRepository) {
    this.criterionRepository = criterionRepository;
    this.validator = validator;
    this.skillRepository = skillRepository;
  }


  @Override
  public List<Criterion> getAllBySkillId(Long skillId) {
    return criterionRepository.findBySkillId(skillId);
  }

  @Override
  public Page<Criterion> getAllBySkillId(Long skillId, Pageable pageable) {
    return criterionRepository.findBySkillId(skillId, pageable);
  }

  @Override
  public Criterion create(Long skillId, Criterion criterion) {
    Set<ConstraintViolation<Criterion>> violations = validator.validate(criterion);

    if (!violations.isEmpty())
      throw new ResourceValidationException(ENTITY, violations);

    return skillRepository.findById(skillId).map(skill -> {
      criterion.setSkill(skill);
      return criterionRepository.save(criterion);
    }).orElseThrow(() -> new ResourceNotFoundException("Skill", skillId));
  }

  @Override
  public Criterion update(Long skillId, Long criterionId, Criterion criterion) {
    Set<ConstraintViolation<Criterion>> violations = validator.validate(criterion);

    if (!violations.isEmpty())
      throw new ResourceValidationException(ENTITY, violations);

    if(!skillRepository.existsById(skillId))
      throw new ResourceNotFoundException("Skill", skillId);

    return criterionRepository.findById(criterionId).map(existingCriterion ->
      criterionRepository.save(existingCriterion.withName(criterion.getName())))
      .orElseThrow(() -> new ResourceNotFoundException("Skill", skillId));
  }

  @Override
  public ResponseEntity<?> delete(Long skillId, Long criterionId) {
    return criterionRepository.findByIdAndSkillId(criterionId, skillId).map(criterion -> {
      criterionRepository.delete(criterion);
      return ResponseEntity.ok().build();
    }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, criterionId));
  }
}
