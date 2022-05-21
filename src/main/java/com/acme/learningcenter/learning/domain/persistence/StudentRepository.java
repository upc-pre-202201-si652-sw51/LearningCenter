package com.acme.learningcenter.learning.domain.persistence;

import com.acme.learningcenter.learning.domain.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  Student findByName(String name);
  List<Student> findAllByAge(int age);
}
