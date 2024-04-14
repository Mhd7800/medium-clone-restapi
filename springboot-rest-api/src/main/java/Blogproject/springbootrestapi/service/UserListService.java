package Blogproject.springbootrestapi.service;

import Blogproject.springbootrestapi.payload.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserListService {
    void addPostToUserList(Long userId, Long postId);
    //List<PostDto> getUserList(Long userId);
}
