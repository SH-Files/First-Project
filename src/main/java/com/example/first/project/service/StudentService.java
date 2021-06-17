package com.example.first.project.service;

import java.util.Map;
import java.util.HashMap;
import org.hibernate.Hibernate;
import com.example.first.project.entity.Book;
import org.springframework.stereotype.Service;
import com.example.first.project.entity.Student;
import com.example.first.project.repository.StudentStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.example.first.project.exception.BookNotFoundException;
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
                Student student = entry.getValue();
                Hibernate.initialize(student.getBooks());
                return student;
            }
        }
        throw new StudentNotFoundException();
    }

    @Transactional(readOnly = true)
    public HashMap<Integer, Student> getStudents() {
        HashMap<Integer, Student> students = new HashMap<>();

        for (Student student : studentStorage.findAll()) {
            students.put(student.getId(), student);
        }
        return students;
    }

    @Transactional
    public void createStudent(String firstName, String lastName) {
        Student student = new Student(firstName, lastName);
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
    public void removeStudent(int studentKey) {
        Student student = getStudent(studentKey);
        studentStorage.delete(student);
    }

    @Transactional
    public void addBookToStudent(int studentKey, String title) {
        Student student = getStudent(studentKey);
        Book book = new Book(title);
        student.addBook(book);
        studentStorage.save(student);
    }

    @Transactional
    public void removeBookFromStudent(int studentKey, int bookKey) {
        Student student = getStudent(studentKey);
        student.removeBook(bookKey);
        studentStorage.save(student);
    }

    @Transactional
    public void updateBookTitle(int studentKeyBookBelongsTo, int bookKey, String newTitle) {
        Student student = getStudent(studentKeyBookBelongsTo);
        Book bookFromStudent = student.getBooks()
            .stream()
            .filter(book -> book.getId() == bookKey)
            .findFirst()
            .orElseThrow(BookNotFoundException::new);
        bookFromStudent.setTitle(newTitle);
        studentStorage.save(student);
    }
}