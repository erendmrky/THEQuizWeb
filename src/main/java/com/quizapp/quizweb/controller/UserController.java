package com.quizapp.quizweb.controller;

import com.quizapp.quizweb.model.User;
import com.quizapp.quizweb.repository.UserRepository;
import com.quizapp.quizweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> loginUser(@RequestBody Map<String,String> creds) {
        String email    = creds.get("email");
        String password = creds.get("password");
        String msg      = userService.loginUser(email, password);
        boolean success = msg.equalsIgnoreCase("logged in successfully");

        Map<String,Object> resp = new HashMap<>();
        resp.put("success", success);
        if (success) {
            int userId = userRepository.findByEmail(email)
                    .map(User::getId)
                    .orElseThrow();
            resp.put("userId", userId);
        }
        System.out.println(resp.toString());
        return resp;
    }
    @GetMapping("/{email}/BestScore")
    public float getBestScore(@PathVariable String email) {
        return userService.getBestScore(email);
    }
    @GetMapping("/leaderboard")
    public List<Map<String,Object>> leaderboard() {
        List<User> users = userRepository.findAll();
        List<Map<String,Object>> board = new ArrayList<>();

        for (User u : users) {
            int total = Optional.ofNullable(u.getNumericalBestScore()).orElse(0)
                      + Optional.ofNullable(u.getVerbalBestScore()).orElse(0)
                      + Optional.ofNullable(u.getNonverbalBestScore()).orElse(0)
                      + Optional.ofNullable(u.getMechanicalBestScore()).orElse(0);

            Map<String,Object> row = new HashMap<>();
            row.put("username",   u.getUsername());
            row.put("totalScore", total);
            board.add(row);
            }
            board.sort((a, b) ->
                    ((Integer)b.get("totalScore")).compareTo((Integer)a.get("totalScore"))
            );
            return board;
    }
    @PostMapping("/delete")
    public String deleteUser(@RequestParam String email) {
        return userService.deleteUser(email);
    }
}
