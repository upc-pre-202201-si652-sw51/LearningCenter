package com.acme.learningcenter.learning.api;

import com.acme.learningcenter.learning.domain.service.CriterionService;
import com.acme.learningcenter.learning.mapping.CriterionMapper;
import com.acme.learningcenter.learning.resource.CreateCriterionResource;
import com.acme.learningcenter.learning.resource.CriterionResource;
import com.acme.learningcenter.learning.resource.UpdateCriterionResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/skills/{skillId}/criteria")
public class SkillCriteriaController {

  private final CriterionService criterionService;

  private final CriterionMapper mapper;


  public SkillCriteriaController(CriterionService criterionService, CriterionMapper mapper) {
    this.criterionService = criterionService;
    this.mapper = mapper;
  }

  @GetMapping
  public Page<CriterionResource> getAllCriteriaBySkillId(@PathVariable Long skillId, Pageable pageable) {
    return mapper.modelListPage(criterionService.getAllBySkillId(skillId), pageable);
  }

  @PostMapping
  public CriterionResource createCriterion(@PathVariable Long skillId,
                                           @RequestBody CreateCriterionResource resource) {
    return mapper.toResource(criterionService.create(skillId, mapper.toModel(resource)));
  }

  @PutMapping("{criterionId}")
  public CriterionResource updateCriterion(@PathVariable Long skillId,
                                           @PathVariable Long criterionId,
                                           @RequestBody UpdateCriterionResource resource) {
    return mapper.toResource(criterionService.update(skillId, criterionId, mapper.toModel(resource)));
  }

  @DeleteMapping("{criterionId}")
  public ResponseEntity<?> deleteCriterion(@PathVariable Long skillId,
                                           @PathVariable Long criterionId) {
    return criterionService.delete(skillId, criterionId);
  }
}
