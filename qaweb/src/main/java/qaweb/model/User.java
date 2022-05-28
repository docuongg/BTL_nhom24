package qaweb.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

//	@NotBlank(message = "Please enter email")
	private String email;

	@NotBlank(message = "Please enter your username")
	private String username;

	@NotBlank(message = "Please enter your password")
	private String password;

	private Date registeredAt;

	private Date lastLogin;

	private boolean isAdmin;

	@OneToMany(targetEntity = Post.class, mappedBy = "user")
	private List<Post> posts;

	@OneToMany(targetEntity = Comment.class, mappedBy = "user")
	private List<Comment> comments;

	@OneToMany(targetEntity = Vote.class, mappedBy = "user")
	private List<Vote> votes;

	public void addPost(Post post) {
		this.posts.add(post);
		post.setUser(this);
	}

	public void addComment(Comment comment) {
		this.comments.add(comment);
		comment.setUser(this);
	}

	public void addVote(Vote vote) {
		this.votes.add(vote);
	}

	@PrePersist
	void registeredAt() {
		this.registeredAt = new Date();
		this.lastLogin = new Date();
	}

	void lastLogin() {
		this.lastLogin = new Date();
	}

	public long questionCount() {
		return posts.stream().filter(c -> c.isQuestion()).count();
	}

	public long answerCount() {
		return posts.stream().filter(c -> !c.isQuestion()).count();
	}

	public long commentCount() {
		return comments.size();
	}
}
