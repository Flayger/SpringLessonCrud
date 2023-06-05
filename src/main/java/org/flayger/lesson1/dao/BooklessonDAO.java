package org.flayger.lesson1.dao;

import org.flayger.lesson1.model.Booklesson;
import org.flayger.lesson1.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BooklessonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BooklessonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Booklesson> index(){
        return jdbcTemplate.query("SELECT * FROM booklesson",
                new BeanPropertyRowMapper<>(Booklesson.class));
    }

    public List<Booklesson> indexReader(int id){
        return jdbcTemplate.query("SELECT * FROM booklesson WHERE owner=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Booklesson.class));
    }

    public Booklesson show(int id){
        return jdbcTemplate.query("SELECT * FROM booklesson " +
                        "WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Booklesson.class))
                .stream().findAny().orElse(null);
    }

    public void save(Booklesson booklesson){
        jdbcTemplate.update("INSERT INTO booklesson(name, author, year, owner) " +
                "VALUES(?,?,?,?) ",
                booklesson.getName(), booklesson.getAuthor(), booklesson.getYear(), booklesson.getOwner());
    }

    public void edit(Booklesson booklesson, int id){
        jdbcTemplate.update("update booklesson set name=?, author=?, year=?, owner=? where id=?",
                booklesson.getName(), booklesson.getAuthor(), booklesson.getYear(), booklesson.getOwner(), id);
    }

    public void releaseOwner(int id){
        jdbcTemplate.update("update booklesson set owner=NULL where id=?", id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM booklesson where id=?", id);
    }


    public void assignOwner(int id, Booklesson booklesson) {
        jdbcTemplate.update("update booklesson set owner=? where id=?", booklesson.getOwner(), id);
    }
}
