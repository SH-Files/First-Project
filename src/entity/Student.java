package entity;

import java.util.HashMap;

public class Student
{
    private int id;
    private String firstName;
    private String lastName;

    private final HashMap<Integer, Book> books = new HashMap<>();

    public Student(int id, String firstName, String lastName)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId()
    {
        return this.id;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public HashMap<Integer, Book> getBooks()
    {
        return this.books;
    }

    public void addBook(int key, Book book)
    {
        this.books.put(key, book);
    }

    public void removeBook(int key)
    {
        this.books.remove(key);
    }

    public Book getBook(int key)
    {
        return this.books.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return id == student.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}