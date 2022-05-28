package qaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import qaweb.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select count(*) from Comment c where month(c.createdAt) = :month and year(c.createdAt) = :year")
    int countCommentForMonth(int month, int year);
}
