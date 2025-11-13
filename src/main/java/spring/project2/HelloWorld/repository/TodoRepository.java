package spring.project2.HelloWorld.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import spring.project2.HelloWorld.model.Todo;
import spring.project2.HelloWorld.model.User;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {


}
