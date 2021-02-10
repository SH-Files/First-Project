package main.java.repository;

import entity.Book;
import entity.Student;

import java.util.HashMap;

public class Storage
{
    private final HashMap<Integer, Book> books = new HashMap<>();
    private final HashMap<Integer, Student> students = new HashMap<>();

    public HashMap<Integer, Book> getBooks()
    {
        return this.books;
    }

    public HashMap<Integer, Student> getStudents()
    {
        return this.students;
    }

    public Book getBook(int key)
    {
        return this.books.get(key);
    }

    public Student getStudent(int key)
    {
        return this.students.get(key);
    }

    public void addBook(int key, Book book)
    {
        this.books.put(key, book);
    }

    public void addStudent(int key, Student student)
    {
        this.students.put(key, student);
    }

    public void removeBook(int key)
    {
        this.books.remove(key);
    }

    public void removeStudent(int key)
    {
        this.students.remove(key);
    }
}