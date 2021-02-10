package main.java.service;

import main.java.entity.Book;
import main.java.entity.Student;
import main.java.repository.Storage;

import java.util.HashMap;

public class Service
{
    private final Storage storage;

    public Service(Storage storage)
    {
        this.storage = storage;
    }

    public Book getBook(int key)
    {
        return this.storage.getBook(key);
    }

    public Student getStudent(int key)
    {
        return this.storage.getStudent(key);
    }

    public HashMap<Integer, Book> getBooks()
    {
        return this.storage.getBooks();
    }

    public HashMap<Integer, Student> getStudents()
    {
        return this.storage.getStudents();
    }

    public void createBook(String title)
    {
        this.storage.addBook(storage.getBooks().size() + 1, new Book(storage.getBooks().size() + 1, title));
    }

    public void createStudent(String firstName, String lastName)
    {
        this.storage.addStudent(storage.getStudents().size() + 1, new Student(storage.getStudents().size() + 1, firstName, lastName));
    }

    public void updateStudentFirstName(int key, String firstname)
    {
        this.storage.getStudent(key).setFirstName(firstname);
    }

    public void updateStudentLastName(int key, String lastName)
    {
        this.storage.getStudent(key).setLastName(lastName);
    }

    public void updateStudentAddBook(int keyStudent, int keyBook)
    {
        this.storage.getStudent(keyStudent).addBook(this.storage.getStudent(keyStudent).getBooks().size() + 1, this.storage.getBook(keyBook));
        this.storage.removeBook(keyBook);
    }

    public void updateStudentRemoveBook(int keyStudent, int keyBook)
    {
        this.storage.addBook(this.storage.getBooks().size() + 1, this.storage.getStudent(keyStudent).getBook(keyBook));
        this.storage.getStudent(keyStudent).removeBook(keyBook);
    }

    public void updateBookTitle(int key, String title)
    {
        this.storage.getBook(key).setTitle(title);
    }

    public void removeBook(int key)
    {
        this.storage.removeBook(key);
    }

    public void removeStudent(int key)
    {
        this.storage.getStudent(key).getBooks().forEach((k, v) -> this.storage.addBook(this.storage.getBooks().size() + 1, v));
        this.storage.removeStudent(key);
    }
}