package Blogproject.springbootrestapi.service.impl;
import Blogproject.springbootrestapi.entity.Role;
import Blogproject.springbootrestapi.entity.User;
import Blogproject.springbootrestapi.exception.BlogApiException;
import Blogproject.springbootrestapi.payload.LoginDto;
import Blogproject.springbootrestapi.payload.RegisterDto;
import Blogproject.springbootrestapi.payload.RegisterDtoGoogle;
import Blogproject.springbootrestapi.repository.RoleRepository;
import Blogproject.springbootrestapi.repository.UserRepository;
import Blogproject.springbootrestapi.security.JwtTokenProvider;
import Blogproject.springbootrestapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,UserRepository userRepository,RoleRepository roleRepository,PasswordEncoder passwordEncoder,JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository= userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public String login(LoginDto loginDto) {
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
    @Override
    public String register(RegisterDto registerDto) {

        // Add check for username exist in database
        if(userRepository.existsByUsername(registerDto.getUsername()))
        {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Username is already exits!");
        }
        // add check for email in database
        if(userRepository.existsByEmail(registerDto.getEmail()))
        {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Email is already exists!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public String registerFromGoogle(RegisterDtoGoogle registerDtoGoogle) {
        User user = new User();
        user.setName(registerDtoGoogle.getName());
        user.setEmail(registerDtoGoogle.getEmail());
        //user.setId(registerDtoGoogle.getId());
        user.setPhotoURL(registerDtoGoogle.getPhotourl());
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";

    }

    /*@Override
    public User getUserByUserName(UserDto userDto) {
          return userRepository.findByUsername(userDto.getUsername());
    }*/
}
