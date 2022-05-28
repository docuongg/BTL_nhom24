package qaweb.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import qaweb.model.Post;
import qaweb.model.User;
import qaweb.repository.PostRepository;
import qaweb.repository.UserRepository;

//@Slf4j
@Controller
@SessionAttributes({ "user", "viewUser" })
public class IndexController {
	private final PostRepository postRepo;
	private final UserRepository userRepo;

	@Autowired
	public IndexController(PostRepository postRepo, UserRepository userRepo) {
		this.postRepo = postRepo;
		this.userRepo = userRepo;
	}

	@ModelAttribute(name = "user")
	public User user() {
		User u = new User();
		return u;
	}

	@GetMapping("/")
	public String Index(@ModelAttribute User user, Model model) {
		List<Post> posts = postRepo.findAll();
		List<Post> result = posts.stream().filter(post -> post.isQuestion())
				.collect(Collectors.toList());
		Collections.sort(result, new Comparator<Post>() {
			@Override
			public int compare(Post p1, Post p2) {
				return p2.getUpdatedAt().compareTo(p1.getUpdatedAt());
			}
		});
		model.addAttribute("posts", result);
		return "index";
	}

	@GetMapping("/logout")
	public String logout(Model model) {
		model.addAttribute("user", user());
		return "redirect:/";
	}

	@GetMapping("/user/{id}")
	public String user(@PathVariable int id, Model model) {
		User u = userRepo.getById(id);
		System.out.println(u);
		if (u != null) {
			model.addAttribute("viewUser", u);
			return "user";
		}
		return "redirect:/";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam("cpassword") String cpassword,
			@RequestParam("npassword") String npassword,
			@RequestParam("cnpassword") String cnpassword, @RequestParam("id") int id,
			Model model) {
		User u = userRepo.getById(id);
		System.out.println(u);
		if (u != null && u.getId() > 0) {
			model.addAttribute("user", u);
			model.addAttribute("viewUser", u);
			model.addAttribute("cpassword", cpassword);
			model.addAttribute("npassword", npassword);
			model.addAttribute("cnpassword", cnpassword);
			String message = "\n";
			if (cpassword.trim().isEmpty()) {
				message += "Please enter your current password.\n";
			}
			if (npassword.trim().isEmpty()) {
				message += "Please enter your new password.\n";
			}
			if (!u.getPassword().equals(cpassword)) {
				message += "Invalid current password.\n";
			}
			if (!npassword.equals(cnpassword)) {
				message += "Confirm password does not match.\n";
			}
			if (message.length() > 1) {
				model.addAttribute("errorMessage", message);
				return "user";
			}
			u.setPassword(npassword);
			userRepo.save(u);
			model.addAttribute("user", u);
			model.addAttribute("viewUser", u);
			return "redirect:/";
		}
		return "redirect:/";
	}
}
