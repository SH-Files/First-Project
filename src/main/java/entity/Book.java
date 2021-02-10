package main.java.entity;

public class Book
{
    private final int id;
    private String title;

    public Book(int id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public int getId()
    {
        return this.id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return this.title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return id == book.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}