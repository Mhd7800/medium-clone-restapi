package Blogproject.springbootrestapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PostDtoV2 {
    private Long id;
    @Schema(description = "Blog Post Title")
    //Title should not empty or null
    //Title should have at least 2 charaters
    @NotEmpty
    @Size(min = 2,message = "Post Title Should have at least 2 characters")
    private String title;

    @Schema(description = "Blog Post Description")

    //Post should not empty or null
    //Post should have at least 2 charaters
    @NotEmpty
    @Size(min = 10,message = "Post description should have at least 10 characters")
    private String description;

    @Schema(description = "Blog Post Content")
    //Post content should not be null or empty
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
    @Schema(description = "Blog Post Category")
    private Long categoryId;
    private List<String> tags;

}
