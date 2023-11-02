package Blogproject.springbootrestapi.payload;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDtoGoogle {
    private String name;
    private String email;
    private Long id;
    private String photoURL;
}


