package by.shapel.springcourse.dao;

import by.shapel.springcourse.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();

        person.setId(resultSet.getInt("person_id"));
        person.setName(resultSet.getString("name"));
        person.setSurname(resultSet.getString("surname"));
        person.setSecondName(resultSet.getString("second_name"));
        person.setBirthYear(resultSet.getInt("birth_year"));
        person.setEmail(resultSet.getString("email"));

        return person;
    }
}
