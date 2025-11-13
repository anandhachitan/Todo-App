package spring.project2.HelloWorld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.project2.HelloWorld.model.Todo;
import spring.project2.HelloWorld.repository.TodoRepository;

import java.util.List;

@Service //It is bean we define the service class as service bean
public class TodoService  {
    @Autowired
    private TodoRepository todoRepository;

    public Todo getTodoId(Long id){
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
    }
    public Todo createUser(Todo todo){
        return todoRepository.save(todo);
    }

    public Page<Todo> getTodoPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return todoRepository.findAll(pageable);
    }

    public List<Todo> getTodo(){
        return todoRepository.findAll();
    }

    public Todo updateTodo(Todo todo){
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id){
        todoRepository.delete(getTodoId(id));
    }

    public void deleteAllTodo(){
        todoRepository.deleteAll();
    }

}
