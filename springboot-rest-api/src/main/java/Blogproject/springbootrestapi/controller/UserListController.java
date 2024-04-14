package Blogproject.springbootrestapi.controller;

import Blogproject.springbootrestapi.payload.PostDto;
import Blogproject.springbootrestapi.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class UserListController {
    private UserListService userListService;

    public UserListController (UserListService userListService)
    {
        this.userListService= userListService;
    }

    @PostMapping("api/v1/{userId}/addPostToList/{postId}")
    public ResponseEntity<String> addPostToUserList(@PathVariable Long userId, @PathVariable Long postId)
    {
        userListService.addPostToUserList(userId,postId);
        return ResponseEntity.ok("Post added successfully");
    }

    /*@GetMapping("/getUserList/{userId}")
    public ResponseEntity<List<PostDto>> getUserList (@PathVariable Long userId)
    {
        return ResponseEntity.ok(userListService.getUserList(userId));
    }*/
}
