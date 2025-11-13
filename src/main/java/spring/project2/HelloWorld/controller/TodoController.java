package spring.project2.HelloWorld.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.project2.HelloWorld.model.Todo;
import spring.project2.HelloWorld.service.TodoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
@Slf4j
public class TodoController {
    @Autowired
    private TodoService todoService;

    @ApiResponses(value={
            @ApiResponse(responseCode = "404", description = "Todo Retrieved Successfully.."),
            @ApiResponse(responseCode = "404", description = "Todo Not Found!!")
    })
    @GetMapping("/{id}")
    ResponseEntity<Todo> getById(@PathVariable Long id){
        try{
            Todo todoCreated = todoService.getTodoId(id);
            log.info("Getting the Todo data by ID.");
            return new ResponseEntity<>(todoService.getTodoId(id), HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            log.error("Error throwing while getting a Todo data by ID", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    ResponseEntity<List<Todo>> getTodo(){
        log.info("Getting all Todo from database.");
        return new ResponseEntity<List<Todo>>(todoService.getTodo(),HttpStatus.OK);
    }

    @GetMapping("/page")
    ResponseEntity<Page<Todo>> getTodoPage(@RequestParam int page, @RequestParam int size){
        log.info("Getting the Todo based on the page limit.");
        return new ResponseEntity<>(todoService.getTodoPage(page, size), HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo){
        log.info("Creating the Todo data.");
        return new ResponseEntity<>(todoService.createUser(todo), HttpStatus.OK);
    }

    @PutMapping("/update")
   ResponseEntity<Todo> updateTodo(@RequestBody Todo todo){
        log.info("Update the Todo data.");
        return new ResponseEntity<>(todoService.updateTodo(todo), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    String deleteTodo(@PathVariable long id){
        try{
            todoService.deleteTodo(id);
            return "The Todo id : " + id + " is Deleted";
        } catch (RuntimeException e){
            log.error("Error while deleting the Todo data.", e);
            return "The data is not in the table";
        }
    }
    @DeleteMapping("/delete")
    String deleteAllTodo(){
        todoService.deleteAllTodo();
        return "Deleted the All Todo data.";
    }

}
