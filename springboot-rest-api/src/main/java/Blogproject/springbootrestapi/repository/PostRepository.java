package Blogproject.springbootrestapi.repository;

import Blogproject.springbootrestapi.entity.Post;
import Blogproject.springbootrestapi.payload.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository  extends JpaRepository<Post, Long> {


    List<Post> findByCategoryId(Long categoryId);

    Post findPostByTitle (String title);

    List<Post> findPostByTopic(String topic);


}
