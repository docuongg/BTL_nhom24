package qaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import qaweb.model.Post;
import qaweb.model.User;
import qaweb.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
	public boolean existsByUserAndPost(User user, Post post);

	public Vote getByUserAndPost(User user, Post post);

}
