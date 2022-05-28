package qaweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "post_id" }) })
public class Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private boolean isUpvote;

	@ToString.Exclude
	@ManyToOne(targetEntity = User.class)
	private User user;

	@ToString.Exclude
	@ManyToOne(targetEntity = Post.class)
	private Post post;
}
