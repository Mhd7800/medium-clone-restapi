package Blogproject.springbootrestapi.controller;

import Blogproject.springbootrestapi.entity.User;
import Blogproject.springbootrestapi.payload.PostDto;
import Blogproject.springbootrestapi.payload.UserDto;
import Blogproject.springbootrestapi.repository.UserRepository;
import Blogproject.springbootrestapi.service.PostService;
import Blogproject.springbootrestapi.service.UserService;
import com.sun.security.auth.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user/")
public class UserController {



    private UserService userService;
    private PostService postService;
    public UserController(UserService userService, PostService postService) {

        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long userId)
    {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email)
    {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateUserAbout (@RequestBody UserDto userDto, @PathVariable Long id)
    {
        userService.updateUserAbout(userDto,id);
        return new ResponseEntity<>("About updated successfully",HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateUser (@RequestBody UserDto userDto, @PathVariable Long id)
    {
        userService.updateUser(userDto, id);
        return new ResponseEntity<>("User updated successfully",HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers()
    {
        //List userService.getAllUser();
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }



}

