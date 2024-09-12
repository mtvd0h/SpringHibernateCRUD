package myapp.controller;

import myapp.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping()
public class UserController {

	private final UserDao userDao;

	@Autowired
	public UserController(UserDao usersDao) {
		this.userDao = usersDao;
	}

	@GetMapping("/{id}")
	public String showUser(@PathVariable("id") int id, Model model) {
		model.addAttribute("user", userDao.show(id));
		return "showUser";
	}

	@RequestMapping("/hello")
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC-SECURITY application");
		messages.add("5.2.0 version by sep'19 ");
		model.addAttribute("messages", messages);
		return "hello";
	}

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }


}