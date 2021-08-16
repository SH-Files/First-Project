package com.example.first.project.controller;

import com.example.first.project.entity.Book;
import com.example.first.project.entity.Student;
import com.example.first.project.exception.BookNotFoundException;
import com.example.first.project.exception.StudentNotFoundException;
import com.example.first.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;

@RestController
public class MyRestController {

    private final StudentService studentService;

    @Autowired
    public MyRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/students")
    public String createStudent(@RequestBody Student student) {
        if (student.getFirstName() != null && !student.getFirstName().isEmpty()) {
            if (student.getLastName() != null && !student.getLastName().isEmpty()) {
                studentService.saveStudent(student);
                return "Student was created successfully";
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student couldn't be created. Last name cannot be empty");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student couldn't be created. First name cannot be empty");
    }

    @GetMapping("/students/{studentKey}")
    public Student getStudent(@PathVariable("studentKey") String studentKey) {
        try {
            return studentService.getStudent(Integer.parseInt(studentKey));
        } catch (StudentNotFoundException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
    }

    @GetMapping("/students")
    public HashSet<Student> getStudents() {
        if (studentService.getStudents().size() > 0) {
            HashSet<Student> students = new HashSet<>();

            studentService.getStudents().forEach((key, student) -> {
                students.add(student);
            });
            return students;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No students found");
    }

    @PutMapping("/students/{studentKey}")
    public String updateStudent(@PathVariable("studentKey") String studentKey, @RequestBody Student changedStudent) {
        try {
            Student student = studentService.getStudent(Integer.parseInt(studentKey));

            if (changedStudent.getFirstName() != null && !changedStudent.getFirstName().isEmpty()) {
                studentService.updateStudentFirstName(student.getId(), changedStudent.getFirstName());
            }
            if (changedStudent.getFirstName() != null && !changedStudent.getFirstName().isEmpty()) {
                studentService.updateStudentFirstName(student.getId(), changedStudent.getFirstName());
            }
            changedStudent.getBooks().forEach(newBook -> {
                studentService.addBookToStudent(student.getId(), newBook.getTitle());
            });
            return "Student was successfully updated";
        } catch (StudentNotFoundException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
    }

    @PutMapping("/students/{studentKey}/{bookKey}")
    public String updateBook(@PathVariable("studentKey") String studentKey, @PathVariable("bookKey") String bookKey, @RequestBody Book changedBook) {
        try {
            Student student = studentService.getStudent(Integer.parseInt(studentKey));

            try {
                studentService.updateBookTitle(student.getId(), Integer.parseInt(bookKey), changedBook.getTitle());
                return "Book was successfully updated";
            } catch (BookNotFoundException | NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
            }
        } catch (StudentNotFoundException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
    }

    @DeleteMapping("/students/{studentKey}/{bookKey}")
    public String deleteBook(@PathVariable("studentKey") String studentKey, @PathVariable("bookKey") String bookKey) {
        try {
            Student student = studentService.getStudent(Integer.parseInt(studentKey));

            try {
                studentService.removeBookFromStudent(student.getId(), Integer.parseInt(bookKey));
                return "Book was successfully deleted";
            } catch (BookNotFoundException | NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
            }
        } catch (StudentNotFoundException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
    }

    @DeleteMapping("/students/{studentKey}")
    public String deleteStudent(@PathVariable("studentKey") String studentKey) {
        try {
            studentService.removeStudent(Integer.parseInt(studentKey));
            return "Student was successfully deleted";
        } catch (StudentNotFoundException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
    }
}