package org.flayger.lesson1.util;

import org.flayger.lesson1.dao.ReaderDAO;
import org.flayger.lesson1.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class ReaderValidator implements Validator {

    private final ReaderDAO readerDAO;

    @Autowired
    public ReaderValidator(ReaderDAO readerDAO) {
        this.readerDAO = readerDAO;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Reader.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Reader reader = (Reader) o;

        Optional<Reader> readerOptional = readerDAO.show(reader.getName());

        if(readerOptional.isPresent())
            errors.rejectValue("name", "", "это имя уже занято");


    }
}
