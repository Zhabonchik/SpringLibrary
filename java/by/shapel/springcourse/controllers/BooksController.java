package by.shapel.springcourse.controllers;

import by.shapel.springcourse.dao.BookDAO;
import by.shapel.springcourse.dao.PersonDAO;
import by.shapel.springcourse.models.Book;
import by.shapel.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO){
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }
    @GetMapping
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("personWithBook", bookDAO.getPersonWithBook(id));
        model.addAttribute("allPeople", personDAO.index());
        return "books/show";
    }
    @PatchMapping("/assign/{id}")
    public String assignBook(Model model, @ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookDAO.assignBook(person.getId(), id);
        return String.format("redirect:/books/%d", id);
    }
}
