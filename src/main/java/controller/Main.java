package main.java.controller;

import main.java.entity.Student;
import main.java.repository.Storage;
import main.java.service.Service;

import java.util.Scanner;

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
        while (true)
        {
            System.out.println("Which one of the following actions would you like to choose?\n");

            System.out.println("1 --> I want to create a student or a book.");
            System.out.println("2 --> I want to update a student or a book.");
            System.out.println("3 --> I want to get information about a student or the books in stock.");
            System.out.println("4 --> I want to remove a student or a book.");
            System.out.println("5 --> I want to quit the game.");

            String choice = scn.nextLine().trim();

            if (choice.equals(CREATE))
            {
                create();
            }
            else if (choice.equals(UPDATE))
            {
                update();
            }
            else if (choice.equals(READ))
            {
                read();
            }
            else if (choice.equals(REMOVE))
            {
                remove();
            }
            else if (choice.equals(EXIT))
            {
                break;
            }
            else
            {
                System.out.println("Chosen action doesn't exist.\n");
            }
        }
        System.out.println("Goodbye and until next time :)");
    }

    public static void create()
    {
        System.out.println("1 --> I want to create a student.");
        System.out.println("2 --> I want to create a book.");

        String choice = scn.nextLine().trim();

        if (choice.equals(CREATESTUDENT))
        {
            createStudent();
        }
        else if (choice.equals(CREATEBOOK))
        {
            createBook();
        }
        else
        {
            System.out.println("Chosen action doesn't exist.\n");
        }
    }

    public static void createStudent()
    {
        System.out.println("What's the student's first name?");

        String firstName = scn.nextLine().trim();

        if (!firstName.isEmpty())
        {
            System.out.println("What's the student's last name?");

            String lastName = scn.nextLine().trim();

            if (!lastName.isEmpty())
            {
                service.createStudent(firstName, lastName);
                System.out.println("Student was successfully added.\n");
            }
            else
            {
                System.out.println("Student could not be added. The last name can't be empty.\n");
            }
        }
        else
        {
            System.out.println("Student could not be added. The first name can't be empty.\n");
        }
    }

    public static void createBook()
    {
        System.out.println("What's the book's title?");

        String title = scn.nextLine().trim();

        if (!title.isEmpty())
        {
            service.createBook(title);
            System.out.println("Book was successfully added.\n");
        }
        else
        {
            System.out.println("Book could not be added. The title can't be empty.\n");
        }
    }

    public static void read()
    {
        if (service.getStudents().size() > 0 || service.getBooks().size() > 0)
        {
            System.out.println("1 --> I want to get information about a student.");
            System.out.println("2 --> I want to get information about the books in stock.");

            String choice = scn.nextLine().trim();

            if (choice.equals(READSTUDENT))
            {
                readStudent();
            }
            else if (choice.equals(READBOOKS))
            {
                readBook();
            }
            else
            {
                System.out.println("Chosen action doesn't exist.\n");
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

            service.getStudents().forEach((key, value) -> System.out.println(key + " --> " + value.getFirstName() + " " + value.getLastName()));

            String choice = scn.nextLine().trim();

            if (isInteger(choice) && service.getStudents().containsKey(Integer.parseInt(choice)))
            {
                Student student = service.getStudent(Integer.parseInt(choice));

                System.out.println("Student's name: " + student.getLastName() + ", " + student.getFirstName() + "\n");

                System.out.println("Books in possession:");

                if (student.getBooks().size() > 0)
                {
                    student.getBooks().forEach((key, value) -> System.out.println("\u2022\t" + value.getTitle() + " (ID: " + value.getId() + ")"));
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

            service.getBooks().forEach((key, value) -> System.out.println("\u2022\t" + value.getTitle() + " (ID: " + value.getId() + ")"));
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

            if (choice.equals(UPDATESTUDENT))
            {
                updateStudent();
            }
            else if (choice.equals(UPDATEBOOK))
            {
                updateBook();
            }
            else
            {
                System.out.println("Chosen action doesn't exist.\n");
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

        service.getStudents().forEach((key, value) -> System.out.println(key + " --> " + value.getFirstName() + " " + value.getLastName()));

        String choice = scn.nextLine().trim();

        if (isInteger(choice) && service.getStudents().containsKey(Integer.parseInt(choice)))
        {
            int key = Integer.parseInt(choice);

            do
            {
                System.out.println("Chosen student: " + service.getStudent(key).getLastName() + ", " + service.getStudent(key).getFirstName() + "\n");

                System.out.println("Which one of the following actions would you like to choose?\n");

                System.out.println("1 --> I want to update the student's first name.");
                System.out.println("2 --> I want to update the student's last name.");
                System.out.println("3 --> I want to add a book to the student.");
                System.out.println("4 --> I want to remove a book from the student.");

                choice = scn.nextLine().trim();

                if (choice.equals(UPDATESTUDENTFIRSTNAME))
                {
                    updateStudentFirstName(key);
                }
                else if (choice.equals(UPDATESTUDENTLASTNAME))
                {
                    updateStudentLastName(key);
                }
                else if (choice.equals(UPDATESTUDENTADDBOOK))
                {
                    updateStudentAddBook(key);
                }
                else if (choice.equals(UPDATESTUDENTREMOVEBOOK))
                {
                    updateStudentRemoveBook(key);
                }
                else
                {
                    System.out.println("Chosen action doesn't exist.\n");
                }

                System.out.println("Would you like to update something else? (Y / N)");

                choice = scn.nextLine().trim();

            } while (choice.equalsIgnoreCase("Y"));
        }
        else
        {
            System.out.println("Chosen action doesn't exist.\n");
        }
    }

    public static void updateStudentFirstName(int key)
    {
        System.out.println("What's the student's new first name?");

        String firstName = scn.nextLine().trim();

        if (!firstName.isEmpty())
        {
            service.updateStudentFirstName(key, firstName);
            System.out.println("Student's first name was successfully updated.\n");
        }
        else
        {
            System.out.println("Student could not be updated. The first name can't be empty.\n");
        }
    }

    public static void updateStudentLastName(int key)
    {
        System.out.println("What's the student's new last name?");

        String lastName = scn.nextLine().trim();

        if (!lastName.isEmpty())
        {
            service.updateStudentLastName(key, lastName);
            System.out.println("Student's last name was successfully updated.\n");
        }
        else
        {
            System.out.println("Student could not be updated. The last name can't be empty.\n");
        }
    }

    public static void updateStudentAddBook(int key)
    {
        if (service.getBooks().size() > 0)
        {
            System.out.println("Choose one of the following books:");

            service.getBooks().forEach((key2, value) -> System.out.println(key2 + " --> " + value.getTitle()));

            String choice = scn.nextLine().trim();

            if (isInteger(choice) && service.getBooks().containsKey(Integer.parseInt(choice)))
            {
                service.updateStudentAddBook(key, Integer.parseInt(choice));
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

    public static void updateStudentRemoveBook(int key)
    {
        if (service.getStudent(key).getBooks().size() > 0)
        {
            System.out.println("Choose one of the following books:");

            service.getStudent(key).getBooks().forEach((key2, value) -> System.out.println(key2 + " --> " + value.getTitle()));

            String choice = scn.nextLine().trim();

            if (isInteger(choice) && service.getStudent(key).getBooks().containsKey(Integer.parseInt(choice)))
            {
                service.updateStudentRemoveBook(key, Integer.parseInt(choice));
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

        service.getBooks().forEach((key, value) -> System.out.println(key + " --> " + value.getTitle()));

        String choice = scn.nextLine().trim();

        if (isInteger(choice) && service.getBooks().containsKey(Integer.parseInt(choice)))
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
                System.out.println("Book could not be added. The title can't be empty.\n");
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

            if (choice.equals(REMOVESTUDENT))
            {
                readStudent();
            }
            else if (choice.equals(REMOVEBOOK))
            {
                readBook();
            }
            else
            {
                System.out.println("Chosen action doesn't exist.\n");
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

            service.getStudents().forEach((key, value) -> System.out.println(key + " --> " + value.getFirstName() + " " + value.getLastName()));

            String choice = scn.nextLine().trim();

            if (isInteger(choice) && service.getStudents().containsKey(Integer.parseInt(choice)))
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

            service.getBooks().forEach((key, value) -> System.out.println(key + " --> " + value.getTitle()));

            String choice = scn.nextLine().trim();

            if (isInteger(choice) && service.getBooks().containsKey(Integer.parseInt(choice)))
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