package qaweb.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import qaweb.model.Category;
import qaweb.model.Post;
import qaweb.model.User;
import qaweb.repository.CategoryRepository;
import qaweb.repository.PostRepository;

//@Slf4j
@Controller
@RequestMapping("/ask")
@SessionAttributes({ "user", "question", "categories" })
public class AskController {
	private final PostRepository postRepo;

	private final CategoryRepository categoryRepo;

	@Autowired
	public AskController(PostRepository postRepo, CategoryRepository categoryRepo) {
		this.postRepo = postRepo;
		this.categoryRepo = categoryRepo;
	}

	@ModelAttribute(name = "user")
	public User user() {
		return new User();
	}

	@GetMapping
	public String getAsk(Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		Post post = new Post();
		model.addAttribute("question", post);
		return "ask";
	}

	@PostMapping
	public String postAsk(@Valid @ModelAttribute("question") Post question,
			BindingResult result, @ModelAttribute User user, Model model) {
		if (question.getTitle().trim().isEmpty()) {
			result.rejectValue("title", "error.post",
					"Please enter your question's title.");
		}
		if (question.getTitle().trim().length() > 255) {
			result.rejectValue("title", "error.post",
					"Your title must be less than 255 characters.");
		}
		if (question.getContent().trim().isEmpty()) {
			result.rejectValue("content", "error.post",
					"Please enter your question's content.");
		}
		if (result.hasErrors()) {
			return "ask";
		}
		question.setQuestion(true);
		question.setUser(user);
		Post newPost = postRepo.save(question);
		return "redirect:/question/" + newPost.getId();
	}
}
