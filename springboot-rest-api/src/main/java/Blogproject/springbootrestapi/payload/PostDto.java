package Blogproject.springbootrestapi.payload;

import Blogproject.springbootrestapi.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        description = "PostDto model Information"
)
public class PostDto {

    //Title should not empty or null
    //Title should have at least 2 charaters
    @NotEmpty
    @Size(min = 2,message = "Post Title Should have at least 2 characters")
    private String title;


    //Post content should not be null or empty
    @NotEmpty
    private String content;

    //@NotEmpty
    private Long user_id;
    private Set<CommentDto> comments;
    //@Schema(description = "Blog Post Category")
   // private Long categoryId;


}
