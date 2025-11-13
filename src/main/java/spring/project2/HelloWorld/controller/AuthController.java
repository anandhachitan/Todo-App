package spring.project2.HelloWorld.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.project2.HelloWorld.model.User;
import spring.project2.HelloWorld.repository.UserRepository;
import spring.project2.HelloWorld.service.UserService;
import spring.project2.HelloWorld.utils.JwtUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> body){
        String email = body.get("email");
        String password = passwordEncoder.encode(body.get("password"));
        if(userRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("User is already registered", HttpStatus.CONFLICT);
        }
        userService.createUser(User.builder().email(email).password(password).build());
        return new ResponseEntity<>("Successfully Registered..", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> body){
        String email = body.get("email");
        String password = body.get("password");

        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            return new ResponseEntity<>("User is not found..", HttpStatus.UNAUTHORIZED);
        }
        User user = userOptional.get();
        if(!passwordEncoder.matches(password, user.getPassword())){
            return new ResponseEntity<>("User name and password is incorrect..", HttpStatus.UNAUTHORIZED);
        }
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("Token", token));
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUser(){
        return new ResponseEntity<List<User>>(userService.getAllUser(),HttpStatus.OK);
    }
}
