package com.example.first.project.service;

import java.util.HashMap;
import com.example.first.project.entity.Book;
import org.springframework.stereotype.Service;
import com.example.first.project.entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.first.project.repository.DataMemoryStorage;
import com.example.first.project.exception.BookNotFoundException;
import com.example.first.project.exception.StudentNotFoundException;

@Service
public class StudentService {

    @Autowired
    private DataMemoryStorage dataMemoryStorage;

    @Autowired
    private ApplicationContext context;

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
        Student student = (Student) context.getBean("student", firstName, lastName);
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
        Book book = (Book) context.getBean("book", title);
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