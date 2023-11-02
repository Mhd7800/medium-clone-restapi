package Blogproject.springbootrestapi.service.impl;

import Blogproject.springbootrestapi.entity.Comment;
import Blogproject.springbootrestapi.entity.Post;
import Blogproject.springbootrestapi.exception.BlogApiException;
import Blogproject.springbootrestapi.exception.RessourceNotFoundException;
import Blogproject.springbootrestapi.payload.CommentDto;
import Blogproject.springbootrestapi.repository.CommentRepository;
import Blogproject.springbootrestapi.repository.PostRepository;
import Blogproject.springbootrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;
    public CommentServiceImpl(ModelMapper mapper,CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        //retrieve post entity by id

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new RessourceNotFoundException("Post", "id", postId));

        //set post to comment entity
        comment.setPost(post);

        //save comment entity to database
        Comment newcomment = commentRepository.save(comment);

        return mapToDto(newcomment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //retrieve comments by postId

        List<Comment> comments = commentRepository.findByPostId(postId);

        //convert list of comment entities to list of comment dto's
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        //retrieve post entity by Id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new RessourceNotFoundException("Post", "id", postId));

        //retrieve comment entity by Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new RessourceNotFoundException("comment","id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
        //retrieve post entity by Id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new RessourceNotFoundException("Post", "id", postId));

        //retrieve comment entity by Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new RessourceNotFoundException("comment","id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        //retrieve post entity by Id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new RessourceNotFoundException("Post", "id", postId));

        //retrieve comment entity by Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new RessourceNotFoundException("comment","id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment)
    {
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setBody(comment.getBody());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());

        return commentDto;
    }

    private Comment mapToEntity (CommentDto commentDto)
    {
        Comment comment = mapper.map(commentDto,Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setBody(commentDto.getBody());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());

        return comment;
    }
}
