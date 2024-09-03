package myapp.controller;

import myapp.dao.UserDao;
import myapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users",userDao.index());
        return "pages/index";

    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userDao.show(id));
        return "pages/show";
    }
    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "pages/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userDao.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userDao.show(id));
        return "pages/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userDao.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userDao.delete(id);
        return "redirect:/users";
    }

}
