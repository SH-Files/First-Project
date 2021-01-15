package controller;

import entity.Student;
import service.Service;
import java.util.Scanner;
import repository.Storage;

public class Main
{
    private static final String CREATE = "1";
    private static final String CREATESTUDENT = "1";
    private static final String CREATEBOOK = "2";

    private static final String UPDATE = "2";
    private static final String UPDATESTUDENT = "1";
    private static final String UPDATEBOOK = "2";
    private static final String UPDATESTUDENTFIRSTNAME = "1";
    private static final String UPDATESTUDENTLASTNAME = "2";
    private static final String UPDATESTUDENTADDBOOK = "3";
    private static final String UPDATESTUDENTREMOVEBOOK = "4";

    private static final String READ = "3";
    private static final String READSTUDENT = "1";
    private static final String READBOOKS = "2";

    private static final String REMOVE = "4";
    private static final String REMOVESTUDENT = "1";
    private static final String REMOVEBOOK = "2";

    private static final String EXIT = "5";

    private static final Scanner scn = new Scanner(System.in);
    private static final Service service = new Service(new Storage());

    public static void main(String[] args)
    {
        start:
        while (true)
        {
            System.out.println("Which one of the following actions would you like to choose?\n");

            System.out.println("1 --> I want to create a student or a book.");
            System.out.println("2 --> I want to update a student or a book.");
            System.out.println("3 --> I want to get information about a student or the books in stock.");
            System.out.println("4 --> I want to remove a student or a book.");
            System.out.println("5 --> I want to quit the game.");

            String choice = scn.nextLine().trim();

            switch (choice)
            {
                case CREATE -> {
                    create();
                }
                case UPDATE -> {
                    update();
                }
                case READ -> {
                    read();
                }
                case REMOVE -> {
                    remove();
                }
                case EXIT -> {
                    break start;
                }
                default -> {
                    System.out.println("Chosen action doesn't exist.\n");
                }
            }
        }
        System.out.println("Goodbye and until next time :)");
    }

    public static void create()
    {
        System.out.println("1 --> I want to create a student.");
        System.out.println("2 --> I want to create a book.");

        String choice = scn.nextLine().trim();

        switch (choice)
        {
            case CREATESTUDENT -> {
                createStudent();
            }
            case CREATEBOOK -> {
                createBook();
            }
            default -> {
                System.out.println("Chosen action doesn't exist.\n");
            }
        }
    }

    public static void createStudent()
    {
        System.out.println("What's the student's first name?");

        String firstName = scn.nextLine().trim();

        if (firstName.length() > 2)
        {
            System.out.println("What's the student's last name?");

            String lastName = scn.nextLine().trim();

            if (lastName.length() > 2)
            {
                service.createStudent(firstName, lastName);
                System.out.println("Student was successfully added.\n");
            }
            else
            {
                System.out.println("Student could not be added. The first name must be at least three characters long.\n");
            }
        }
        else
        {
            System.out.println("Student could not be added. The first name must be at least three characters long.\n");
        }
    }

    public static void createBook()
    {
        System.out.println("What's the book's title?");

        String title = scn.nextLine().trim();

        if (title.length() > 0)
        {
            service.createBook(title);
            System.out.println("Book was successfully added.\n");
        }
        else
        {
            System.out.println("Book could not be added. The title must be at least one character long.\n");
        }
    }

    public static void read()
    {
        if (service.getStudents().size() > 0 || service.getBooks().size() > 0)
        {
            System.out.println("1 --> I want to get information about a student.");
            System.out.println("2 --> I want to get information about the books in stock.");

            String choice = scn.nextLine().trim();

            switch (choice)
            {
                case READSTUDENT -> {
                    readStudent();
                }
                case READBOOKS -> {
                    readBook();
                }
                default -> {
                    System.out.println("Book was successfully added.\n");
                }
            }
        }
        else
        {
            System.out.println("There are currently no students or books in stock.\n");
        }
    }

