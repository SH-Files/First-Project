package Entity;

import java.util.ArrayList;
import java.util.List;

public class Student
{
    private String firstname;
    private String lastname;
    private final List<Book> books = new ArrayList<Book>();

    public Student(String firstname, String lastname)
    {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public void addBook(Book book)
    {
        books.add(book);
    }

    public List<Book> getBooks()
    {
        return books;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public String getLastname()
    {
        return lastname;
    }
}