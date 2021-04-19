package com.example.first.project.service;

import java.util.HashMap;

import com.example.first.project.entity.Book;
import com.example.first.project.entity.Student;
import com.example.first.project.repository.DataMemoryStorage;
import com.example.first.project.exception.BookNotFoundException;
import com.example.first.project.exception.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final DataMemoryStorage dataMemoryStorage;

    @Autowired
    public StudentService(DataMemoryStorage dataMemoryStorage) {
        this.dataMemoryStorage = dataMemoryStorage;
    }

    public Student getStudent(String studentKey) {
        Student student = dataMemoryStorage.getStudent(studentKey);
        if (student == null) {
            throw new StudentNotFoundException();
        }
        return student;
    }

    public HashMap<String, Student> getStudents() {
        return dataMemoryStorage.getStudents();
    }

    public void createStudent(String firstName, String lastName) {
        Student student = new Student(firstName, lastName);
        dataMemoryStorage.addStudent(student);
    }

    public void updateStudentFirstName(String studentKey, String newFirstName) {
        Student student = getStudent(studentKey);
        student.setFirstName(newFirstName);
    }

    public void updateStudentLastName(String studentKey, String newLastName) {
        Student student = getStudent(studentKey);
        student.setLastName(newLastName);
    }

    public void removeStudent(String studentKey) {
        Student student = getStudent(studentKey);
        dataMemoryStorage.removeStudent(student.getId());
    }

    public void addBookToStudent(String studentKey, String title) {
        Student student = getStudent(studentKey);
        Book book = new Book(title);
        student.addBook(book);
    }

    public void removeBookFromStudent(String studentKey, String bookKey) {
    Student student = getStudent(studentKey);
    student.removeBook(bookKey);
    }

    public void updateBookTitle(String studentKeyBookBelongsTo, String bookKey, String newTitle) {
        Student student = getStudent(studentKeyBookBelongsTo);
        Book bookFromStudent = student.getBooks()
            .stream()
            .filter(book -> book.getId().equals(bookKey))
            .findFirst()
            .orElseThrow(BookNotFoundException::new);
        bookFromStudent.setTitle(newTitle);
    }
}
