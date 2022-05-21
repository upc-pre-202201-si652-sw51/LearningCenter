package com.acme.learningcenter.learning.service;

import com.acme.learningcenter.learning.domain.model.entity.Student;
import com.acme.learningcenter.learning.domain.persistence.StudentRepository;
import com.acme.learningcenter.learning.domain.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

  private static final String ENTITY = "Student";

  private final StudentRepository studentRepository;

  public StudentServiceImpl(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public List<Student> getAll() {
    return studentRepository.findAll();
  }

  @Override
  public Page<Student> getAll(Pageable pageable) {
    return studentRepository.findAll(pageable);
  }

  @Override
  public Student getById(Long studentId) {
    return null;
  }

  @Override
  public Student create(Student student) {
    return null;
  }

  @Override
  public Student update(Long studentId, Student request) {
    return null;
  }

  @Override
  public ResponseEntity<?> delete(Long studentId) {
    return null;
  }
}
