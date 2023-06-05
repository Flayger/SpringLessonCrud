package org.flayger.lesson1.dao;

import org.flayger.lesson1.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReaderDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReaderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reader> index(){
        return jdbcTemplate.query("SELECT * FROM reader", new BeanPropertyRowMapper<>(Reader.class));
    }

    public Reader show(int id) {
        return jdbcTemplate.query("SELECT * FROM reader WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Reader.class)).stream().findAny().orElse(null);
    }

    public void save(Reader reader) {
        jdbcTemplate.update("INSERT INTO reader(name,year) VALUES (?,?)", reader.getName(), reader.getYear());
    }

    public void update(Reader reader, int id) {
        jdbcTemplate.update("update reader set name=?, year=? WHERE id=?",
                reader.getName(), reader.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE from reader where id=?", id);
    }

    public Optional<Reader> show(String selectedName){
        return jdbcTemplate.query("SELECT * FROM reader WHERE name=?",
                new Object[]{selectedName}, new BeanPropertyRowMapper<>(Reader.class)).stream().findAny();
    }
}