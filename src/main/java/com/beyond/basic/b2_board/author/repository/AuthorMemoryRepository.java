package com.beyond.basic.b2_board.author.repository;

import com.beyond.basic.b2_board.author.domain.Author;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorMemoryRepository {
    private List<Author> authorList = new ArrayList<>(); //초기화 시켰기때문에 Optional로 안감쌈
    private static Long staticId=1L;

    public void save(Author author){
        this.authorList.add(author);
//        author.setId(staticId++);
    }
    public List<Author> findAll(){
        return this.authorList;
    }
    public Optional<Author> findById(Long id){
        Author author = null;
        for(Author a : this.authorList){
            if(a.getId().equals(id)){
                author = a;
            }
        }
        return Optional.ofNullable(author); //null일 수도 있을 때
    }
}
