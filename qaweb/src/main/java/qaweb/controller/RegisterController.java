package qaweb.controller;

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

@Controller
@RequestMapping("/register")
@SessionAttributes("user")
public class RegisterController {
	private final UserRepository userRepo;

	@Autowired
	public RegisterController(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@ModelAttribute(name = "user")
	public User user() {
		return new User();
	}

	@GetMapping
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping
	public String register(@Valid User user, Errors errors, Model model) {
		if (user.getEmail().trim() == "") {
			errors.rejectValue("email", "empty", "Please enter your email");
		}
		if (userRepo.existsByEmail(user.getEmail())) {
			errors.rejectValue("email", "exists", "This email already exists");
		}
		if (userRepo.existsByUsername(user.getUsername())) {
			errors.rejectValue("username", "exists", "This username already exists");
		}
		if (errors.hasErrors()) {
			return "register";
		}
		user.setAdmin(false);
		userRepo.save(user);
		return "redirect:/";
	}
}
