package org.flayger.lesson1.controllers;

import org.flayger.lesson1.dao.BooklessonDAO;
import org.flayger.lesson1.dao.ReaderDAO;
import org.flayger.lesson1.model.Reader;
import org.flayger.lesson1.util.ReaderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/lesson/readers")
public class ReaderController {

    private final ReaderDAO readerDAO;
    private final BooklessonDAO booklessonDAO;

    private final ReaderValidator readerValidator;

    @Autowired
    public ReaderController(ReaderDAO readerDAO, BooklessonDAO booklessonDAO, ReaderValidator readerValidator) {
        this.readerDAO = readerDAO;
        this.booklessonDAO = booklessonDAO;
        this.readerValidator = readerValidator;
    }

    //страница со списком читателей
    @GetMapping()
    public String index(Model model){
        model.addAttribute("readers", readerDAO.index());
        return "lesson/readers/index";
    }

    //страница конкретного читателя
    @GetMapping("/{id}")
    public String showReader(@PathVariable("id") int id, Model model){
        model.addAttribute("reader", readerDAO.show(id));
        model.addAttribute("readerBooks", booklessonDAO.indexReader(id));
        return "lesson/readers/show";
    }

    //страница с созданием читателя
    @GetMapping("/new")
    public String newReader(@ModelAttribute("reader") Reader reader){
        return "lesson/readers/new";
    }

    //метод сохранения в бд
    @PostMapping("/save")
    public String saveReader(@ModelAttribute("reader") @Valid Reader reader,
                             BindingResult bindingResult){

        readerValidator.validate(reader, bindingResult);

        if(bindingResult.hasErrors())
            return "lesson/readers/new";

        readerDAO.save(reader);
        return "redirect:/lesson/readers";
    }

    //страница редактирования читателя
    @GetMapping("/{id}/edit")
    public String editReader(Model model, @PathVariable("id") int id){
        model.addAttribute("reader", readerDAO.show(id));
        return "lesson/readers/edit";
    }

    //метод сохранения изменений читателя
    @PatchMapping("/{id}")
    public String updateReader(@ModelAttribute("reader") @Valid Reader reader,
                               BindingResult bindingResult,
                               @PathVariable("id") int id){
        readerValidator.validate(reader, bindingResult);
        if(bindingResult.hasErrors())
            return "lesson/readers/edit";

        readerDAO.update(reader, id);
        return "redirect:/lesson/readers";
    }

    //метод удаления читателя
    @DeleteMapping("/{id}/delete")
    public String deleteReader(@PathVariable("id") int id){
        readerDAO.delete(id);
        return "redirect:/lesson/readers";
    }


}
