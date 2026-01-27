package com.beyond.basic.b2_board.post.respository;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByDelYn(String delYn);
//    List<Post> findAllByAuthorIdAndDelYn(Long authorId, String delYn);

//    jpql : 객체지향형
//    jpql을 활용한 일반 inner join : N+1문제 해결 X
//    jpql과 raw쿼리의 차이
//    1) jpql을 사용한 inner join시 별도의 on조건 필요 X
//    2) jpql은 컴파일타임에 에러를 체크해줌
//    순수 raw : select p.* from post p inner join author a on a.id=p.author_id;(필터링만 해줌)
    @Query("select p from Post p inner join p.author")
    List<Post> findAllInnerJoin();

//    jpql을 활용한 inner join(fetch) : N+1문제 해결 O
//    fetch join은 post와 author 둘다 나오게
//    순수 raw : select * from post p inner join author a on a.id=p.author_id;
    @Query("select p from Post p inner join fetch p.author")
    List<Post> findAllFetchInnerJoin();
}
