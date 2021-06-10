package com.example.first.project;

import com.example.first.project.entity.Book;
import com.example.first.project.entity.Student;
import com.example.first.project.service.StudentService;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.first.project.repository.StudentStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.*;

@SpringBootTest
public class FirstProjectApplicationTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentStorage studentStorage;

    @Test
    void createStudentTest() {
        studentService.createStudent("SFN", "SLN");

        Mockito.verify(studentStorage).save(new Student("SFN", "SLN"));
    }

    @Test
    void getStudentsTest() {
        Mockito.when(studentStorage.findAll()).thenReturn(getListOfStudents());

        HashMap<Integer, Student> students = studentService.getStudents();

        assertEquals(2, students.size());
    }

    @Test
    void getStudentTest() {
        Mockito.when(studentStorage.findAll()).thenReturn(getListOfStudents());

        HashMap<Integer, Student> students = studentService.getStudents();

        Student student1 = students.get(1);
        Student student2 = students.get(2);

        assertEquals("SFN1", student1.getFirstName());
        assertEquals("SLN1", student1.getLastName());
        assertEquals("SFN2", student2.getFirstName());
        assertEquals("SLN2", student2.getLastName());
    }

    @Test
    void updateStudentFirstNameTest() {
        Student student = new Student("SFN", "SLN");

        Mockito.when(studentStorage.findAll()).thenReturn(Collections.singletonList(student));

        studentService.updateStudentFirstName(student.getId(), "USFN");

        Mockito.verify(studentStorage).save(student);
        assertEquals("USFN", student.getFirstName());
        assertEquals("SLN", student.getLastName());
    }

    @Test
    void updateStudentLastNameTest() {
        Student student = new Student("SFN", "SLN");

        Mockito.when(studentStorage.findAll()).thenReturn(Collections.singletonList(student));

        studentService.updateStudentLastName(student.getId(), "USLN");

        Mockito.verify(studentStorage).save(student);
        assertEquals("SFN", student.getFirstName());
        assertEquals("USLN", student.getLastName());
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
        assertEquals(1, student.getBooks().size());
        student.getBooks().forEach(o -> {
            assertEquals("Sample book!", o.getTitle());
        });
    }

    @Test
    void removeBookToStudentTest() {
        Book book = new Book("Sample book!");

        Student student = new Student("SFN", "SLN");
        student.addBook(book);

        Mockito.when(studentStorage.findAll()).thenReturn(Collections.singletonList(student));

        studentService.removeBookFromStudent(student.getId(), book.getId());

        Mockito.verify(studentStorage).save(student);
        assertTrue(student.getBooks().isEmpty());
    }

    @Test
    void updateBookTitleTest() {
        Book book = new Book("Sample book!");

        Student student = new Student("SFN", "SLN");
        student.addBook(book);

        Mockito.when(studentStorage.findAll()).thenReturn(Collections.singletonList(student));

        studentService.updateBookTitle(student.getId(), book.getId(), "Another book!");

        Mockito.verify(studentStorage).save(student);
        student.getBooks().forEach(o -> {
            assertEquals("Another book!", book.getTitle());
        });
    }

    List<Student> getListOfStudents() {
        List<Student> students = new ArrayList<>();

        Student student1 = new Student("SFN1", "SLN1");
        Student student2 = new Student("SFN2", "SLN2");

        student1.setId(1);
        student2.setId(2);

        students.add(student1);
        students.add(student2);

        return students;
    }
}