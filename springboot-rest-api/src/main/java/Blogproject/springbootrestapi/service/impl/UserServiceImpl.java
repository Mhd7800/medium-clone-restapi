package Blogproject.springbootrestapi.service.impl;

import Blogproject.springbootrestapi.entity.Post;
import Blogproject.springbootrestapi.entity.User;
import Blogproject.springbootrestapi.exception.RessourceNotFoundException;
import Blogproject.springbootrestapi.payload.PostDto;
import Blogproject.springbootrestapi.payload.UserDto;
import Blogproject.springbootrestapi.repository.PostRepository;
import Blogproject.springbootrestapi.repository.UserRepository;
import Blogproject.springbootrestapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private User user;
    private ModelMapper mapper;
    private PostRepository postRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.postRepository = postRepository;
    }


    @Override
    public UserDto getUserById(Long id) {

        User user =  userRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("User", "id", id));

        return mapToDTO(user);

    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String updateUserAbout(UserDto userDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("User", "id", id));

        user.setAbout(userDto.getAbout());
        userRepository.save(user);

        return "user about updated successfully";
    }

    @Override
    public String updateUser(UserDto userDto, Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RessourceNotFoundException("User", "id", id));

        user.setBio(userDto.getBio());
        user.setPhotoURL(userDto.getPhotoURL());
        user.setName(userDto.getName());
        userRepository.save(user);

        return "User updated successfully";
    }

    @Override
    public List<User> getAllUser() {

        List<User> allUsers = userRepository.findAll();

        return allUsers;
    }






    private UserDto mapToDTO(User user)
    {
        UserDto userDto = mapper.map(user,UserDto.class);

        return userDto;
    }

    private User mapToEntity(UserDto userDto)
    {
        User user = mapper.map(userDto,User.class);
        return user;
    }



}
