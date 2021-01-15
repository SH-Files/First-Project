package entity;

import service.CustomSet;

public class Student
{
    private static int counter = 0;

    private final int id;
    private String firstName;
    private String lastName;

    private final CustomSet<Book> books = new CustomSet<>();

    public Student(String firstName, String lastName)
    {
        this.id = ++counter;
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

    public CustomSet<Book> getBooks()
    {
        return this.books;
    }

    public void addBook(Book book)
    {
        this.books.add(book);
    }

    public void removeBook(Book book)
    {
        this.books.remove(book);
    }

    public Book getBook(int hashCode)
    {
        return this.books.get(hashCode);
    }

    @Override
    public boolean equals(Object o)
    {
        return o instanceof Student && this.id == ((Student) o).id;
    }

    @Override
    public int hashCode()
    {
        return this.id;
    }
}