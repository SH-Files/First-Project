package com.example.first.project.entity;

import java.util.*;

import com.example.first.project.exception.BookNotFoundException;

public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private final Set<Book> books;

    public Student(String firstName, String lastName) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        books = new HashSet<>();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(String id) {
        Book bookFromStudent = books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(BookNotFoundException::new);
        books.remove(bookFromStudent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id) &&
                Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return String.format("Student { id = %s, firstName = %s, lastName = %s, books = %s }", id, firstName, lastName, books);
    }
}