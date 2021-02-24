package repository;

import entity.Student;
import java.util.HashMap;

public class Storage {
    private final HashMap<String, Student> students = new HashMap<>();

    public HashMap<String, Student> getStudents() {
        return students;
    }

    public Student getStudent(String key) {
        return students.get(key);
    }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    public void removeStudent(String key) {
        students.remove(key);
    }
}