package Blogproject.springbootrestapi.service.impl;

import Blogproject.springbootrestapi.entity.Category;
import Blogproject.springbootrestapi.entity.Post;
import Blogproject.springbootrestapi.entity.User;
import Blogproject.springbootrestapi.exception.RessourceNotFoundException;
import Blogproject.springbootrestapi.payload.PostDto;
import Blogproject.springbootrestapi.payload.PostResponse;
import Blogproject.springbootrestapi.repository.CategoryRepository;
import Blogproject.springbootrestapi.repository.PostRepository;
import Blogproject.springbootrestapi.repository.UserRepository;
import Blogproject.springbootrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    //private List<Post> allPosts = new ArrayList<>();

    //Autowired is not needed here
    public PostServiceImpl(ModelMapper mapper,
                           PostRepository postRepository,
                           CategoryRepository categoryRepository,
                            UserRepository userRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
        this.userRepository= userRepository;
        //this.allPosts = allPosts;
    }



    @Override
    public PostDto createPost(PostDto postDto) {

        //Category category = categoryRepository.findById(postDto.getCategoryId())
            // .orElseThrow(()->new RessourceNotFoundException("Category","id",postDto.getCategoryId()));

        // Fetch the user from the database based on user_id
        User user = userRepository.findById(postDto.getUser_id())
                .orElseThrow(() -> new RessourceNotFoundException("User", "id", postDto.getUser_id()));

        // Convert Dto to Entity
        Post post = mapToEntity(postDto);

        //post.setCreated_date(postDto.getCreated_date());

        // Associate the user with the post
        post.setUser(user);

        // Save the post to the database
        Post newPost = postRepository.save(post);

        // Convert entity to Dto
        return mapToDTO(newPost);
    }

    //Convert Entity to Dto

    private PostDto mapToDTOV2(Post post, Long userId) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setCreated_date(post.getCreated_date());
        postDto.setRead_time(post.getRead_time());
        postDto.setUser_id(userId); // Set the user_id
        //postDto.setComments(mapCommentsToDTO(post.getComments()));
        postDto.setTopic(post.getTopic());
        postDto.setClaps(post.getClaps());
        return postDto;
    }

    private PostDto mapToDTO(Post post)
    {
        //PostDto postDto = mapper.map(post,PostDto.class);
        PostDto postDto = new PostDto();
       postDto.setId(post.getId());
       postDto.setUser_id(post.getUser().getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setClaps(post.getClaps());
        postDto.setCreated_date(post.getCreated_date());
        postDto.setRead_time(post.getRead_time());
        postDto.setTopic(post.getTopic());


        return postDto;

    }

    private Post mapToEntity(PostDto postDto)
    { Post post = mapper.map(postDto,Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        //create a pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        //get content from page object
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {

        Post post = postRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("Post","id",id));

        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {

        //Category category = categoryRepository.findById(postDto.getCategoryId())
          //      .orElseThrow(()->new RessourceNotFoundException("Category","id",postDto.getCategoryId()));



        //get post by id from the database, throw post does not exist
        Post post = postRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("Post","id",id));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        //post.setCategory(category);
        Post updatePost = postRepository.save(post);
        return mapToDTO(updatePost);
    }

    @Override
    public void deletePostById(long id) {
        //get post by id from the database, throw post does not exist
        Post post = postRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new RessourceNotFoundException("Category","id",categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map((post)->mapToDTO(post))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostByTitle(String title) {
        Post post = postRepository.findPostByTitle(title);
        return mapToDTO(post);
    }


    @Override
    public List<PostDto> getRandomPosts() {
        List<Post> allPosts = postRepository.findAll();

        // Shuffle the list randomly
        Collections.shuffle(allPosts);

        // Determine the number of posts to return (either 10 or all)
        int numberOfPostsToReturn = Math.min(allPosts.size(), 10);

        // Get a sublist of posts (either 10 or all)
        List<Post> randomPosts = allPosts.subList(0, numberOfPostsToReturn);

        // Convert the list of Post entities to a list of PostDto objects
        //List<PostDto> postDtos = randomPosts.stream().map(this::mapToDTO).collect(Collectors.toList());
        List<PostDto> postDtos = randomPosts.stream()
                .map(post -> mapToDTOV2(post, post.getUser().getId()))
                .collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostByTopic(String topic) {

        List<Post> posts = postRepository.findPostByTopic(topic);

        return posts.stream().map((post)->mapToDTO(post))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getPopularTopics() {
        // Fetch all posts from the database
        List<Post> allPosts = postRepository.findAll();

        // Count occurrences of each topic
        Map<String, Long> topicCounts = allPosts.stream()
                .collect(Collectors.groupingBy(Post::getTopic, Collectors.counting()));

        // Sort topics by their occurrence count in descending order
        List<String> popularTopics = topicCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5) // Get the top 5 most popular topics
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return popularTopics;
    }

    @Override
    public List<PostDto> getUserList(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RessourceNotFoundException("User","id",userId));
        List<Post> posts = user.getSavedPosts();

        return posts.stream().map((post)->mapToDTO(post))
                .collect(Collectors.toList());
    }

}
