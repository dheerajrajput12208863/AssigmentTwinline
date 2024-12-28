

package com.dheeraj.Blog_Site.Controller;

import com.dheeraj.Blog_Site.Model.*;
import com.dheeraj.Blog_Site.Repository.*;
import com.dheeraj.Blog_Site.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public Users registerUser(@RequestBody Users user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Users> loginUser(@RequestBody Users user) {
        Users existingUser = userRepository.findByUsername(user.getUsername());

        // Check if the user exists
        if (existingUser != null) {
            // Check if the password matches
            if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                return ResponseEntity.ok(existingUser); // Return the existing user
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(null); // Incorrect password
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // User not found
        }
    }
}