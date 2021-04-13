package com.example.first.project.config;

import com.example.first.project.entity.Book;
import com.example.first.project.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import com.example.first.project.service.StudentService;
import org.springframework.context.annotation.Configuration;
import com.example.first.project.repository.DataMemoryStorage;

@Configuration
public class AppConfig {

    @Bean(name = "service")
    public StudentService getService() {
        return new StudentService();
    }

    @Bean(name = "dataMemoryStorage")
    public DataMemoryStorage getStorage() {
        return new DataMemoryStorage();
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