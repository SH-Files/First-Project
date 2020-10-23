import java.util.Scanner;
import java.util.TreeMap;

public class Main
{
    static Scanner scn = new Scanner(System.in);                    // Scanner to get user input
    static TreeMap<Integer, Course> courses = new TreeMap<Integer, Course>();      // Dictionary to save all existing courses
    static TreeMap<Integer, Student> students = new TreeMap<Integer, Student>();    // Dictionary to save all existing students

    public static void main(String[] args)
    {
        while (true)
        {
            System.out.println("Which one of the following actions do you want to choose?");

            System.out.println("1 --> Create a new student or a new course.");
            System.out.println("2 --> Get information about a student or a course.");
            System.out.println("3 --> Update information of a student or a course.");
            System.out.println("4 --> Delete a Student or course.");
            System.out.println("5 --> Exit the game.");

            String choice = scn.nextLine().trim();

            if (choice.equals("1"))
            {
                create();
            }
            else if (choice.equals("2"))
            {
                read();
            }
            else if (choice.equals("3"))
            {
                update();
            }
            else if (choice.equals("4"))
            {
                delete();
            }
            else if (choice.equals("5"))
            {
                break;
            }
            else
            {
                System.out.println("Chosen action doesn't exist. Try it again.\n");
            }
        }
        System.out.println("Goodbye and until next time :)");
    }

    public static void create()
    {
        System.out.println("1 --> Create a new student.");
        System.out.println("2 --> Create a new course.");

        String choice = scn.nextLine().trim();

        if (choice.equals("1"))
        {
            createStudent();
        }
        else if (choice.equals("2"))
        {
            createCourse();
        }
        else
        {
            System.out.println("Chosen action doesn't exist. You are getting redirected to the start.\n");
        }
    }

    public static void createStudent()
    {
        try
        {
            System.out.println("What's the student's name? (e.g. John Doe)");
            String name = scn.nextLine().trim();

            if (students.isEmpty())
            {
                students.put(0, new Student(name.split(" ")[0], name.split(" ")[1]));
            }
            else
            {
                students.put(students.lastKey() + 1, new Student(name.split(" ")[0], name.split(" ")[1]));
            }
            System.out.println("Student was added successfully.\n");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Student couldn't get added. He / She needs a first and a last name.\n");
        }
    }

    public static void createCourse()
    {
        System.out.println("What's the name of the course? (must contain at least three characters)");
        String name = scn.nextLine().trim();

        if (name.length() > 2)
        {
            if (courses.size() > 0)
            {
                for (int key : courses.keySet())
                {
                    if (courses.get(key).getName().equals(name))
                    {
                        System.out.println("Course couldn't get added. Chosen name is already taken.\n");
                        break;
                    }
                    else if (key == courses.lastKey())
                    {
                        System.out.println("What's the description of the course?");
                        String description = scn.nextLine().trim();

                        Course course = new Course(name);
                        course.setDescription(description);

                        if (courses.isEmpty())
                        {
                            courses.put(0, course);
                        }
                        else
                        {
                            courses.put(courses.lastKey() + 1, course);
                        }
                        System.out.println("Course was added successfully.\n");
                    }
                }
            }
            else
            {
                System.out.println("What's the description of the course?");
                String description = scn.nextLine().trim();

                Course course = new Course(name);
                course.setDescription(description);

                if (courses.isEmpty())
                {
                    courses.put(0, course);
                }
                else
                {
                    courses.put(courses.lastKey() + 1, course);
                }
                System.out.println("Course was added successfully.\n");
            }
        }
        else
        {
            System.out.println("Course couldn't get added.");
            System.out.println("Course does need a name with at least three letters.\n");
        }
    }

    public static void read()
    {
        System.out.println("1 --> Get information about a student.");
        System.out.println("2 --> Get information about the courses.");

        String choice = scn.nextLine().trim();

        if (choice.equals("1"))
        {
            readStudent();
        }
        else if (choice.equals("2"))
        {
            readCourse();
        }
        else
        {
            System.out.println("Chosen action doesn't exist. You are getting redirected to the start.\n");
        }
    }

