package com.example.first.project.controller;

import java.util.HashSet;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import com.example.first.project.entity.Student;
import org.springframework.web.bind.annotation.*;
import com.example.first.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import com.example.first.project.exception.StudentNotFoundException;

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
        return studentService.getStudent(Integer.parseInt(studentKey));
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
        Student student = studentService.getStudent(Integer.parseInt(studentKey));
        changedStudent.setBooks(changedStudent.getBooks().stream().filter(book -> book.getTitle() != null && !book.getTitle().trim().isEmpty()).collect(Collectors.toSet()));

        if (changedStudent.getFirstName() != null && !changedStudent.getFirstName().isEmpty()) {
            studentService.updateStudentFirstName(student.getId(), changedStudent.getFirstName());
        }
        if (changedStudent.getFirstName() != null && !changedStudent.getFirstName().isEmpty()) {
            studentService.updateStudentFirstName(student.getId(), changedStudent.getFirstName());
        }
        studentService.updateStudentBooks(student.getId(), changedStudent.getBooks());
        return "Student was updated successfully";
    }

    @DeleteMapping("/students/{studentKey}")
    public String deleteStudent(@PathVariable("studentKey") String studentKey) {
        studentService.removeStudent(Integer.parseInt(studentKey));
        return "Student was deleted successfully";
    }

    @ExceptionHandler(value = {StudentNotFoundException.class, NumberFormatException.class})
    public String studentNotFoundExceptionHandler() {
        return "No student with the given id was found";
    }
}