package spring.project2.HelloWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project2.HelloWorld.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
