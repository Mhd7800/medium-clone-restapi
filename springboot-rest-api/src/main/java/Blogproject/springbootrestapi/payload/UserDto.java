package Blogproject.springbootrestapi.payload;


import Blogproject.springbootrestapi.entity.Comment;
import Blogproject.springbootrestapi.entity.Post;
import Blogproject.springbootrestapi.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {

    private Long id;
    private String name;
    private String username;

    private String email;

    private String photoURL;
    private String bio;
    private String about;

    //private List<Post> myList ;

    //private List<Post> savedList ;
    //private List<Post> readingHistory ;
    //private List<Comment> comments;

}
