package config;

import entity.Book;
import entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import repository.Storage;
import service.StudentService;

import java.util.Scanner;

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
    public Student getStudent() {
        return new Student();
    }

    @Bean(name = "book")
    @Scope("prototype")
    public Book getBook() {
        return new Book();
    }

}