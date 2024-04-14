package Blogproject.springbootrestapi.service.impl;

import Blogproject.springbootrestapi.entity.Post;
import Blogproject.springbootrestapi.entity.User;
import Blogproject.springbootrestapi.entity.UserList;
import Blogproject.springbootrestapi.exception.RessourceNotFoundException;
import Blogproject.springbootrestapi.payload.PostDto;
import Blogproject.springbootrestapi.repository.PostRepository;
import Blogproject.springbootrestapi.repository.UserListRepository;
import Blogproject.springbootrestapi.repository.UserRepository;
import Blogproject.springbootrestapi.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserListServiceImpl implements UserListService {


    private UserRepository userRepository;
    private PostRepository postRepository;
    private UserListRepository userListRepository;

    public UserListServiceImpl (UserRepository userRepository, PostRepository postRepository, UserListRepository userListRepository)
    {
        this.postRepository=postRepository;
        this.userRepository=userRepository;
        this.userListRepository=userListRepository;
    }


    @Override
    public void addPostToUserList(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RessourceNotFoundException("User", "id", userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RessourceNotFoundException("Post", "id", postId));

        UserList userList = new UserList();
        userList.setUser(user);
        userList.setPost(post);
        userListRepository.save(userList);
    }


    /*public List<PostDto> getUserList(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RessourceNotFoundException("User","id",userId));
        List<Post> posts = user.getSavedPosts();

        return posts.stream().map((post)->mapToDTO(post))
                .collect(Collectors.toList());
    }*/

    /*
    @Override
    public List<Post> getUserList(Long userId) {
        List<UserList> userLists = userListRepository.findAllByUserId(userId);
        List<Post> savedPosts = userLists.stream()
                .map(UserList::getPost)
                .collect(Collectors.toList());
        return savedPosts;
    }*/
}
