package qaweb.controller;

import java.util.Comparator;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import qaweb.model.Category;
import qaweb.model.Post;
import qaweb.model.User;
import qaweb.repository.CategoryRepository;
import qaweb.repository.PostRepository;

@Controller
@RequestMapping("/question")
@SessionAttributes({"user", "question"})
public class QuestionController {

	private final PostRepository postRepo;
	private final CategoryRepository categoryRepo;

	@Autowired
	public QuestionController(PostRepository postRepo, CategoryRepository categoryRepo) {
		this.postRepo = postRepo;
		this.categoryRepo = categoryRepo;
	}

	@GetMapping("/{id}")
	public String showQuestion(@PathVariable("id") int id, Model model) {
		Post question = postRepo.getById(id);
		List<Post> answers = question.getAnswers();
		answers.sort(new Comparator<Post>() {
			@Override
			public int compare(Post p1, Post p2) {
				return p2.voteCount() - p1.voteCount();
			}
		});
		question.setAnswers(answers);
		model.addAttribute("question", question);
		return "question-detail";
	}
	
	@GetMapping("/edit/{id}")
	public String editQuestion(@PathVariable("id") int id, Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		Post question = postRepo.getById(id);
		if (user.getId() != question.getUser().getId())
			return "redirect:/";
		model.addAttribute("question", question);
		return "edit-question";
	}
	
	@PostMapping("/edit")
	public String postUpdateQuestion(@Valid @ModelAttribute("question") Post question,
			BindingResult result, Model model) {
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
			return "edit-question";
		}
		postRepo.save(question);
		return "redirect:/question/" + question.getId();
	}

	// Delete post
	@PostMapping("/delete/{id}")
	public String deleteProcess(@RequestParam("submit") String value,
			@PathVariable(name = "id") int id) {
		Post post = postRepo.getById(id);
		boolean isQuestion = post.isQuestion();
		int parentId = 0;
		if (!isQuestion)
			parentId = post.getParent().getId();
		if (value.equalsIgnoreCase("yes")) {
			postRepo.deleteById(id);
			if (isQuestion)
				return "redirect:/";
			else
				return "redirect:/question/" + parentId;
		}
		if (isQuestion)
			return "redirect:/question/{id}";
		else
			return "redirect:/question/" + parentId;
	}
}
