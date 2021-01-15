package service;

import entity.Book;
import entity.Student;
import repository.Storage;

public class Service
{
    private final Storage storage;

    public Service(Storage storage)
    {
        this.storage = storage;
    }

    public Book getBook(int hashCode)
    {
        return this.storage.getBook(hashCode);
    }

    public Student getStudent(int hashCode)
    {
        return this.storage.getStudent(hashCode);
    }

    public CustomSet<Book> getBooks()
    {
        return this.storage.getBooks();
    }

    public CustomSet<Student> getStudents()
    {
        return this.storage.getStudents();
    }

    public void createBook(String title)
    {
        this.storage.addBook(new Book(title));
    }

    public void createStudent(String firstName, String lastName)
    {
        this.storage.addStudent(new Student(firstName, lastName));
    }

    public void updateStudentFirstName(int hashCode, String firstname)
    {
        this.storage.getStudent(hashCode).setFirstName(firstname);
    }

    public void updateStudentLastName(int hashCode, String lastName)
    {
        this.storage.getStudent(hashCode).setLastName(lastName);
    }

    public void updateStudentAddBook(int hashCodeStudent, int hashCodeBook)
    {
        this.storage.getStudent(hashCodeStudent).addBook(this.storage.getBook(hashCodeBook));
        this.storage.removeBook(this.storage.getBook(hashCodeBook));
    }

    public void updateStudentRemoveBook(int hashCodeStudent, int hashCodeBook)
    {
        this.storage.addBook(this.storage.getStudent(hashCodeStudent).getBook(hashCodeBook));
        this.storage.getStudent(hashCodeStudent).removeBook(this.storage.getStudent(hashCodeStudent).getBook(hashCodeBook));
    }

    public void updateBookTitle(int hashCode, String title)
    {
        this.storage.getBook(hashCode).setTitle(title);
    }

    public void removeBook(int hashCode)
    {
        this.storage.removeBook(this.storage.getBook(hashCode));
    }

    public void removeStudent(int hashCode)
    {
        this.storage.getStudent(hashCode).getBooks().forEach(e -> {
            this.storage.addBook(e);
        });
        this.storage.removeStudent(this.storage.getStudent(hashCode));
    }
}