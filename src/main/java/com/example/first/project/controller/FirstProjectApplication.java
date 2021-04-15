package com.example.first.project.controller;

import com.example.first.project.config.AppConfig;
import com.example.first.project.entity.Student;
import com.example.first.project.enumeration.Action;


import com.example.first.project.service.StudentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.example.first.project.exception.BookNotFoundException;
import com.example.first.project.exception.StudentNotFoundException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

@SpringBootApplication
@RestController
public class FirstProjectApplication {

	private static final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	private static final StudentService studentService = (StudentService) context.getBean("studentService");

	private final static Scanner scn = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(FirstProjectApplication.class, args);

		while (true) {
			System.out.println("Which one of the following actions do you choose?\n");

			System.out.println("1 --> I want to add a new student.");
			System.out.println("2 --> I want to get information about a student.");
			System.out.println("3 --> I want to update a student.");
			System.out.println("4 --> I want to remove a new student.");
			System.out.println("5 --> I want to quit the game.");

			String choice = scn.nextLine().trim();

			if (choice.equals(Action.CREATE.getCode())) {
				createStudent();
			} else if (choice.equals(Action.READ.getCode())) {
				readStudent();
			} else if (choice.equals(Action.UPDATE.getCode())) {
				updateStudent();
			} else if (choice.equals(Action.REMOVE.getCode())) {
				removeStudent();
			} else if (choice.equals(Action.QUIT.getCode())) {
				break;
			} else {
				System.out.println("Chosen action doesn't exist.\n");
			}
		}
		System.out.println("Goodbye and until next time :)");
	}

	@GetMapping
	public String helloWorld() {
		return "Hello World!";
	}

	public static void createStudent() {
		System.out.println("What's the student's first name?");

		String firstName = scn.nextLine().trim();

		if (!firstName.isEmpty()) {
			System.out.println("What's the student's last name?");

			String lastName = scn.nextLine().trim();

			if (!lastName.isEmpty()) {
				studentService.createStudent(firstName, lastName);
				System.out.println("Student was created successfully.\n");
			} else {
				System.out.println("Student couldn't be created. Last name cannot be empty.\n");
			}
		} else {
			System.out.println("Student couldn't be created. First name cannot be empty.\n");
		}
	}

	public static void readStudent() {
		if (studentService.getStudents().size() > 0) {
			System.out.println("Which one of the following students would you like to receive information about?");

			studentService.getStudents().forEach((k, v) -> System.out.println(k + " --> " + v.getFirstName() + " " + v.getLastName()));

			String studentKey = scn.nextLine().trim();

			try {
				Student student = studentService.getStudent(studentKey);

				System.out.println("Chosen student's name: " + student.getLastName() + ", " + student.getFirstName() + "\n");
				System.out.println("Books in possession:");

				if (student.getBooks().size() > 0) {
					student.getBooks().forEach(o -> System.out.println("\u2022\t" + o.getTitle() + " (ID: " + o.getId() + ")"));
				} else {
					System.out.println("Student has currently no books in possession.");
				}
				System.out.println();
			} catch (StudentNotFoundException e) {
				System.out.println("Chosen action doesn't exist.\n");
			}
		} else {
			System.out.println("There are currently no students that could be read.\n");
		}
	}

	public static void updateStudent() {
		if (studentService.getStudents().size() > 0) {
			System.out.println("Which one of the following students do you want to update?");

			studentService.getStudents().forEach((k, v) -> System.out.println(k + " --> " + v.getFirstName() + " " + v.getLastName()));

			String studentKey = scn.nextLine().trim();

			try {
				Student student = studentService.getStudent(studentKey);

				do {
					System.out.println("Chosen student's name: " + student.getLastName() + ", " + student.getFirstName() + "\n");

					System.out.println("Which one of the following actions would you like to choose?\n");

					System.out.println("1 --> I want to update the student's first name.");
					System.out.println("2 --> I want to update the student's last name.");
					System.out.println("3 --> I want to update the student's books.");

					String choice = scn.nextLine().trim();

					if (choice.equals(Action.UPDATESTUDENTFIRSTNAME.getCode())) {
						updateStudentFirstName(student);
					} else if (choice.equals(Action.UPDATESTUDENTLASTNAME.getCode())) {
						updateStudentLastName(student);
					} else if (choice.equals(Action.UPDATESTUDENTBOOKS.getCode())) {
						updateStudentBooks(student);
					} else {
						System.out.println("Chosen action doesn't exist.\n");
					}

					System.out.println("Do you want to keep updating the student? (Y / N)");

				} while (scn.nextLine().trim().equalsIgnoreCase("Y"));
			} catch (StudentNotFoundException e) {
				System.out.println("Chosen action doesn't exist.\n");
			}
		} else {
			System.out.println("There are currently no students that could be updated.\n");
		}
	}

	public static void updateStudentFirstName(Student student) {
		System.out.println("What's the student's new first name?");

		String newFirstName = scn.nextLine().trim();

		if (!newFirstName.isEmpty()) {
			studentService.updateStudentFirstName(student.getId(), newFirstName);
			System.out.println("Student's first name was updated successfully.\n");
		} else {
			System.out.println("Student couldn't be updated. First name can't be empty.\n");
		}
	}

	public static void updateStudentLastName(Student student) {
		System.out.println("What's the student's new last name?");

		String newLastName = scn.nextLine().trim();

		if (!newLastName.isEmpty()) {
			studentService.updateStudentLastName(student.getId(), newLastName);
			System.out.println("Student's last name was updated successfully.\n");
		} else {
			System.out.println("Student couldn't be updated. Last name can't be empty.\n");
		}
	}

	public static void updateStudentBooks(Student student) {
		do {
			System.out.println("1 --> I want to update the title of a book.");
			System.out.println("2 --> I want to add a book to the student.");
			System.out.println("3 --> I want to remove a book from the student.");

			String choice = scn.nextLine().trim();

			if (choice.equals(Action.UPDATESTUDENTBOOKSBOOKTITLE.getCode())) {
				updateStudentBooksBookTitle(student);
			} else if (choice.equals(Action.UPDATESTUDENTBOOKSADDBOOK.getCode())) {
				updateStudentBooksAddBook(student);
			} else if (choice.equals(Action.UPDATESTUDENTBOOKSREMOVEBOOK.getCode())) {
				updateStudentBooksRemoveBook(student);
			} else {
				System.out.println("Chosen action doesn't exist.\n");
			}
			System.out.println("Do you want to keep updating the student's books? (Y / N)");

		} while (scn.nextLine().trim().equalsIgnoreCase("Y"));
	}

	public static void updateStudentBooksBookTitle(Student student) {
		if (student.getBooks().size() > 0) {
			System.out.println("Which of the following books do you want to update the title of?");

			student.getBooks().forEach(o -> System.out.println(o.getId() + " --> " + o.getTitle()));

			String bookKey = scn.nextLine().trim();

			System.out.println("What's the book's new title?");

			String newTitle = scn.nextLine().trim();

			try {
				studentService.updateBookTitle(student.getId(), bookKey, newTitle);
				System.out.println("Book was successfully updated.\n");
			} catch (BookNotFoundException e) {
				System.out.println("Chosen action doesn't exist.\n");
			}
		} else {
			System.out.println("Chosen student has no books that could be updated.\n");
		}
	}

	public static void updateStudentBooksAddBook(Student student) {
		System.out.println("What's the book's title?");

		String title = scn.nextLine().trim();

		if (!title.isEmpty()) {
			studentService.addBookToStudent(student.getId(), title);
			System.out.println("Book was added successfully.\n");
		} else {
			System.out.println("Book couldn't be added. Title cannot be empty.\n");
		}
	}

	public static void updateStudentBooksRemoveBook(Student student) {
		if (student.getBooks().size() > 0) {
			System.out.println("Which one of the following books do you want to remove?");

			student.getBooks().forEach(o -> System.out.println(o.getId() + " --> " + o.getTitle()));

			String bookKey = scn.nextLine().trim();

			try {
				studentService.removeBookFromStudent(student.getId(), bookKey);
				System.out.println("Book was successfully removed.\n");
			} catch (BookNotFoundException e) {
				System.out.println("Chosen action doesn't exist.\n");
			}
		} else {
			System.out.println("Chosen student has no books that could be removed.\n");
		}
	}

	public static void removeStudent() {
		if (studentService.getStudents().size() > 0) {
			System.out.println("Which one of the following students do you want to remove?");

			studentService.getStudents().forEach((k, v) -> System.out.println(k + " --> " + v.getFirstName() + " " + v.getLastName()));

			String studentKey = scn.nextLine().trim();

			try {
				studentService.removeStudent(studentKey);
				System.out.println("Student was successfully removed.\n");
			} catch (StudentNotFoundException e) {
				System.out.println("Chosen action doesn't exist.\n");
			}
		} else {
			System.out.println("There are currently no students that could be removed.\n");
		}
	}



}