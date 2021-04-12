package entity;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class Book {
    private String id;
    private String title;

    public Book(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) &&
                Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return String.format("Book { id = %s, title = %s }", id, title);
    }
}