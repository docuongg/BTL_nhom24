package qaweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import qaweb.model.Post;
import qaweb.model.User;
import qaweb.model.Vote;
import qaweb.repository.PostRepository;
import qaweb.repository.UserRepository;
import qaweb.repository.VoteRepository;

//@Slf4j
@Controller
@RequestMapping("/vote")
@SessionAttributes({ "user" })
public class VoteController {

	private final VoteRepository voteRepo;
	private final PostRepository postRepo;

	@Autowired
	public VoteController(VoteRepository voteRepo, PostRepository postRepo, UserRepository userRepo) {
		this.voteRepo = voteRepo;
		this.postRepo = postRepo;
	}

	@GetMapping
	public String vote(@RequestParam(name = "post_id") String id, @RequestParam(name = "type") String type,
			@ModelAttribute User user) {
		if (user == null || user.getId() == 0) {
			return "redirect:/login";
		}
		Post post = postRepo.getById(Integer.parseInt(id));
		Vote vote = new Vote();
		if (type.equalsIgnoreCase("upvote")) {
			vote.setUpvote(true);
		} else if (type.equalsIgnoreCase("downvote")) {
			vote.setUpvote(false);
		}
		vote.setUser(user);
		vote.setPost(post);
		if (!voteRepo.existsByUserAndPost(user, post)) {
			voteRepo.save(vote);
		} else {
			Vote existVote = voteRepo.getByUserAndPost(user, post);
			existVote.setPost(post);
			existVote.setUser(user);

			if (existVote.isUpvote() == vote.isUpvote()) { // xoá vote
				voteRepo.delete(existVote);
			} else { // sửa vote
				existVote.setUpvote(vote.isUpvote());
				voteRepo.save(existVote);
			}
		}
		if (!post.isQuestion()) {
			id = post.getParent().getId() + "";
		}
		return "redirect:/question/" + id;
	}
}