    public static void readStudent()
    {
        if (students.isEmpty())
        {
            System.out.println("It doesn't exist any student yet.\n");
        }
        else
        {
            try
            {
                System.out.println("Choose one of the following students:");

                for (int i = 0; i < students.size(); i++)
                {
                    System.out.println((i + 1) + " --> " + students.get(i).getFirstname() + " " + students.get(i).getLastname());
                }

                int key = (int)students.keySet().toArray()[Integer.parseInt(scn.nextLine().trim()) - 1];

                System.out.println("Name: " + students.get(key).getLastname() + ", " + students.get(key).getFirstname() + "\n");

                System.out.println("Linked courses:");

                if (students.get(key).getCourses().isEmpty())
                {
                    System.out.println("The student is currently not participating in any course.\n");
                }
                else
                {
                    for (int key2 : students.get(key).getCourses().keySet())
                    {
                        System.out.println("\u00B7 " + students.get(key).getCourses().get(key2).getName());
                    }
                    System.out.print("\n");
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Chosen action doesn't exist. You are getting redirected to the start.\n");
            }
        }
    }

    public static void readCourse()
    {
        if (courses.isEmpty())
        {
            System.out.println("It doesn't exist any course yet.\n");
        }
        else
        {
            System.out.println("Following courses are currently existing:");

            for (int key : courses.keySet())
            {
                if (courses.get(key).getDescription().equals(""))
                {
                    System.out.println("\u00B7 " + courses.get(key).getName());
                }
                else
                {
                    System.out.println("\u00B7 " + courses.get(key).getName() + " - " + courses.get(key).getDescription());
                }
            }
            System.out.println();
        }
    }

    public static void update()
    {
        System.out.println("1 --> Update a student.");
        System.out.println("2 --> Update a course.");

        String choice = scn.nextLine().trim();

        if (choice.equals("1"))
        {
            updateStudent();
        }
        else if (choice.equals("2"))
        {
            updateCourse();
        }
        else
        {
            System.out.println("Chosen action doesn't exist. You are getting redirected to the start.\n");
        }
    }

    public static void updateStudent()
    {
        if (students.isEmpty())
        {
            System.out.println("It doesn't exist any student which could get updated.\n");
        }
        else
        {
            try
            {
                System.out.println("Which one of the following students do you wanna update:");

                for (int i = 0; i < students.size(); i++)
                {
                    System.out.println((i + 1) + " --> " + students.get(i).getFirstname() + " " + students.get(i).getLastname());
                }

                int key = (int)students.keySet().toArray()[Integer.parseInt(scn.nextLine().trim()) - 1];

                while (true)
                {
                    System.out.println("Selected student: " + students.get(key).getLastname() + ", " + students.get(key).getFirstname() + "\n");

                    System.out.println("1 --> Change the firstname.");
                    System.out.println("2 --> Change the lastname.");
                    System.out.println("3 --> Add the student to a course.");
                    System.out.println("4 --> Remove the student from a course.");

                    String choice = scn.nextLine().trim();

                    if (choice.equals("1"))
                    {
                        updateStudentFirstname(key);
                    }
                    else if (choice.equals("2"))
                    {
                        updateStudentLastname(key);
                    }
                    else if (choice.equals("3"))
                    {
                        updateStudentAddCourse(key);
                    }
                    else if (choice.equals("4"))
                    {
                        updateStudentRemoveCourse(key);
                    }

                    System.out.println("Do you want to update something else of this student? (Y / N)");

                    if (!scn.nextLine().trim().toUpperCase().equals("Y"))
                    {
                        break;
                    }
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Chosen action doesn't exist. Try it again.\n");
            }
        }
    }

    public static void updateStudentFirstname(int key)
    {
        String firstname = students.get(key).getFirstname();

        System.out.println("What should be the new firstname of the student?");

        String choice = scn.nextLine().trim();

        if (choice.length() >= 3)
        {
            students.get(key).setFirstname(choice);
            System.out.println("The student \"" + firstname + " " + students.get(key).getLastname() + "\" was successfully renamed to \"" + students.get(key).getFirstname() + " " + students.get(key).getLastname() + "\".\n");
        }
        else
        {
            System.out.println("The firstname couldn't get updated. The firstname must consist of at least three letters.\n");
        }
    }

    public static void updateStudentLastname(int key)
    {
        String lastname = students.get(key).getLastname();

        System.out.println("What should be the new lastname of the student?");

        String choice = scn.nextLine().trim();

        if (choice.length() >= 3)
        {
            students.get(key).setLastname(choice);
            System.out.println("The student \"" + students.get(key).getFirstname() + " " + lastname + "\" was successfully renamed to \"" + students.get(key).getFirstname() + " " + students.get(key).getLastname() + "\".\n");
        }
        else
        {
            System.out.println("The lastname couldn't get updated. The firstname must consist of at least three letters.\n");
        }
    }

    public static void updateStudentAddCourse(int key)
    {
        if (courses.isEmpty())
        {
            System.out.println("There exist no course yet.\n");
        }
        else
        {
            System.out.println("Choose one of the courses that you want to add:");

            for (int i = 0; i < courses.size(); i++)
            {
                if (courses.get(i).getDescription().equals(""))
                {
                    System.out.println((i + 1) + " --> " + courses.get(i).getName());
                }
                else
                {
                    System.out.println((i + 1) + " --> " + courses.get(i).getName() + " - " + courses.get(i).getDescription());
                }
            }

            int key2 = (int)courses.keySet().toArray()[Integer.parseInt(scn.nextLine().trim()) - 1];

            if (students.get(key).getCourses().containsKey(key2))
            {
                System.out.println("The selected student is already part of the course \"" + courses.get(key2).getName() + "\".\n");
            }
            else
            {
                students.get(key).addCourse(key2, courses.get(key2));
                System.out.println("The course \"" + courses.get(key2).getName() + "\" was successfully added.\n");
            }
        }
    }

    public static void updateStudentRemoveCourse(int key)
    {
        if (students.get(key).getCourses().isEmpty())
        {
            System.out.println("The selected student is currently not participating in any course.\n");
        }
        else
        {
            System.out.println("Choose one of the following courses that you like to remove from the student:");

            for (int i = 0; i < students.get(key).getCourses().size(); i++)
            {
                int key2 = (int)students.get(key).getCourses().keySet().toArray()[i];

                if (students.get(key).getCourses().get(key2).getDescription().equals(""))
                {
                    System.out.println((i + 1) + " --> " + students.get(key).getCourses().get(key2).getName());
                }
                else
                {
                    System.out.println((i + 1) + " --> " + students.get(key).getCourses().get(key2).getName() + " - " + students.get(key).getCourses().get(key2).getDescription());
                }
            }

            int key2 = (int) students.get(key).getCourses().keySet().toArray()[Integer.parseInt(scn.nextLine().trim()) - 1];

            System.out.println("The course \"" + students.get(key).getCourses().get(key2).getName() + "\" was successfully removed.\n");
            students.get(key).getCourses().remove(key2);
        }
    }

    public static void updateCourse()
    {
        if (courses.isEmpty())
        {
            System.out.println("It doesn't exist any course which could get updated.\n");
        }
        else
        {
            try
            {
                System.out.println("Choose one of the following courses:");

                for (int i = 0; i < courses.size(); i++)
                {
                    int key = (int)courses.keySet().toArray()[i];

                    if (courses.get(key).getDescription().equals(""))
                    {
                        System.out.println((i + 1) + " --> " + courses.get(key).getName());
                    }
                    else
                    {
                        System.out.println((i + 1) + " --> " + courses.get(key).getName() + " - " + courses.get(key).getDescription());
                    }
                }

                int key = (int)courses.keySet().toArray()[Integer.parseInt(scn.nextLine().trim()) - 1];

                while (true)
                {
                    if (courses.get(key).getDescription().equals(""))
                    {
                        System.out.println("Selected course: " + courses.get(key).getName() + "\n");
                    }
                    else
                    {
                        System.out.println("Selected course: " + courses.get(key).getName() + " - " + courses.get(key).getDescription() + "\n");
                    }

                    System.out.println("1 --> Change the name.");
                    System.out.println("2 --> Change the description.");

                    String choice = scn.nextLine().trim();

                    if (choice.equals("1"))
                    {
                        updateCourseName(key);
                    }
                    else if (choice.equals("2"))
                    {
                        updateCourseDescription(key);
                    }

                    System.out.println("Do you want to update something else of this student? (Y / N)");

                    if (!scn.nextLine().trim().toUpperCase().equals("Y"))
                    {
                        break;
                    }
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Chosen action doesn't exist. Try it again.\n");
            }
        }
    }

    public static void updateCourseName(int key)
    {
        System.out.println("What should be the new name of the course?");

        String name = scn.nextLine().trim();

        for (int key2 : courses.keySet())
        {
            if (courses.get(key).getName().equals(name))
            {
                System.out.println("Course couldn't get updated. Name is already taken.\n");
                break;
            }
            else if (key2 == courses.lastKey())
            {
                courses.get(key).setName(name);
                System.out.println("The course name was successfully updated.\n");
            }
        }
    }

    public static void updateCourseDescription(int key)
    {
        System.out.println("What should be the new description of the course? (Leave the filed empty to delete the current description)");

        String description = scn.nextLine().trim();

        courses.get(key).setDescription(description);

        if (description.equals(""))
        {
            System.out.println("Description was successfully deleted.\n");
        }
        else
        {
            System.out.println("Description was successfully updated.\n");
        }
    }

    public static void delete()
    {
        System.out.println("1 --> Delete a student.");
        System.out.println("2 --> Delete a course.");

        String choice = scn.nextLine().trim();

        if (choice.equals("1"))
        {
            deleteStudent();
        }
        else if (choice.equals("2"))
        {
            deleteCourse();
        }
        else
        {
            System.out.println("Chosen action doesn't exist. You are getting redirected to the start.\n");
        }
    }

    public static void deleteStudent()
    {
        if (students.isEmpty())
        {
            System.out.println("It doesn't exist any student which could get deleted.\n");
        }
        else
        {
            try
            {
                System.out.println("Choose one of the following students:");

                for (int i = 0; i < students.size(); i++)
                {
                    System.out.println((i + 1) + " --> " + students.get(i).getFirstname() + " " + students.get(i).getLastname());
                }

                int key = (int)students.keySet().toArray()[Integer.parseInt(scn.nextLine().trim()) - 1];

                System.out.println("Are you sure you want to delete the student \"" + students.get(key).getFirstname() + " " + students.get(key).getLastname() + "\"? (Y / N)");

                if (!scn.nextLine().trim().toUpperCase().equals("Y"))
                {
                    System.out.println("Delete process canceled.\n");
                }
                else
                {
                    String firstname = students.get(key).getFirstname();
                    String lastname = students.get(key).getLastname();

                    students.remove(key);

                    System.out.println("\"" + firstname + " " + lastname + "\" was successfully removed.\n");
                }
            }
            catch (IndexOutOfBoundsException e)
            {
                System.out.println("Chosen action doesn't exist. You are getting redirected to the start.\n");
            }
        }
    }

    public static void deleteCourse()
    {
        if (courses.isEmpty())
        {
            System.out.println("It doesn't exist any course which could get deleted.\n");
        }
        else
        {
            try
            {
                System.out.println("Choose one of the following courses:");

                for (int i = 0; i < courses.size(); i++)
                {
                    if (courses.get(i).getDescription().equals(""))
                    {
                        System.out.println((i + 1) + " --> " + courses.get(i).getName());
                    }
                    else
                    {
                        System.out.println((i + 1) + " --> " + courses.get(i).getName() + " - " + courses.get(i).getDescription());
                    }
                }

                int key = (int)courses.keySet().toArray()[Integer.parseInt(scn.nextLine().trim()) - 1];

                System.out.println("Are you sure you want delete the course \"" + courses.get(key).getName() + "\"? (Y / N)");

                if (!scn.nextLine().trim().toUpperCase().equals("Y"))
                {
                    System.out.println("Delete process canceled.\n");
                }
                else
                {
                    String name = courses.get(key).getName();

                    courses.remove(key);

                    for (int key2 : students.keySet())
                    {
                        for (int key3 : students.get(key2).getCourses().keySet())
                        {
                            if (key == key3)
                            {
                                students.get(key2).getCourses().remove(key);
                            }
                        }
                    }
                    System.out.println("Course \"" + name + "\" was successfully removed.\n");
                }
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Chosen action doesn't exist. You are getting redirected to the start.\n");
            }
        }
    }
}