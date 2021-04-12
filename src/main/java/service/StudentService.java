package service;

import entity.Book;
import entity.Student;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import repository.Storage;
import exception.BookNotFoundException;
import exception.StudentNotFoundException;

@Service
public class StudentService {

    @Autowired
    private Storage storage;

    @Autowired
    private ApplicationContext context;

    public Student getStudent(String studentKey) {
        Student student = storage.getStudent(studentKey);
        if (student == null) {
            throw new StudentNotFoundException();
        }
        return student;
    }

    public HashMap<String, Student> getStudents() {
        return storage.getStudents();
    }

    public void createStudent(String firstName, String lastName) {
        Student student = (Student) context.getBean("student", firstName, lastName);
        storage.addStudent(student);
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
        storage.removeStudent(student.getId());
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