package com.beyond.basic.b2_board.author.repository;

import com.beyond.basic.b2_board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//SpringDataJpa를 사용하기 위해서는 JpaRepository인터페이스를 상속해야하고, 상속시에 Entity명과 pk타입을 제네릭에 설정
//JpaRepository를 상속함으로써 JpaRepository의 주요기능 (각종CRUD) 상속
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
//    save, findById(Optional), findAll(List), delete등은 사전에 JpaRepository에 구현되어 있음

//    그 외에 다른컬럼으로 조회할때에는 findBy+컬럼명 형식으로 선언하면 실행시점 자동구현.
//    단건인 경우에는 findBy+컬럼명, 여러건인 경우는 findAllBy+ 컬럼명
//    컬럼을 여러개 조회하고 싶을때에는 And, Or 등 사용
    Optional<Author> findByEmail(String email);
    List<Author> findAllByName(String name);
}
