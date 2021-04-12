package config;

import entity.Book;
import entity.Student;
import repository.Storage;
import service.StudentService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "service")
    public StudentService getService() {
        return new StudentService();
    }

    @Bean(name = "storage")
    public Storage getStorage() {
        return new Storage();
    }

    @Bean(name = "student")
    @Scope("prototype")
    public Student getStudent(String firstName, String lastName) {
        return new Student(firstName, lastName);
    }

    @Bean(name = "book")
    @Scope("prototype")
    public Book getBook(String title) {
        return new Book(title);
    }

}