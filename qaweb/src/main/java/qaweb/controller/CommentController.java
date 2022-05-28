package qaweb.controller;

import java.util.Date;

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

import qaweb.model.Comment;
import qaweb.model.Post;
import qaweb.model.User;
import qaweb.repository.CommentRepository;
import qaweb.repository.PostRepository;

@Controller
@RequestMapping("/comment")
@SessionAttributes({ "user", "comment" })
public class CommentController {

	private final PostRepository postRepo;
	private final CommentRepository commentRepo;

	@Autowired
	public CommentController(PostRepository postRepo, CommentRepository commentRepo) {
		this.postRepo = postRepo;
		this.commentRepo = commentRepo;
	}

	@ModelAttribute(name = "user")
	public User user() {
		return new User();
	}

	@GetMapping("/{id}")
	public String getCommentPage(@PathVariable("id") int id, Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		Post post = postRepo.getById(id);
		Comment comment = new Comment();
		post.addComment(comment);
		model.addAttribute("comment", comment);
		return "comment";
	}

	@PostMapping
	public String addComment(@ModelAttribute Comment comment, BindingResult result, @ModelAttribute User user,
			Model model) {
		comment.setUser(user);
		Post post = comment.getPost();
		if (comment.getContent().trim().isEmpty()) {
			result.rejectValue("content", "comment.content", "Please enter your comment");
		}
		if (result.hasErrors()) {
			return "comment";
		}
		commentRepo.save(comment);
		post.setUpdatedAt(new Date());
		postRepo.save(post);
		int questionId = post.getId();
		if (!post.isQuestion()) {
			questionId = post.getParent().getId();
		}
		return "redirect:/question/" + questionId;
	}

	@GetMapping("/edit/{id}")
	public String editComment(@PathVariable("id") int id, Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		Comment comment = commentRepo.getById(id);
		if (user.getId() != comment.getUser().getId()) {
			return "redirect:/";
		}
		model.addAttribute("comment", comment);
		return "edit-comment";
	}

	@PostMapping("/edit")
	public String postUpdateComment(@ModelAttribute Comment comment, BindingResult result, Model model) {
		Post post = comment.getPost();
		if (comment.getContent().trim().isEmpty()) {
			result.rejectValue("content", "comment.content", "Please enter your comment");
		}
		if (result.hasErrors()) {
			return "edit-comment";
		}
		commentRepo.save(comment);
		post.setUpdatedAt(new Date());
		postRepo.save(post);
		int questionId = post.getId();
		if (!post.isQuestion()) {
			questionId = post.getParent().getId();
		}
		return "redirect:/question/" + questionId;
	}

	@PostMapping("/deleteComment/{id}")
	public String deleteCommentProcess(@RequestParam("submit") String value, @PathVariable(name = "id") int id) {
		Comment comment = commentRepo.getById(id);
		if (value.equalsIgnoreCase("yes")) {
			commentRepo.deleteById(id);
		}
		if (comment.getPost().isQuestion()) {
			return "redirect:/question/" + comment.getPost().getId();
		} else {
			return "redirect:/question/" + comment.getPost().getParent().getId();
		}
	}
}
