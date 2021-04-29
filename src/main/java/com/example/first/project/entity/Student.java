package com.example.first.project.entity;

import java.util.*;

import com.example.first.project.exception.BookNotFoundException;

import javax.persistence.*;

@Entity(name = "Student")
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "studentId")
    private final Set<Book> books = new HashSet<>();

    public Student() {}

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void removeBook(long id) {
        Book bookFromStudent = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(BookNotFoundException::new);
        books.remove(bookFromStudent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (!firstName.equals(student.firstName)) return false;
        return lastName.equals(student.lastName);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("Student { id = %s, firstName = %s, lastName = %s, books = %s }", id, firstName, lastName, books);
    }
}