package Blogproject.springbootrestapi.service;

import Blogproject.springbootrestapi.entity.User;
import Blogproject.springbootrestapi.payload.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);
    User getUserByEmail(String email);

    String updateUserAbout(UserDto userDto, Long id);

    String updateUser(UserDto userDto, Long id);

    List<User> getAllUser();

    void addPostToUserList(Long userId, Long postId);
}
