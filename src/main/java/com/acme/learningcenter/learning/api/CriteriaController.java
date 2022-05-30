package com.acme.learningcenter.learning.api;

import com.acme.learningcenter.learning.domain.service.CriterionService;
import com.acme.learningcenter.learning.mapping.CriterionMapper;
import com.acme.learningcenter.learning.resource.CriterionResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/criteria")
public class CriteriaController {
  private final CriterionService criterionService;

  private final CriterionMapper mapper;


  public CriteriaController(CriterionService criterionService, CriterionMapper mapper) {
    this.criterionService = criterionService;
    this.mapper = mapper;
  }

  @GetMapping
  public Page<CriterionResource> getAllCriteria(Pageable pageable) {
    return mapper.modelListPage(criterionService.getAll(),pageable);
  }
}
