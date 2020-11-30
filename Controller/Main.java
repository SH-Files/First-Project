package Controller;

import Repository.InMemoryStorage;
import Service.StudentService;
import Service.Validator;

import java.util.Scanner;

public class Main
{
    private static String choice;

    private static final String CREATE = "1";
    private static final String CREATESTUDENT = "1";
    private static final String CREATEBOOK = "2";

    private static final String UPDATE = "2";
    private static final String UPDATESTUDENT = "1";
    private static final String UPDATEBOOK = "2";
    private static final String CHANGEFIRSTNAME = "1";
    private static final String CHANGELASTNAME = "2";
    private static final String ADDBOOK = "3";
    private static final String REMOVEBOOK = "4";
    private static final String CHANGETITLE = "1";

    private static final String READ = "3";
    private static final String READSTUDENT = "1";
    private static final String READBOOKS = "2";

    private static final String DELETE = "4";
    private static final String DELETESTUDENT = "1";
    private static final String DELETEBOOK = "2";

    private static final String EXIT = "5";

    private static final Scanner scn = new Scanner(System.in);

    private static final StudentService stundentService = new StudentService(new InMemoryStorage());

    public static void main(String[] args)
    {
        while (true)
        {
            System.out.println("1 --> I want to create a student or a book.");
            System.out.println("2 --> I want to update a student or a book.");
            System.out.println("3 --> I want to get information about a student or the stored books.");
            System.out.println("4 --> I want to delete a student or a book.");
            System.out.println("5 --> I want to quit the game.");

            choice = scn.nextLine().trim();

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
            else if (choice.equals(DELETE))
            {
                delete();
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

        choice = scn.nextLine().trim();

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
        System.out.println("What's the firstname of the student?");

        String firstname = scn.nextLine().trim();

        if (firstname.length() > 2)
        {
            System.out.println("What's the lastname of the student?");

            String lastname = scn.nextLine().trim();

            if (lastname.length() > 2)
            {
                stundentService.createStudent(firstname, lastname);
                System.out.println("Student got added successfully.\n");
            }
            else
            {
                System.out.println("Student couldn't get created. Lastname of student has to contain at least three letters.\n");
            }
        }
        else
        {
            System.out.println("Student couldn't get created. Firstname of student has to contain at least three letters.\n");
        }
    }

    public static void createBook()
    {
        System.out.println("What's the title of the book?");

        String title = scn.nextLine().trim();

        if (!title.equals(""))
        {
            if (stundentService.createBook(title))
            {
                System.out.println("Book was added successfully.\n");
            }
            else
            {
                System.out.println("Book couldn't get added. Title already taken.\n");
            }
        }
        else
        {
            System.out.println("Title of book has to contain at least one letter.\n");
        }
    }

    public static void update()
    {
        if (stundentService.getBooks().size() > 0 || stundentService.getStudents().size() > 0)
        {
            System.out.println("1 --> I want to update a student.");
            System.out.println("2 --> I want to update a book.");

            choice = scn.nextLine().trim();

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
            System.out.println("There are currently no students and no books that can get updated.\n");
        }
    }

    public static void updateStudent()
    {
        if (stundentService.getStudents().size() > 0)
        {
            System.out.println("Which one of the following students do you want to update?");

            for (int i = 0; i < stundentService.getStudents().size(); i++)
            {
                System.out.println((i + 1) + " --> " + stundentService.getStudents().get(i).getFirstname() + " " + stundentService.getStudents().get(i).getLastname());
            }

            choice = scn.nextLine().trim();

            if (Validator.isInteger(choice) && Integer.parseInt(choice) > 0 && Integer.parseInt(choice) - 1 < stundentService.getStudents().size())
            {
                int studentIndex = Integer.parseInt(choice) - 1;

                while (true)
                {
                    System.out.println("Selected student: " + stundentService.getStudents().get(studentIndex).getLastname() + ", " + stundentService.getStudents().get(studentIndex).getFirstname() + "\n");

                    System.out.println("1 --> I want to change the firstname.");
                    System.out.println("2 --> I want to change the lastname.");
                    System.out.println("3 --> I want to add a book to the student.");
                    System.out.println("4 --> I want to remove a book from the student.");

                    choice = scn.nextLine().trim();

                    if (choice.equals(CHANGEFIRSTNAME))
                    {
                        updateStudentFirstname(studentIndex);
                    }
                    else if (choice.equals(CHANGELASTNAME))
                    {
                        updateStudentLastname(studentIndex);
                    }
                    else if (choice.equals(ADDBOOK))
                    {
                        updateStudentAddBook(studentIndex);
                    }
                    else if (choice.equals(REMOVEBOOK))
                    {
                        updateStudentRemoveBook(studentIndex);
                    }
                    else
                    {
                        System.out.println("Chosen action doesn't exist.\n");
                    }

                    System.out.println("Would you like to try it again or update something else of this student? (Y / N)");

                    if (!scn.nextLine().trim().toUpperCase().equals("Y"))
                    {
                        break;
                    }
                }
            }
            else
            {
                System.out.println("Chosen action doesn't exist.\n");
            }
        }
        else
        {
            System.out.println("There is currently no student which could get updated.\n");
        }
    }

    public static void updateStudentFirstname(int studentIndex)
    {
        System.out.println("What's the new firstname?");

        String firstname = scn.nextLine().trim();

        if (stundentService.updateStudentFirstname(studentIndex, firstname))
        {
            System.out.println("Firstname of chosen student got successfully updated.\n");
        }
        else
        {
            System.out.println("Firstname couldn't get updated. Firstname of student has to contain at least three letters.\n");
        }
    }

    public static void updateStudentLastname(int studentIndex)
    {
        System.out.println("What's the new lastname?");

        String lastname = scn.nextLine().trim();

        if (stundentService.updateStudentLastname(studentIndex, lastname))
        {
            System.out.println("Lastname of chosen student got successfully updated.\n");
        }
        else
        {
            System.out.println("Lastname couldn't get updated. Lastname of student has to contain at least three letters.\n");
        }
    }

    public static void updateStudentAddBook(int studentIndex)
    {
        if (stundentService.getBooks().size() > 0)
        {
            System.out.println("Which one of the following books do you want to add?");

            for (int i = 0; i < stundentService.getBooks().size(); i++)
            {
                System.out.println((i + 1) + " --> " + stundentService.getBooks().get(i).getTitle());
            }

            choice = scn.nextLine().trim();

            if (stundentService.updateStudentAddBook(studentIndex, choice))
            {
                System.out.println("Book got successfully added.\n");
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

    public static void updateStudentRemoveBook(int studentIndex)
    {
        if (stundentService.getStudents().get(studentIndex).getBooks().size() > 0)
        {
            System.out.println("Which one of the following books do you want to remove?");

            for (int i = 0; i < stundentService.getStudents().get(studentIndex).getBooks().size(); i++)
            {
                System.out.println((i + 1) + " --> " + stundentService.getStudents().get(studentIndex).getBooks().get(i).getTitle());
            }

            choice = scn.nextLine().trim();

            if (stundentService.updateStudentRemoveBook(studentIndex, choice))
            {
                System.out.println("Book got successfully removed.\n");
            }
            else
            {
                System.out.println("Chosen action doesn't exist.\n");
            }
        }
        else
        {
            System.out.println("The selected student doesn't have any books yet.\n");
        }
    }

    public static void updateBook()
    {
        if (stundentService.getBooks().size() > 0)
        {
            System.out.println("1 --> I want to change the title of a book.");

            choice = scn.nextLine().trim();

            if (choice.equals(CHANGETITLE))
            {
                updateBookTitle();
            }
            else
            {
                System.out.println("Chosen action doesn't exist.\n");
            }
        }
        else
        {
            System.out.println("There is currently no book which could get updated.\n");
        }
    }

    public static void updateBookTitle()
    {
        System.out.println("Which of the following books would you like to change the title of?");

        for (int i = 0; i < stundentService.getBooks().size(); i++)
        {
            System.out.println((i + 1) + " --> " + stundentService.getBooks().get(i).getTitle());
        }

        choice = scn.nextLine().trim();

        if (Validator.isInteger(choice) && Integer.parseInt(choice) > 0 && Integer.parseInt(choice) - 1 < stundentService.getBooks().size())
        {
            int bookIndex = Integer.parseInt(choice) - 1;

            System.out.println("What's the new title of the book?");

            String title = scn.nextLine().trim();

            if (!choice.equals(""))
            {
                if (stundentService.updateBookTitle(bookIndex, title))
                {
                    System.out.println("Title got successfully changed.\n");
                }
                else
                {
                    System.out.println("Title of book couldn't get changed. Title already taken.\n");
                }
            }
            else
            {
                System.out.println("Firstname of student has to contain at least three letters.\n");
            }
        }
        else
        {
            System.out.println("Chosen action doesn't exist.\n");
        }
    }

    public static void read()
    {

        System.out.println("1 --> I want to get information about a student.");
        System.out.println("2 --> I want to to get an overview of all books in stock.");

        choice = scn.nextLine().trim();

        if (choice.equals(READSTUDENT))
        {
            readStudent();
        }
        else if (choice.equals(READBOOKS))
        {
            stundentService.readBooks();
        }
        else
        {
            System.out.println("Chosen action doesn't exist.\n");
        }
    }

    public static void readStudent()
    {
        if (stundentService.getStudents().size() > 0)
        {
            System.out.println("From which of the following student do you want to get information of?");

            for (int i = 0; i < stundentService.getStudents().size(); i++)
            {
                System.out.println((i + 1) + " --> " + stundentService.getStudents().get(i).getFirstname() + " " + stundentService.getStudents().get(i).getLastname());
            }

            choice = scn.nextLine().trim();

            if (!stundentService.readStudent(choice))
            {
                System.out.println("Chosen action doesn't exist.\n");
            }
        }
        else
        {
            System.out.println("There are currently no students existing.\n");
        }
    }

    public static void delete()
    {
        System.out.println("1 --> I want to delete a student.");
        System.out.println("2 --> I want to delete a book.");

        choice = scn.nextLine().trim();

        if (choice.equals(DELETESTUDENT))
        {
            deleteStudent();
        }
        else if (choice.equals(DELETEBOOK))
        {
            deleteBook();
        }
        else
        {
            System.out.println("Chosen action doesn't exist.\n");
        }
    }

    public static void deleteStudent()
    {
        System.out.println("Which student do you want to delete?");

        for (int i = 0; i < stundentService.getStudents().size(); i++)
        {
            System.out.println((i + 1) + " --> " + stundentService.getStudents().get(i).getFirstname() + " " + stundentService.getStudents().get(i).getLastname());
        }

        choice = scn.nextLine().trim();

        if (stundentService.deleteStudent(choice))
        {
            System.out.println("Student got successfully deleted.\n");
        }
        else
        {
            System.out.println("Chosen action doesn't exist.\n");
        }
    }

    public static void deleteBook()
    {
        System.out.println("Which book do you want to delete?");

        for (int i = 0; i < stundentService.getBooks().size(); i++)
        {
            System.out.println((i + 1) + " --> " + stundentService.getBooks().get(i).getTitle());
        }

        choice = scn.nextLine().trim();

        if (stundentService.deleteBook(choice))
        {
            System.out.println("Book got successfully deleted.\n");
        }
        else
        {
            System.out.println("Chosen action doesn't exist.\n");
        }
    }
}