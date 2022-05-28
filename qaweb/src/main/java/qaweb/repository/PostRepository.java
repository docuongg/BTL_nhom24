package qaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import qaweb.model.Category;
import qaweb.model.Post;

import java.sql.Date;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("select count(*) from Post p where month(p.createdAt) = :month and year(p.createdAt) = :year and isQuestion = 1")
    int countQuestionForMonth(int month,int year);

    @Query("select count(*) from Post p where month(p.createdAt) = :month and year(p.createdAt) = :year and isQuestion = 0")
    int countAnswerForMonth(int month, int year);

    @Query("select p from Post p where (p.title like %:key% or p.content like %:key%) and isQuestion = 1")
    List<Post> findByTitleOrContent(String key);

    @Query("select p from Post p where p.category.name = :key and isQuestion = 1")
    List<Post> findPostByCategory(String key);
}
