package Service;

import Entity.Book;
import Entity.Student;
import Repository.InMemoryStorage;

import java.util.List;

public class StudentService
{
    private final InMemoryStorage inMemoryStorage;

    public StudentService(InMemoryStorage inMemoryStorage)
    {
        this.inMemoryStorage = inMemoryStorage;
    }

    public List<Student> getStudents()
    {
        return inMemoryStorage.getStudents();
    }

    public List<Book> getBooks()
    {
        return inMemoryStorage.getBooks();
    }

    public void createStudent(String firstname, String lastname)
    {
        inMemoryStorage.addStudent(new Student(firstname, lastname));
    }

    public boolean createBook(String title)
    {
        for (Book book : this.inMemoryStorage.getBooks())
        {
            if (book.getTitle().equals(title))
            {
                return false;
            }
        }

        for (Student student : inMemoryStorage.getStudents())
        {
            for (Book book : student.getBooks())
            {
                if (book.getTitle().equals(title))
                {
                    return false;
                }
            }
        }

        this.inMemoryStorage.addBook(new Book(title));

        return true;
    }

    public boolean updateStudentFirstname(int studentIndex, String firstname)
    {
        if (firstname.length() > 2)
        {
            inMemoryStorage.getStudents().get(studentIndex).setFirstname(firstname);
        }
        else
        {
            return false;
        }
        return true;
    }

    public boolean updateStudentLastname(int studentIndex, String lastname)
    {
        if (lastname.length() > 2)
        {
            inMemoryStorage.getStudents().get(studentIndex).setFirstname(lastname);
        }
        else
        {
            return false;
        }
        return true;
    }

    public boolean updateStudentAddBook(int studentIndex, String choice)
    {
        if (Validator.isInteger(choice) && Integer.parseInt(choice) > 0 && Integer.parseInt(choice) - 1 < inMemoryStorage.getBooks().size())
        {
            int bookIndex = Integer.parseInt(choice) - 1;

            inMemoryStorage.getStudents().get(studentIndex).addBook(inMemoryStorage.getBooks().get(bookIndex));
            inMemoryStorage.getBooks().remove(bookIndex);
        }
        else
        {
            return false;
        }
        return true;
    }

    public boolean updateStudentRemoveBook(int studentIndex, String choice)
    {
        if (Validator.isInteger(choice) && Integer.parseInt(choice) > 0 && Integer.parseInt(choice) - 1 < inMemoryStorage.getStudents().get(studentIndex).getBooks().size())
        {
            int bookIndex = Integer.parseInt(choice) - 1;

            inMemoryStorage.getBooks().add(inMemoryStorage.getStudents().get(studentIndex).getBooks().get(bookIndex));
            inMemoryStorage.getStudents().get(studentIndex).getBooks().remove(bookIndex);
        }
        else
        {
            return false;
        }
        return true;
    }

    public boolean updateBookTitle(int bookIndex, String title)
    {
        for (Book book : inMemoryStorage.getBooks())
        {
            if (book.getTitle().equals(title))
            {
                return false;
            }
        }

        inMemoryStorage.getBooks().get(bookIndex).setTitle(title);

        return true;
    }

    public boolean readStudent(String choice)
    {
        if (Validator.isInteger(choice) && Integer.parseInt(choice) > 0 && Integer.parseInt(choice) - 1 < inMemoryStorage.getStudents().size())
        {
            int index = Integer.parseInt(choice) - 1;

            System.out.println("Selected student: " + inMemoryStorage.getStudents().get(index).getLastname() + ", " + inMemoryStorage.getStudents().get(index).getFirstname() + "\n");
            System.out.println("Related books:");

            if (inMemoryStorage.getStudents().get(index).getBooks().size() > 0)
            {
                for (Book book : inMemoryStorage.getStudents().get(index).getBooks())
                {
                    System.out.println("\u2022\t" + book.getTitle());
                }
                System.out.println();
            }
            else
            {
                System.out.println("Student has currently no books.\n");
            }
        }
        else
        {
            return false;
        }
        return true;
    }

    public void readBooks()
    {
        if (inMemoryStorage.getBooks().size() > 0)
        {
            System.out.println("Following books are currently in stock:");

            for (Book book : inMemoryStorage.getBooks())
            {
                System.out.println("\u2022\t" + book.getTitle());
            }
            System.out.println();
        }
        else
        {
            System.out.println("There is currently no book in stock.\n");
        }
    }

    public boolean deleteStudent(String choice)
    {
        if (Validator.isInteger(choice) && Integer.parseInt(choice) > 0 && Integer.parseInt(choice) - 1 < inMemoryStorage.getStudents().size())
        {
            int studentIndex = Integer.parseInt(choice) - 1;

            for (Book book : inMemoryStorage.getStudents().get(studentIndex).getBooks())
            {
                inMemoryStorage.getBooks().add(book);
            }

            inMemoryStorage.getStudents().remove(studentIndex);
        }
        else
        {
            return false;
        }
        return true;
    }

    public boolean deleteBook(String choice)
    {
        if (Validator.isInteger(choice) && Integer.parseInt(choice) > 0 && Integer.parseInt(choice) - 1 < inMemoryStorage.getStudents().size())
        {
            inMemoryStorage.getBooks().remove(Integer.parseInt(choice) - 1);
        }
        else
        {
            return false;
        }
        return true;
    }
}