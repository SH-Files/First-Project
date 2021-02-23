package service;

import entity.Book;
import entity.Student;
import exception.BookNotFoundException;
import exception.StudentNotFoundException;
import repository.Storage;

import java.util.HashMap;
import java.util.Set;

public class Service {
    private final Storage storage;

    public Service(Storage storage) {
        this.storage = storage;
    }

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
        Student student = new Student(firstName, lastName);
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