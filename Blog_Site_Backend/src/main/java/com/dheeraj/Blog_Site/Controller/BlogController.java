package com.dheeraj.Blog_Site.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dheeraj.Blog_Site.Model.Blog;
import com.dheeraj.Blog_Site.Model.Users;
import com.dheeraj.Blog_Site.Repository.BlogRepository;
import com.dheeraj.Blog_Site.Repository.UserRepository;
import com.dheeraj.Blog_Site.Service.BlogService;
//import com.abhi.Blog_Site.Service.UserService;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog, @RequestParam Long userId) {
        // Fetch user by userId (you can adjust this logic as per your need)
        Optional<Users> user = userRepository.findById(userId);

        if (user.isPresent()) {
            blog.setUser(user.get());
            Blog savedBlog = blogService.createBlog(blog);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBlog);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Blog>> getAllBlogsByUser(@PathVariable Long userId) {
        Optional<Users> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<Blog> blogs = blogService.getAllBlogsByUser(userId);
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long blogId) {
        Optional<Blog> blog = blogRepository.findById(blogId);

        if (blog.isPresent()) {
            blogService.deleteBlog(blogId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long blogId, @RequestBody Blog blogDetails) {

        Blog updatedBlog = blogService.updateBlog(blogId, blogDetails);

        if (updatedBlog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedBlog);
    }

    @GetMapping("/report/{userId}")
    public ResponseEntity<Map<String, Integer>> getTop5FrequentWords(@PathVariable Long userId) {
        try {
            Map<String, Integer> frequentWords = blogService.getTop5FrequentWords(userId);
            return new ResponseEntity<>(frequentWords, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
