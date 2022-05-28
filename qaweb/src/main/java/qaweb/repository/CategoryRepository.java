package qaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import qaweb.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
