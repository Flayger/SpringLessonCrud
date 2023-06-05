package org.flayger.lesson1.model;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.Constraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Booklesson {
    private int id;

    @NotNull(message = "book name should not be empty")
    private String name;

    @NotNull(message = "book author should not be empty")
    private String author;

    @NotNull(message = "year book should not be empty")
    @Min(value = 0, message = "year book should not be negative")
    private int year;

    //@Pattern(regexp = "^[1-9]+$|^$",message = "can be empty or positive")
    private Integer owner;

    public Booklesson() {
    }

    public Booklesson(int id, String name, String author, int year, Integer owner) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }
}
