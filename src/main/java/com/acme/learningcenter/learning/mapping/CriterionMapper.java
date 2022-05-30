package com.acme.learningcenter.learning.mapping;

import com.acme.learningcenter.learning.domain.model.entity.Criterion;
import com.acme.learningcenter.learning.resource.CreateCriterionResource;
import com.acme.learningcenter.learning.resource.CriterionResource;
import com.acme.learningcenter.learning.resource.UpdateCriterionResource;
import com.acme.learningcenter.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class CriterionMapper implements Serializable {
  @Autowired
  EnhancedModelMapper mapper;

  // Object Mapping
  public CriterionResource toResource(Criterion model) {
    return mapper.map(model, CriterionResource.class);
  }

  public Page<CriterionResource> modelListPage(List<Criterion> modelList, Pageable pageable) {
    return new PageImpl<>(mapper.mapList(modelList, CriterionResource.class), pageable, modelList.size());
  }

  public Criterion toModel(CreateCriterionResource resource) {
    return mapper.map(resource, Criterion.class);
  }

  public Criterion toModel(UpdateCriterionResource resource) {
    return mapper.map(resource, Criterion.class);
  }
}