    public static void readStudent()
    {
        if (service.getStudents().size() > 0)
        {
            System.out.println("Choose one of the following students:");

            service.getStudents().forEach(e -> {
                System.out.println(e.getId() + " --> " + e.getFirstName() + " " + e.getLastName());
            });

            String choice = scn.nextLine().trim();

            if (isInteger(choice) && service.getStudents().contains(service.getStudent(Integer.parseInt(choice))))
            {
                Student student = service.getStudent(Integer.parseInt(choice));

                System.out.println("Student's name: " + student.getLastName() + ", " + student.getFirstName() + "\n");

                System.out.println("Books in possession:");

                if (student.getBooks().size() > 0)
                {
                    student.getBooks().forEach(e -> {
                        System.out.println("\u2022\t" + e.getTitle() + " (ID: " + e.getId() + ")");
                    });
                    System.out.println();
                }
                else
                {
                    System.out.println("There are currently no books in the student's possession.\n");
                }
            }
            else
            {
                System.out.println("Chosen action doesn't exist.\n");
            }
        }
        else
        {
            System.out.println("There are currently no students.\n");
        }
    }

    public static void readBook()
    {
        if (service.getBooks().size() > 0)
        {
            System.out.println("The following books are currently in stock:");

            service.getBooks().forEach(e -> {
                System.out.println("\u2022\t" + e.getTitle() + " (ID: " + e.getId() + ")");
            });
            System.out.println();
        }
        else
        {
            System.out.println("There are currently no books in stock.\n");
        }
    }

    public static void update()
    {
        if (service.getStudents().size() > 0 || service.getBooks().size() > 0)
        {
            System.out.println("1 --> I want to update a student.");
            System.out.println("2 --> I want to update a book.");

            String choice = scn.nextLine().trim();

            switch (choice)
            {
                case UPDATESTUDENT -> {
                    updateStudent();
                }
                case UPDATEBOOK -> {
                    updateBook();
                }
            }
        }
        else
        {
            System.out.println("There are currently no students or books in stock.");
        }
    }

    public static void updateStudent()
    {
        System.out.println("Choose one of the following students:");

        service.getStudents().forEach(e -> {
            System.out.println(e.getId() + " --> " + e.getFirstName() + " " + e.getLastName());
        });

        String choice = scn.nextLine().trim();

        if (isInteger(choice) && service.getStudents().contains(service.getStudent(Integer.parseInt(choice))))
        {
            int hashCode = Integer.parseInt(choice);

            do
            {
                System.out.println("Chosen student: " + service.getStudent(hashCode).getLastName() + ", " + service.getStudent(hashCode).getFirstName() + "\n");

                System.out.println("Which one of the following actions would you like to choose?\n");

                System.out.println("1 --> I want to update the student's first name.");
                System.out.println("2 --> I want to update the student's last name.");
                System.out.println("3 --> I want to add a book to the student.");
                System.out.println("4 --> I want to remove a book from the student.");

                choice = scn.nextLine().trim();

                switch (choice)
                {
                    case UPDATESTUDENTFIRSTNAME -> {
                        updateStudentFirstName(hashCode);
                    }
                    case UPDATESTUDENTLASTNAME -> {
                        updateStudentLastName(hashCode);
                    }
                    case UPDATESTUDENTADDBOOK -> {
                        updateStudentAddBook(hashCode);
                    }
                    case UPDATESTUDENTREMOVEBOOK -> {
                        updateStudentRemoveBook(hashCode);
                    }
                    default -> {
                        System.out.println("Chosen action doesn't exist.\n");
                    }
                }

                System.out.println("Would you like to update something else? (Y / N)");

                choice = scn.nextLine().trim();

            } while (choice.toUpperCase().equals("Y"));
        }
        else
        {
            System.out.println("Chosen action doesn't exist.\n");
        }
    }

    public static void updateStudentFirstName(int hashCode)
    {
        System.out.println("What's the student's new first name?");

        String firstName = scn.nextLine().trim();

        if (firstName.length() > 2)
        {
            service.updateStudentFirstName(hashCode, firstName);
            System.out.println("Student's first name was successfully updated.\n");
        }
        else
        {
            System.out.println("Student could not be updated. First name must be at least three characters long.\n");
        }
    }

    public static void updateStudentLastName(int hashCode)
    {
        System.out.println("What's the student's new last name?");

        String lastName = scn.nextLine().trim();

        if (lastName.length() > 2)
        {
            service.updateStudentLastName(hashCode, lastName);
            System.out.println("Student's last name was successfully updated.\n");
        }
        else
        {
            System.out.println("Student could not be updated. Last name must be at least three characters long.\n");
        }
    }

