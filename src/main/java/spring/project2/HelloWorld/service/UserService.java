package spring.project2.HelloWorld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.project2.HelloWorld.model.User;
import spring.project2.HelloWorld.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

}
