package qaweb.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import qaweb.model.Post;
import qaweb.model.User;
import qaweb.repository.PostRepository;

//@Slf4j
@Controller
@RequestMapping("/answer")
@SessionAttributes({ "user", "answer" })
public class AnswerController {

	private final PostRepository postRepo;

	@Autowired
	public AnswerController(PostRepository postRepo) {
		this.postRepo = postRepo;
	}

	@GetMapping("/{id}")
	public String getAnswerPage(@PathVariable("id") int id, Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		Post question = postRepo.getById(id);
		Post answer = new Post();
		answer.setParent(question);
		model.addAttribute("answer", answer);
		return "answer";
	}

	@PostMapping
	public String addAnswer(@Valid @ModelAttribute("answer") Post answer, BindingResult result,
			@ModelAttribute User user, Model model) {
		Post parent = answer.getParent();
		if (answer.getContent().trim().isEmpty()) {
			result.rejectValue("content", "error.answer", "Please enter your answer.");
		}
		if (result.hasErrors()) {
			return "answer";
		}
		answer.setUser(user);
		postRepo.save(answer);
		parent.setUpdatedAt(new Date());
		postRepo.save(parent);
		return "redirect:/question/" + parent.getId();
	}

	@GetMapping("/edit/{id}")
	public String editAnswer(@PathVariable("id") int id, Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		Post answer = postRepo.getById(id);
		if (user.getId() != answer.getUser().getId())
			return "redirect:/";
		model.addAttribute("answer", answer);
		return "edit-answer";
	}

	@PostMapping("edit")
	public String postUpdateAnswer(@Valid @ModelAttribute("answer") Post answer, BindingResult result, Model model) {
		Post parent = answer.getParent();
		if (answer.getContent().trim().isEmpty()) {
			result.rejectValue("content", "error.answer", "Please enter your answer.");
		}	
		if (result.hasErrors()) {
			return "answer";
		}
		answer.setUpdatedAt(new Date());
		postRepo.save(answer);
		parent.setUpdatedAt(new Date());
		postRepo.save(parent);
		return "redirect:/question/" + parent.getId();
	}
}
