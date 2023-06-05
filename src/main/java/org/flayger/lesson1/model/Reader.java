package org.flayger.lesson1.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Reader {
    private int id;

    @NotNull(message = "name should not be empty")
    @Size(min = 2, max=100, message = "wrong credentials, between 2 or 100 characters")
    private String name;

    @NotNull(message = "birth date should not be empty")
    @Min(value = 0, message = "birth date should not be negative")
    private int year;

    public Reader() {
    }

    public Reader(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
