package org.flayger.lesson1.controllers;

import org.flayger.lesson1.dao.BooklessonDAO;
import org.flayger.lesson1.dao.ReaderDAO;
import org.flayger.lesson1.model.Booklesson;
import org.flayger.lesson1.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/lesson/books")
public class BooklessonController {

    private final BooklessonDAO booklessonDAO;
    private final ReaderDAO readerDAO;

    @Autowired
    public BooklessonController(BooklessonDAO booklessonDAO, ReaderDAO readerDAO) {
        this.booklessonDAO = booklessonDAO;
        this.readerDAO = readerDAO;
    }


    //страница всех книг

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", booklessonDAO.index());
        return "lesson/books/index";
    }

    //создать
    @GetMapping("/new")
    public String createBook(@ModelAttribute("book") Booklesson booklesson, Model model){

        return "lesson/books/new";
    }

    //сохранить
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") @Valid Booklesson booklesson,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("error");
            return "lesson/books/new";
        }

        booklessonDAO.save(booklesson);
        return "redirect:/lesson/books";
    }

    //изменить
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("readers", readerDAO.index());
        Booklesson booklesson;
        booklesson = booklessonDAO.show(id);
        model.addAttribute("book", booklesson);
        if(booklesson.getOwner() != null)
            model.addAttribute("reader", readerDAO.show(booklesson.getOwner()));
        return "lesson/books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booklessonDAO.show(id));
        model.addAttribute("readers", readerDAO.index());
        return "/lesson/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Booklesson booklesson,
                           BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "/lesson/books/edit";
        booklessonDAO.edit(booklesson, id);
        return "redirect:/lesson/books";
    }
    //удалить

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booklessonDAO.delete(id);
        return "redirect:/lesson/books";
    }

    //освободить книгу от владельца
    @PatchMapping("/{id}/release")
    public String releaseOwner(@ModelAttribute("book") @Valid Booklesson booklesson,
                               BindingResult bindingResult, @PathVariable("id") int id){
        booklessonDAO.releaseOwner(id);
        return  "redirect:/lesson/books/" + id;
    }

    //назначить владельца
    @PatchMapping("/{id}/assign")
    public String assignOwner(@ModelAttribute("reader") @Valid Booklesson booklesson,
                              BindingResult bindingResult, @PathVariable("id") int id){
        booklessonDAO.assignOwner(id,booklesson);
        return "redirect:/lesson/books/" + id;
    }
}
