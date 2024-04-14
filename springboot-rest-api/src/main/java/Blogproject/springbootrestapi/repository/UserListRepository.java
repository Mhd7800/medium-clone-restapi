package Blogproject.springbootrestapi.repository;

import Blogproject.springbootrestapi.entity.Post;
import Blogproject.springbootrestapi.entity.UserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserListRepository extends JpaRepository <UserList, Long> {
    List<Post> findAllByUserId(Long userId);
}
