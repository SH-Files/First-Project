package Repository;

import Entity.Book;
import Entity.Student;
import java.util.List;
import java.util.ArrayList;

public class InMemoryStorage
{
    private final List<Book> books = new ArrayList<Book>();
    private final List<Student> students = new ArrayList<Student>();

    public void addStudent(Student student)
    {
        students.add(student);
    }

    public void addBook(Book book)
    {
        books.add(book);
    }

    public List<Student> getStudents()
    {
        return students;
    }

    public List<Book> getBooks()
    {
        return books;
    }
}