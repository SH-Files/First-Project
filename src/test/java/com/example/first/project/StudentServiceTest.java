package com.example.first.project;

import com.example.first.project.entity.Book;
import com.example.first.project.entity.Student;
import com.example.first.project.exception.StudentNotFoundException;
import com.example.first.project.repository.StudentStorage;
import com.example.first.project.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {

    @Mock
    private StudentStorage studentStorage;

    private final StudentService studentService;

    StudentServiceTest() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentService(studentStorage);
    }

    @Test
    void createStudentTest() {
        Student student = new Student("SFN", "SLN");
        studentService.createStudent(student.getFirstName(), student.getLastName());
        Mockito.verify(studentStorage).save(student);
    }

    @Test
    void getStudentsTest() {
        studentService.getStudents();
        Mockito.verify(studentStorage).findAll();
    }

    @Test
    void getStudentTest() {
        Exception exception = assertThrows(StudentNotFoundException.class, () -> {
            Student student = new Student("SFN", "SLN");
            Mockito.when(studentStorage.findAll()).thenReturn(Collections.singletonList(student));
            studentService.getStudent(1);
        });
        assertNull(exception.getMessage());
    }

    @Test
    void updateStudentFirstNameTest() {
        Student student = new Student("SFN", "SLN");
        Mockito.when(studentStorage.findAll()).thenReturn(Collections.singletonList(student));
        studentService.updateStudentFirstName(student.getId(), "USFN");
        Mockito.verify(studentStorage).save(student);
    }

    @Test
    void updateStudentLastNameTest() {
        Student student = new Student("SFN", "SLN");
        Mockito.when(studentStorage.findAll()).thenReturn(Collections.singletonList(student));
        studentService.updateStudentLastName(student.getId(), "USLN");
        Mockito.verify(studentStorage).save(student);
    }

    @Test
    void removeStudentTest() {
        Student student = new Student("SFN", "SLN");
        Mockito.when(studentStorage.findAll()).thenReturn(Collections.singletonList(student));
        studentService.removeStudent(student.getId());
        Mockito.verify(studentStorage).delete(student);
    }

    @Test
    void addBookToStudentTest() {
        Student student = new Student("SFN", "SLN");
        Mockito.when(studentStorage.findAll()).thenReturn(Collections.singletonList(student));
        studentService.addBookToStudent(student.getId(), "Sample book!");
        Mockito.verify(studentStorage).save(student);
    }

    @Test
    void removeBookToStudentTest() {
        Book book = new Book("Sample book!");
        Student student = new Student("SFN", "SLN");
        student.addBook(book);
        Mockito.when(studentStorage.findAll()).thenReturn(Collections.singletonList(student));
        studentService.removeBookFromStudent(student.getId(), book.getId());
        Mockito.verify(studentStorage).save(student);
    }

    @Test
    void updateBookTitleTest() {
        Book book = new Book("Sample book!");
        Student student = new Student("SFN", "SLN");
        student.addBook(book);
        Mockito.when(studentStorage.findAll()).thenReturn(Collections.singletonList(student));
        studentService.updateBookTitle(student.getId(), book.getId(), "Another book!");
        Mockito.verify(studentStorage).save(student);
    }
}