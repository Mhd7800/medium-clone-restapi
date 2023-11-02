package Blogproject.springbootrestapi.controller;

import Blogproject.springbootrestapi.exception.BlogApiException;
import Blogproject.springbootrestapi.payload.JWTAuthResponse;
import Blogproject.springbootrestapi.payload.LoginDto;
import Blogproject.springbootrestapi.payload.RegisterDto;
import Blogproject.springbootrestapi.payload.RegisterDtoGoogle;
import Blogproject.springbootrestapi.repository.UserRepository;
import Blogproject.springbootrestapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.sql.PreparedStatement;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    //Build login rest API
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto)
    {
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    //build register api
    @PostMapping(value ={"/register","/signup"})
    public ResponseEntity<String> register (@RequestBody RegisterDto registerDto)
    {
         String response = authService.register(registerDto);
         return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // build a register from google
    @PostMapping(value ={"/registerWithGoogle"})
    public ResponseEntity<String> registerWithGoogle(@RequestBody RegisterDtoGoogle registerDtoGoogle)
    {
        String response = authService.registerFromGoogle(registerDtoGoogle);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/check-user")
    public ResponseEntity<?> checkUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        // Check if the user exists in your database

        // add check for email in database
        if(userRepository.existsByEmail(email))
        {
            //throw new BlogApiException(HttpStatus.BAD_REQUEST,"Email is already exists!");
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.noContent().build();
        }

    }
}
