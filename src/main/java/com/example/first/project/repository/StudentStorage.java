package com.example.first.project.repository;

import com.example.first.project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentStorage extends JpaRepository<Student, Integer> { }