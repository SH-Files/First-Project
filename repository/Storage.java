package repository;

import entity.Book;
import entity.Student;
import service.CustomSet;

public class Storage
{
    private final CustomSet<Book> books = new CustomSet<>();
    private final CustomSet<Student> students = new CustomSet<>();

    public CustomSet<Book> getBooks()
    {
        return this.books;
    }

    public CustomSet<Student> getStudents()
    {
        return this.students;
    }

    public Book getBook(int hashCode)
    {
        return this.books.get(hashCode);
    }

    public Student getStudent(int hashCode)
    {
        return this.students.get(hashCode);
    }

    public void addBook(Book book)
    {
        this.books.add(book);
    }

    public void addStudent(Student student)
    {
        this.students.add(student);
    }

    public void removeBook(Book book)
    {
        this.books.remove(book);
    }

    public void removeStudent(Student student)
    {
        this.students.remove(student);
    }
}