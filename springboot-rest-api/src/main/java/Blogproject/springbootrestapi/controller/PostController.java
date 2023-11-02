package Blogproject.springbootrestapi.controller;

import Blogproject.springbootrestapi.payload.PostDto;
import Blogproject.springbootrestapi.payload.PostResponse;
import Blogproject.springbootrestapi.service.PostService;
import Blogproject.springbootrestapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping()
@Tag(name = "CRUD Rest APIs for Post Ressources")
public class PostController {

    //@autowired is not needed for only one argument contructor
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )

    @SecurityRequirement(
            name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("api/v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto
    ){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Post REST API",
            description = "Get All Post REST API is used to fetch all the post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // Get all
    @GetMapping("api/v1/posts")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value= "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize,sortBy, sortDir);
    }

//    @Operation(
//            summary = "Get Post By Id REST API",
//            description = "Get Post REST API is used to get single post from database"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "Http Status 200 SUCCESS"
//    )
    // Versioning concept
//    @GetMapping(value = "api/posts/{id}", produces = "application/vnd.ouchar.v2+json")
//    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable(name="id") long PostId)
//    {
//        PostDto postDto = postService.getPostById(PostId);
//        PostDtoV2 postDtoV2 = new PostDtoV2();
//        postDtoV2.setId(postDto.getId());
//        postDtoV2.setTitle(postDto.getTitle());
//        postDtoV2.setContent(postDto.getContent());
//        postDtoV2.setDescription(postDto.getDescription());
//        List<String> tags = new ArrayList<>();
//        tags.add("Java");
//        tags.add("Spring");
//        tags.add("Leetcode");
//        postDtoV2.setTags(tags);
//
//        return ResponseEntity.ok(postDtoV2);
//    }

    @GetMapping(value = "api/v1/posts/{id}",produces = "application/vnd.ouchar.v1+json")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable(name="id") long PostId)
    {
        return ResponseEntity.ok(postService.getPostById(PostId));
    }

    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST API is used to update a particular post in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )

    //Update by id
    @SecurityRequirement(
            name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid  @RequestBody PostDto postDto, @PathVariable long id)
    {
        PostDto postResponse = postService.updatePost(postDto,id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API is used to delete a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    //Delete by Id
    @SecurityRequirement(
            name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id)
    {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post Entity deleted successfully", HttpStatus.OK);
    }

    //Build get post by category rest API
    @GetMapping("api/v1/posts/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId)
    {
        List<PostDto> postDtos = postService.getPostsByCategoryId(categoryId);
        return ResponseEntity.ok(postDtos);
    }



}
