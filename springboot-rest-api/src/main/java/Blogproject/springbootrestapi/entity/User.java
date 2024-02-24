package Blogproject.springbootrestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //@Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    //@Column(nullable = false, unique = false)
    private String password;

    private String photoURL;
    private String bio;
    private String about;
    //private String category;

    @OneToMany(mappedBy = "user")
    private List<Post> myList ;

    @ManyToMany
    @JoinTable(name = "user_saved_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> savedPosts;

    @ManyToMany
    @JoinTable(name = "user_reading_history")
    private List<Post> readingHistory ;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="user_roles",
                joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;
}
