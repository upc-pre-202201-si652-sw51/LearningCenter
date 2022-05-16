package com.acme.learningcenter.learning.domain.service;

import com.acme.learningcenter.learning.domain.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
  List<Student> getAll();
  Page<Student> getAll(Pageable pageable);
  Student getById(Long studentId);
  Student create(Student student);
  Student update(Long studentId, Student request);
  ResponseEntity<?> delete(Long studentId);
}
