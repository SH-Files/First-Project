package com.example.first.project.service;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import org.hibernate.Hibernate;
import com.example.first.project.entity.Book;
import org.springframework.stereotype.Service;
import com.example.first.project.entity.Student;
import com.example.first.project.repository.StudentStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.example.first.project.exception.StudentNotFoundException;

@Service
public class StudentService {

    private final StudentStorage studentStorage;

    @Autowired
    public StudentService(StudentStorage studentStorage) {
        this.studentStorage = studentStorage;
    }

    @Transactional(readOnly = true)
    public Student getStudent(int studentKey) {
        for (Map.Entry<Integer, Student> entry : getStudents().entrySet()) {
            if (entry.getValue().getId() == studentKey) {
                return entry.getValue();
            }
        }
        throw new StudentNotFoundException();
    }

    @Transactional(readOnly = true)
    public HashMap<Integer, Student> getStudents() {
        HashMap<Integer, Student> students = new HashMap<>();

        for (Student student : studentStorage.findAll()) {
            Hibernate.initialize(student.getBooks());
            students.put(student.getId(), student);
        }
        return students;
    }

    @Transactional
    public void saveStudent(Student student) {
        studentStorage.save(student);
    }

    @Transactional
    public void updateStudentFirstName(int studentKey, String newFirstName) {
        Student student = getStudent(studentKey);
        student.setFirstName(newFirstName);
        studentStorage.save(student);
    }

    @Transactional
    public void updateStudentLastName(int studentKey, String newLastName) {
        Student student = getStudent(studentKey);
        student.setLastName(newLastName);
        studentStorage.save(student);
    }

    @Transactional
    public void updateStudentBooks(int studentKey, Set<Book> newBooks) {
        Student student = getStudent(studentKey);
        student.setBooks(newBooks);
        studentStorage.save(student);
    }

    @Transactional
    public void addBookToStudent(int studentKey, String title) {
        Student student = getStudent(studentKey);
        Book book = new Book(title);
        student.addBook(book);
        studentStorage.save(student);
    }

    @Transactional
    public void removeStudent(int studentKey) {
        Student student = getStudent(studentKey);
        studentStorage.delete(student);
    }
}