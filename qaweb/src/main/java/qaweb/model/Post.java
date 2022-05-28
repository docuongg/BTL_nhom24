package qaweb.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private boolean isQuestion;

	private String title;

	private String content;

	private Date createdAt;

	private Date updatedAt;

	@ToString.Exclude
	@OneToMany(targetEntity = Comment.class, mappedBy = "post", fetch = FetchType.EAGER)
	private List<Comment> comments = new ArrayList<>();

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Post parent;

	@ToString.Exclude
	@OneToMany(mappedBy = "parent")
	private List<Post> answers = new ArrayList<>();

	@ToString.Exclude
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

	@ToString.Exclude
	@OneToMany(targetEntity = Vote.class, mappedBy = "post")
	private List<Vote> votes = new ArrayList<>();

	@ToString.Exclude
	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name = "category_id")
	private Category category;

	public void addComment(Comment comment) {
		this.comments.add(comment);
		comment.setPost(this);
	}

	public void addAnswer(Post answer) {
		this.answers.add(answer);
		answer.setParent(this);
	}

	public void addVote(Vote vote) {
		this.votes.add(vote);
	}

	public int voteCount() {
		int count = 0;
		for (Vote vote : votes) {
			if (vote.isUpvote()) {
				count++;
			} else {
				count--;
			}
		}
		return count;
	}

	public int answerCount() {
		return answers.size();
	}

	public int commentCount() {
		return comments.size();
	}

	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}

	@PreUpdate
	void updatedAt() {
		this.updatedAt = new Date();
	}

	public String getVoteOfUser(int userId) {
		for (Vote v : votes) {
			if (v.getUser().getId() == userId) {
				return v.isUpvote() ? "1" : "-1";
			}
		}
		return "0";
	}

	public String contentHtml() {
		return markdownToHtml(content);
	}

	public String markdownToHtml(String markdown) {
		List<Extension> extensions = Arrays.asList(TablesExtension.create());
		Parser parser = Parser.builder().extensions(extensions).build();
		Node document = parser.parse(markdown);
		HtmlRenderer htmlRenderer = HtmlRenderer.builder().extensions(extensions).build();
		return htmlRenderer.render(document);
	}

}
