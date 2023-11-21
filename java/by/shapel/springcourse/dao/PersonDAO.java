package by.shapel.springcourse.dao;

import by.shapel.springcourse.models.Book;
import by.shapel.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id = ?", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name,surname,second_name,birth_year,email) VALUES(?,?,?,?,?)", person.getName(), person.getSurname(), person.getSecondName(),
                person.getBirthYear(), person.getEmail());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, surname=?, second_name=?, birth_year=?, email=? WHERE person_id = ?",
                person.getName(), person.getSurname(), person.getSecondName(),
                person.getBirthYear(), person.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }

    public List<Book> findAllBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id IN (SELECT book_id FROM unavailable_book WHERE person_id=?)", new Object[]{id}, new BookMapper());
    }
}
