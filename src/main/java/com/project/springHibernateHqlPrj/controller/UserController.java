package com.project.springHibernateHqlPrj.controller;

import com.project.springHibernateHqlPrj.dto.PostRequest;
import com.project.springHibernateHqlPrj.dto.UserRequest;
import com.project.springHibernateHqlPrj.entity.Post;
import com.project.springHibernateHqlPrj.entity.Profile;
import com.project.springHibernateHqlPrj.entity.User;
import com.project.springHibernateHqlPrj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody UserRequest userRequest) {

        User user = new User(userRequest.getUsername(), userRequest.getEmail());
        Profile profile = new Profile(userRequest.getProfileBio(), user);
        user.setProfile(profile);

        userService.saveUserWithProfile(user);
        return "User and profile created successfully!";
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/createPost")
    public String createPost(@RequestBody PostRequest postRequest) {

        User user = userService.getUserById(postRequest.getUserId());
        if (user == null) {
            return "User not found!";
        }

        Post post = new Post(postRequest.getContent(), user);

        userService.savePost(post);
        return "Post created successfully!";
    }
    @GetMapping("/getPosts/{userId}")
    public List<Post> getPosts(@PathVariable int userId) {
        List<Post> posts = userService.getPostsByUserId(userId);
        if (posts == null || posts.isEmpty()) {
            throw new RuntimeException("No posts found for user with ID: " + userId);
        }
        return posts;
    }
}