    public static void updateStudentAddBook(int hashCode)
    {
        if (service.getBooks().size() > 0)
        {
            System.out.println("Choose one of the following books:");

            service.getBooks().forEach(e -> {
                System.out.println(e.getId() + " --> " + e.getTitle());
            });

            String choice = scn.nextLine().trim();

            if (isInteger(choice) && service.getBooks().contains(service.getBook(Integer.parseInt(choice))))
            {
                service.updateStudentAddBook(hashCode, Integer.parseInt(choice));
                System.out.println("Book is now in student's possession.\n");
            }
            else
            {
                System.out.println("Chosen action doesn't exist.\n");
            }
        }
        else
        {
            System.out.println("There are currently no books in stock.\n");
        }
    }

    public static void updateStudentRemoveBook(int hashCode)
    {
        if (service.getStudent(hashCode).getBooks().size() > 0)
        {
            System.out.println("Choose one of the following books:");

            service.getStudent(hashCode).getBooks().forEach(e -> {
                System.out.println(e.getId() + " --> " + e.getTitle());
            });

            String choice = scn.nextLine().trim();

            if (isInteger(choice) && service.getStudent(hashCode).getBooks().contains(service.getStudent(hashCode).getBook(Integer.parseInt(choice))))
            {
                service.updateStudentRemoveBook(hashCode, Integer.parseInt(choice));
                System.out.println("Book is no longer in student's possession.\n");
            }
            else
            {
                System.out.println("Book could not be removed. Chosen book is not in the student's possession.\n");
            }
        }
        else
        {
            System.out.println("Student has currently no books in possession.\n");
        }
    }

    public static void updateBook()
    {
        System.out.println("Choose one of the following books:");

        service.getBooks().forEach(e -> {
            System.out.println(e.getId() + " --> " + e.getTitle());
        });

        String choice = scn.nextLine().trim();

        if (isInteger(choice) && service.getBooks().contains(service.getBook(Integer.parseInt(choice))))
        {
            System.out.println("What's the book's new title?");

            String title = scn.nextLine().trim();

            if (title.length() > 0)
            {
                service.updateBookTitle(Integer.parseInt(choice), title);
                System.out.println("Book's title was successfully updated.\n");
            }
            else
            {
                System.out.println("Book could not be added. The title must be at least one character long.\n");
            }
        }
        else
        {
            System.out.println("Chosen action doesn't exist.\n");
        }
    }

    public static void remove()
    {
        if (service.getStudents().size() > 0 || service.getBooks().size() > 0)
        {
            System.out.println("1 --> I want to remove a student.");
            System.out.println("2 --> I want to remove a book.");

            String choice = scn.nextLine().trim();

            switch (choice)
            {
                case REMOVESTUDENT -> {
                    removeStudent();
                }
                case REMOVEBOOK -> {
                    removeBook();
                }
                default -> {
                    System.out.println("Chosen action doesn't exist.\n");
                }
            }
        }
        else
        {
            System.out.println("There are currently no students or books in stock.\n");
        }
    }

    public static void removeStudent()
    {
        if (service.getStudents().size() > 0)
        {
            System.out.println("Choose one of the following student's:");

            service.getStudents().forEach(e -> {
                System.out.println(e.getId() + " --> " + e.getFirstName() + " " + e.getLastName());
            });

            String choice = scn.nextLine().trim();

            if (isInteger(choice) && service.getStudents().contains(service.getStudent(Integer.parseInt(choice))))
            {
                service.removeStudent(Integer.parseInt(choice));
                System.out.println("Student was removed successfully.\n");
            }
            else
            {
                System.out.println("Chosen action doesn't exist.\n");
            }
        }
        else
        {
            System.out.println("There are currently no students.\n");
        }
    }

    public static void removeBook()
    {
        if (service.getBooks().size() > 0)
        {
            System.out.println("Choose one of the following book's:");

            service.getBooks().forEach(e -> {
                System.out.println(e.getId() + " --> " + e.getTitle());
            });

            String choice = scn.nextLine().trim();

            if (isInteger(choice) && service.getBooks().contains(service.getBook(Integer.parseInt(choice))))
            {
                service.removeBook(Integer.parseInt(choice));
                System.out.println("Book was removed successfully.\n");
            }
            else
            {
                System.out.println("Chosen action doesn't exist.\n");
            }
        }
        else
        {
            System.out.println("There are currently no books in stock.\n");
        }
    }

    public static boolean isInteger(String sequence)
    {
        try
        {
            Integer.parseInt(sequence);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }
}