package Blogproject.springbootrestapi.service;

import Blogproject.springbootrestapi.entity.User;
import Blogproject.springbootrestapi.payload.LoginDto;
import Blogproject.springbootrestapi.payload.UserDto;
import Blogproject.springbootrestapi.payload.RegisterDto;
import Blogproject.springbootrestapi.payload.RegisterDtoGoogle;

public interface AuthService {

    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);

    String registerFromGoogle(RegisterDtoGoogle registerDtoGoogle);

    //User getUserByUserName (UserDto userDto);
}
