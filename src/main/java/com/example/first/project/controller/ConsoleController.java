package com.example.first.project.controller;

import java.util.Scanner;
import com.example.first.project.entity.Student;
import com.example.first.project.exception.BookNotFoundException;
import com.example.first.project.exception.StudentNotFoundException;
import com.example.first.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RestController
public class ConsoleController {

    /*

        200: HTTP OK
        400: BAD REQUEST
        404: NOT FOUND
        500: INTERNAL SERVER ERROR

     */

    private final Scanner scn;
    private final StudentService studentService;

    @Autowired
    public ConsoleController(StudentService studentService) {
        scn = new Scanner(System.in);
        this.studentService = studentService;
    }

    @PostMapping("/createStudent")
    public void createStudent(@RequestParam(value = "firstName") String firstName,
                              @RequestParam(value = "lastName") String lastName) {
        if (firstName != null && !firstName.isEmpty()) {
            if (lastName != null && !lastName.isEmpty()) {
                studentService.createStudent(firstName, lastName);
                throw new ResponseStatusException(HttpStatus.OK, "Student was created successfully.");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student couldn't be created. Last name cannot be empty.");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student couldn't be created. First name cannot be empty.");
    }

    @PostMapping("/readStudent")
    public Student readStudent(@RequestParam(value = "studentKey") String studentKey) {
        try {
            return studentService.getStudent(Integer.parseInt(studentKey));
        } catch (StudentNotFoundException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student not found.");
        }
    }

    @PostMapping("/updateStudentFirstName")
    public void updateStudentFirstName(@RequestParam(value = "studentKey") String studentKey,
                                       @RequestParam(value = "newFirstName") String newFirstName) {
        try {
            if (newFirstName != null && !newFirstName.isEmpty()) {
                studentService.updateStudentFirstName(Integer.parseInt(studentKey), newFirstName);
                throw new ResponseStatusException(HttpStatus.OK, "Student's first name was updated successfully.");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student couldn't be updated. First name can't be empty.");
        } catch (StudentNotFoundException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found.");
        }
    }

    @PostMapping("/updateStudentLastName")
    public String updateStudentLastName(@RequestParam(value = "studentKey") String studentKey,
                                        @RequestParam(value = "newLastName") String newLastName) {
        try {
            if (newLastName != null && !newLastName.isEmpty()) {
                studentService.updateStudentLastName(Integer.parseInt(studentKey), newLastName);
                throw new ResponseStatusException(HttpStatus.OK, "Student's last name was updated successfully.");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student couldn't be updated. Last name can't be empty.");
        } catch (StudentNotFoundException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found.");
        }
    }

    @PostMapping("/updateStudentBooksAddBook")
    public void updateStudentAddBook(@RequestParam(value = "studentKey") String studentKey,
                                     @RequestParam(value = "title") String title) {
        try {
            if (title != null && !title.isEmpty()) {
                studentService.addBookToStudent(Integer.parseInt(studentKey), title);
                throw new ResponseStatusException(HttpStatus.OK, "Book was added successfully.");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book couldn't be added. Title cannot be empty.");
        } catch (StudentNotFoundException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found.");
        }
    }

    @PostMapping("/updateStudentBooksRemoveBook")
    public void updateStudentRemoveBook(@RequestParam(value = "studentKey") String studentKey,
                                        @RequestParam(value = "bookKey") String bookKey) {
        try {
            studentService.getStudent(Integer.parseInt(studentKey));
            try {
                studentService.removeBookFromStudent(Integer.parseInt(studentKey), Integer.parseInt(bookKey));
                throw new ResponseStatusException(HttpStatus.OK, "Book was successfully removed.");
            } catch (BookNotFoundException | NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found.");
            }
        } catch (StudentNotFoundException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found.");
        }
    }

    @PostMapping("/updateStudentBooksUpdateBookTitle")
    public void updateStudentBooksUpdateBookTitle(@RequestParam(value = "studentKey") String studentKey,
                                                    @RequestParam(value = "bookKey") String bookKey,
                                                    @RequestParam(value = "newTitle") String newTitle) {
        try {
            studentService.getStudent(Integer.parseInt(studentKey));
            try {
                studentService.updateBookTitle(Integer.parseInt(studentKey), Integer.parseInt(bookKey), newTitle);
            } catch (BookNotFoundException | NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found.");
            }
        } catch (StudentNotFoundException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found.");
        }
    }

    @PostMapping("/removeStudent")
    public void removeStudent(@RequestParam(value = "studentKey") String studentKey) {
        try {
            studentService.removeStudent(Integer.parseInt(studentKey));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student was successfully removed.");
        } catch (StudentNotFoundException | NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found.");
        }
    }
}