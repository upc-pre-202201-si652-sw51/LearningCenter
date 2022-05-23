package com.acme.learningcenter.learning.domain.service;

import com.acme.learningcenter.learning.domain.model.entity.Criterion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CriterionService {
  List<Criterion> getAllBySkillId(Long skillId);
  Page<Criterion> getAllBySkillId(Long skillId, Pageable pageable);
  Criterion create(Long skillId, Criterion criterion);
  Criterion update(Long skillId, Long criterionId, Criterion criterion);
  ResponseEntity<?> delete(Long skillId, Long criterionId);
}
