package qaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import qaweb.model.Category;
import qaweb.model.Post;
import qaweb.model.User;
import qaweb.repository.CategoryRepository;
import qaweb.repository.CommentRepository;
import qaweb.repository.PostRepository;
import qaweb.repository.UserRepository;

@Controller
@RequestMapping("/manager")
@SessionAttributes({ "user", "users" })
public class ManagerController {

	private final UserRepository userRepo;
	private final CategoryRepository categoryRepo;
	private final PostRepository postRepo;
	private final CommentRepository commentRepo;

	@Autowired
	public ManagerController(UserRepository userRepo, CategoryRepository categoryRepo,
			PostRepository postRepo, CommentRepository commentRepo) {
		this.userRepo = userRepo;
		this.categoryRepo = categoryRepo;
		this.postRepo = postRepo;
		this.commentRepo = commentRepo;
	}

	@GetMapping
	public String mananger(Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		if (!user.isAdmin())
			return "redirect:/";
		return "manager";
	}

	// User manager

	@GetMapping("/user-manager")
	public String userManager(Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		if (!user.isAdmin())
			return "redirect:/";
		List<User> users = userRepo.findAll();
		model.addAttribute("users", users);
		return "user-manager";
	}

	@PostMapping("/user-manager")
	public String saveUsers(@RequestParam("id") int id,
			@RequestParam(value = "isAdmin", required = false) String isAdmin) {
		User u = userRepo.getById(id);
		if (isAdmin != null)
			u.setAdmin(true);
		else
			u.setAdmin(false);
		userRepo.save(u);
		return "redirect:/manager/user-manager";
	}

	// Category manager

	@GetMapping("/category-manager")
	public String categoryManager(Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		if (!user.isAdmin())
			return "redirect:/";
		List<Category> categories = categoryRepo.findAll();
		model.addAttribute("categories", categories);
		return "category-manager";
	}

	@PostMapping("/add-category")
	public String addCategory(@RequestParam("name") String name) {
		Category category = new Category();
		category.setName(name);
		categoryRepo.save(category);
		return "redirect:/manager/category-manager";
	}

	@PostMapping("/edit-category/{id}")
	public String editCategory(@PathVariable("id") int id,
			@RequestParam("name") String name) {
		Category category = categoryRepo.getById(id);
		category.setName(name);
		categoryRepo.save(category);
		return "redirect:/manager/category-manager";
	}

	@PostMapping("/delete-category/{id}")
	public String deleteCategory(@PathVariable("id") int id) {
		Category category = categoryRepo.getById(id);
		if (category.postCount() == 0) {
			categoryRepo.deleteById(id);
		}
		return "redirect:/manager/category-manager";
	}

	// Post manager

	@GetMapping("/post-manager")
	public String postManager(Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		if (!user.isAdmin())
			return "redirect:/";
		List<Post> posts = postRepo.findAll();
		model.addAttribute("posts", posts);
		return "post-manager";
	}

	@GetMapping("/delete-post/{id}")
	public String confirmDeletePost(@PathVariable("id") int id, Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		if (!user.isAdmin())
			return "redirect:/";
		Post post = postRepo.getById(id);
		model.addAttribute("post", post);
		return "confirm-delete-post";
	}

	@PostMapping("/delete-post/{id}")
	public String doDeletePost(@PathVariable("id") int id, Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		if (!user.isAdmin())
			return "redirect:/";
		postRepo.deleteById(id);
		return "redirect:/manager/post-manager";
	}

	// Statistics

	@GetMapping("/statistics")
	public String getStat(Model model) {
		User user = (User) model.getAttribute("user");
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		if (!user.isAdmin())
			return "redirect:/";
		return "statistics";
	}

	@PostMapping("statistics/month")
	public String month(Model model, @RequestParam("month") Integer month,
			@RequestParam("year") Integer year) {
		int posts = userRepo.countForMonth(month, year);
		int ques = postRepo.countQuestionForMonth(month, year);
		int ans = postRepo.countAnswerForMonth(month, year);
		int com = commentRepo.countCommentForMonth(month, year);

		model.addAttribute("posts", posts);
		model.addAttribute("ques", ques);
		model.addAttribute("ans", ans);
		model.addAttribute("com", com);
		model.addAttribute("month", month);
		model.addAttribute("year", year);

		return "statistics";
	}
}
