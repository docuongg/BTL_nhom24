package qaweb.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import qaweb.model.User;
import qaweb.repository.UserRepository;

//@Slf4j
@Controller
@RequestMapping("/login")
@SessionAttributes("user")
public class LoginController {
	private final UserRepository userRepo;

	@Autowired
	public LoginController(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@ModelAttribute(name = "user")
	public User user() {
		return new User();
	}

	@GetMapping
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping
	public String login(@Valid User user, Errors errors, Model model,
			HttpServletResponse response) {
		if (!userRepo.existsByUsernameAndPassword(user.getUsername(),
				user.getPassword())) {
			errors.rejectValue("username", "not exists", "Invalid username or password");
		}
		if (errors.hasErrors()) {
			return "login";
		}
		user = userRepo.getByUsernameAndPassword(user.getUsername(), user.getPassword());
		user.setLastLogin(new Date());
		userRepo.save(user);
		model.addAttribute("user", user);
		return "redirect:/";
	}
}
