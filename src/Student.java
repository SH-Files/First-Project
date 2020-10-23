import java.util.TreeMap;

public class Student
{
    private int id;
    private String firstname;
    private String lastname;
    private TreeMap<Integer, Course> courses = new TreeMap<Integer, Course>();

    Student(String firstname, String lastname)
    {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getFirstname()
    {
        return this.firstname;
    }

    public String getLastname()
    {
        return this.lastname;
    }

    public void addCourse(int index, Course course)
    {
        this.courses.put(index, course);
    }

    public void removeCourse(int index)
    {
        this.courses.remove(index);
    }

    public TreeMap<Integer, Course> getCourses()
    {
        return this.courses;
    }
}