package Blogproject.springbootrestapi.payload;

import Blogproject.springbootrestapi.entity.Comment;
import Blogproject.springbootrestapi.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
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
    private Long id;
    private String read_time;
    //Post content should not be null or empty
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    @NotEmpty
    private String content;
    private String created_date;
    //@NotEmpty
    private Long user_id;
    private Set<CommentDto> comments;
    private List<PostDto> savedPosts;
    private String topic;
    private int claps;
    //@Schema(description = "Blog Post Category")
   // private Long categoryId;


}
