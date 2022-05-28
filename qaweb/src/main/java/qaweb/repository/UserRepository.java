package qaweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import qaweb.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByUsernameAndPassword(String username, String password);

	User getByUsernameAndPassword(String username, String password);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	@Query("select count(*) from User u where month(u.registeredAt) = :month and year(u.registeredAt) = :year")
	int countForMonth(int month, int year);
}
