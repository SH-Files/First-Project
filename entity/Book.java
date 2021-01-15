package entity;

public class Book
{
    private static int counter = 0;

    private final int id;
    private String title;

    public Book(String title)
    {
        this.id = ++counter;
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
    public boolean equals(Object o)
    {
        return o instanceof Book && this.id == ((Book) o).id;
    }

    @Override
    public int hashCode()
    {
        return this.id;
    }
}