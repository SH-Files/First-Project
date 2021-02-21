package controller;

import entity.Student;
import exception.StudentNotFoundException;
import repository.Storage;
import service.Service;

import java.util.Scanner;

public class Main {
    private static final String CREATE = "1";
    private static final String READ = "2";
    private static final String UPDATE = "3";
    private static final String REMOVE = "4";
    private static final String QUIT = "5";

    private static final String UPDATESTUDENTFIRSTNAME = "1";
    private static final String UPDATESTUDENTLASTNAME = "2";
    private static final String UPDATESTUDENTADDBOOK = "3";
    private static final String UPDATESTUDENTREMOVEBOOK = "4";

    private static final Scanner scn = new Scanner(System.in);
    private static final Service service = new Service(new Storage());

    public static void main(String[] args) {
        while (true) {
            System.out.println("1 --> I want to add a new student.");
            System.out.println("2 --> I want to get information about a student.");
            System.out.println("3 --> I want to update a student.");
            System.out.println("4 --> I want to remove a new student.");
            System.out.println("5 --> I want to quit the game.");

            String choice = scn.nextLine().trim();

            if (choice.equals(CREATE)) {
                createStudent();
            } else if (choice.equals(READ)) {
                readStudent();
            } else if (choice.equals(UPDATE)) {
                updateStudent();
            } else if (choice.equals(REMOVE)) {
                removeStudent();
            } else if (choice.equals(QUIT)) {
                break;
            } else {
                System.out.println("Chosen action doesn't exist.\n");
            }
        }
        System.out.println("Goodbye and until next time :)");
    }

    public static void createStudent() {
        System.out.println("What's the first name of the student?");

        String firstName = scn.nextLine().trim();

        if (!firstName.isEmpty()) {
            System.out.println("What's the last name of the student?");

            String lastName = scn.nextLine().trim();

            if (!lastName.isEmpty()) {
                service.createStudent(firstName, lastName);
                System.out.println("Student was created successfully.\n");
            } else {
                System.out.println("Student couldn't be created. Last name cannot be empty.\n");
            }
        } else {
            System.out.println("Student couldn't be created. First name cannot be empty.\n");
        }
    }

    public static void readStudent()
    {
        if (service.getStudents().size() > 0) {
            System.out.println("Of which of the following students do you want to get information about?");

            service.getStudents().forEach((k, v) -> System.out.println(k + " --> " + v.getFirstName() + " " + v.getFirstName()));

            String choice = scn.nextLine().trim();

            try {
                Student student = service.getStudent(choice);

                System.out.println("Chosen student's name: " + student.getLastName() + ", " + student.getFirstName() + "\n");
                System.out.println("Books in possession:");

                if (student.getBooks().size() > 0) {
                    student.getBooks().forEach(o -> System.out.println("\u2022\t" + o.getTitle() + " (ID:" + o.getId() + ")"));
                } else {
                    System.out.println("Chosen student has no books in possession yet.");
                }
                System.out.println();
            } catch (StudentNotFoundException e) {
                System.out.println("Chosen action doesn't exist\n");
            }
        } else {
            System.out.println("There are currently no students who could be read.\n");
        }
    }

    public static void updateStudent() {
        if (service.getStudents().size() > 0) {
            System.out.println("Which one of the following students do you want to edit?");

            service.getStudents().forEach((k, v) -> System.out.println(k + " --> " + v.getFirstName() + " " + v.getLastName()));

            String choice = scn.nextLine().trim();

            try {
                Student student = service.getStudent(choice);

                do {
                    System.out.println("Chosen student's name: " + student.getLastName() + ", " + student.getFirstName() + "\n");

                    System.out.println("Which one of the following actions would you like to choose?\n");

                    System.out.println("1 --> I want to update the student's first name.");
                    System.out.println("2 --> I want to update the student's last name.");
                    System.out.println("3 --> I want to add a book to the student.");
                    System.out.println("4 --> I want to remove a book from the student.");

                    choice = scn.nextLine().trim();

                    if (choice.equals(UPDATESTUDENTFIRSTNAME)) {
                        updateStudentFirstName(student);
                    } else if (choice.equals(UPDATESTUDENTLASTNAME)) {
                        updateStudentLastName(student);
                    } else if (choice.equals(UPDATESTUDENTADDBOOK)) {
                        updateStudentAddBook(student);
                    } else if (choice.equals(UPDATESTUDENTREMOVEBOOK)) {
                        updateStudentRemoveBook(student);
                    } else {
                        System.out.println("Chosen action doesn't exist\n");
                    }

                    System.out.println("Do you want to update something else? (Y / N)");

                    choice = scn.nextLine().trim();

                } while (choice.equalsIgnoreCase("Y"));
            } catch (StudentNotFoundException e) {
                System.out.println("Chosen action doesn't exist\n");
            }
        } else {
            System.out.println("There are currently no students who could be updated.\n");
        }
    }

    public static void updateStudentFirstName(Student student) {
        System.out.println("What's the student's new first name?");

        String newFirstName = scn.nextLine().trim();

        if (!newFirstName.isEmpty()) {
            service.updateStudentFirstName(student.getId(), newFirstName);
            System.out.println("Student's first name was updated successfully.\n");
        } else {
            System.out.println("Student couldn't be updated. First name can't be empty.\n");
        }
    }

    public static void updateStudentLastName(Student student) {
        System.out.println("What's the student's new last name?");

        String newLastName = scn.nextLine().trim();

        if (!newLastName.isEmpty()) {
            service.updateStudentLastName(student.getId(), newLastName);
            System.out.println("Student's last name was updated successfully.\n");
        } else {
            System.out.println("Student couldn't be updated. Last name can't be empty.\n");
        }
    }

    public static void updateStudentAddBook(Student student) {
        System.out.println("What's the book's title?");

        String title = scn.nextLine().trim();

        if (!title.isEmpty()) {
            service.addBookToStudent(student.getId(), title);
            System.out.println("Book was added successfully.\n");
        } else {
            System.out.println("Book couldn't be created. Title cannot be empty.\n");
        }
    }

    public static void updateStudentRemoveBook(Student student) {
        if (student.getBooks().size() > 0) {
            System.out.println("Which one of the following books do you want to remove?");

            student.getBooks().forEach(o -> System.out.println(o.getId() + " --> " + o.getTitle()));

            String choice = scn.nextLine().trim();

            service.removeBookFromStudent(student.getId(), choice);
            System.out.println("Book was successfully removed.\n");
        } else {
            System.out.println("No books can be removed. Chosen student has no books yet.\n");
        }
    }

    public static void removeStudent() {
        if (service.getStudents().size() > 0) {
            System.out.println("Which one of the following students do you want to remove?");

            service.getStudents().forEach((k, v) -> System.out.println(k + " --> " + v.getFirstName() + " " + v.getLastName()));

            String choice = scn.nextLine().trim();

            try {
                service.removeStudent(choice);
            } catch (StudentNotFoundException e) {
                System.out.println("Chosen action doesn't exist.\n");
            }
        } else {
            System.out.println("There are currently no students who could be removed.\n");
        }
    }
}
