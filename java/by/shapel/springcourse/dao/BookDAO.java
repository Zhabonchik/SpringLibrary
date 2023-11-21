package by.shapel.springcourse.dao;

import by.shapel.springcourse.models.Book;
import by.shapel.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id = ?", new Object[]{id}, new BookMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name,author,year) VALUES(?,?,?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET name=?,author=?,year=? WHERE book_id = ?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public Person getPersonWithBook(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id = (SELECT person_id FROM unavailable_book WHERE book_id=?)", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void assignBook(int personId, int bookId){
        jdbcTemplate.update("INSERT INTO unavailable_book values (?,?)",personId, bookId);
    }

    public void disassociateBook(int bookId){
        jdbcTemplate.update("DELETE FROM unavailable_book WHERE book_id=?",bookId);
    }
}
