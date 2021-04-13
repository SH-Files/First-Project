package com.example.first.project.repository;

import com.example.first.project.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class DataMemoryStorage {
    private final HashMap<String, Student> students;

    public DataMemoryStorage() {
        students = new HashMap<>();
    }

    public HashMap<String, Student> getStudents() {
        return students;
    }

    public Student getStudent(String key) {
        return students.get(key);
    }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    public void removeStudent(String key) {
        students.remove(key);
    }
}