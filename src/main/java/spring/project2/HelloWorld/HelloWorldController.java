package spring.project2.HelloWorld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.project2.HelloWorld.service.TodoService;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloWorldController {
    @Autowired
    private TodoService todoService;

    @GetMapping("/")
    String sayHello(){
        return "Hello world";
    }
//    @GetMapping("/get")
//    String getTodo(){
//        todoService.getTodoService();
//        return "Todo";
//    }

    @GetMapping("{id}")
    String getHelloById(@PathVariable int id){
        return "hello by id : " + id;
    }

    @GetMapping("")
    String getHelloByIdParam(@RequestParam("todoId") int id){
        return "Hello world by Request param id : " + id;
    }

    @PostMapping("/create")
    String createUser(@RequestBody String body){
        System.out.println(body);
        return body;
    }
}
