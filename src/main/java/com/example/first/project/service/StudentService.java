package com.example.first.project.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.example.first.project.entity.Book;
import com.example.first.project.entity.Student;
import com.example.first.project.repository.StudentStorage;
import com.example.first.project.exception.BookNotFoundException;
import com.example.first.project.exception.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentStorage studentStorage;

    @Autowired
    public StudentService(StudentStorage studentStorage) {
        this.studentStorage = studentStorage;
    }

    public Student getStudent(String studentKey) {
        for (Map.Entry<String, Student> entry : getStudents().entrySet()) {
            if (entry.getValue().getId().equals(studentKey)) {
                return entry.getValue();
            }
        }
        throw new StudentNotFoundException();
    }

    public HashMap<String, Student> getStudents() {
        HashMap<String, Student> students = new HashMap<>();

        for (Student student : studentStorage.findAll()) {
            students.put(student.getId(), student);
        }
        return students;
    }

    public void createStudent(String firstName, String lastName) {
        Student student = new Student(firstName, lastName);
        studentStorage.save(student);
    }

    public void updateStudentFirstName(String studentKey, String newFirstName) {
        Student student = getStudent(studentKey);
        student.setFirstName(newFirstName);
        studentStorage.save(student);
    }

    public void updateStudentLastName(String studentKey, String newLastName) {
        Student student = getStudent(studentKey);
        student.setLastName(newLastName);
        studentStorage.save(student);
    }

    public void removeStudent(String studentKey) {
        Student student = getStudent(studentKey);
        studentStorage.delete(student);
    }

    public void addBookToStudent(String studentKey, String title) {
        Student student = getStudent(studentKey);
        Book book = new Book(title);
        student.addBook(book);
        studentStorage.save(student);
    }

    public void removeBookFromStudent(String studentKey, String bookKey) {
        Student student = getStudent(studentKey);
        student.removeBook(bookKey);
        studentStorage.save(student);
    }

    public void updateBookTitle(String studentKeyBookBelongsTo, String bookKey, String newTitle) {
        Student student = getStudent(studentKeyBookBelongsTo);
        Book bookFromStudent = student.getBooks()
            .stream()
            .filter(book -> book.getId().equals(bookKey))
            .findFirst()
            .orElseThrow(BookNotFoundException::new);
        bookFromStudent.setTitle(newTitle);
        studentStorage.save(student);
    }
}