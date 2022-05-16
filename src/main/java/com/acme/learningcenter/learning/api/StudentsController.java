package com.acme.learningcenter.learning.api;

import com.acme.learningcenter.learning.domain.service.StudentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/students")
public class StudentsController {
  private final StudentService studentService;

  public StudentsController(StudentService studentService) {
    this.studentService = studentService;
  }
}
