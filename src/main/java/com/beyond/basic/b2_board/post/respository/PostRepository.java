package com.beyond.basic.b2_board.post.respository;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
//    삭제 안된 글이 여러 개 있을 수 있음 -> list로 설정, 결과가 하나면 Optional설정
    List<Post> findByDelYn(String delYn);
}
