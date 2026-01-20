package com.beyond.basic.b2_board.author.repository;

import com.beyond.basic.b2_board.author.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorJdbcRepository {
// DataSource는 jdbc의 DB관리객체
    private final DataSource dataSource;
    @Autowired
    public AuthorJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//    jdbc의 단점
//    1. 쿼리 직접 작성 : 1) raw 쿼리에서 오타가 나도 컴파일 에러 X 2). 데이터 추가 시, 컬럼의 매핑을 수작업
//    2. 데이터 조회 후 객체조립을 수작업
    public void save(Author author){
        try {
            Connection connection = dataSource.getConnection();
            String sql = "insert into author(name,email,password) values(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, author.getName());
            ps.setString(2, author.getEmail());
            ps.setString(3, author.getPassword());
//            excuteUpdate : 추가/수정, excuteQuery : 조회
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Author> findById(Long inputId){
        Author author = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select * from author where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1,inputId);
            ResultSet rs = ps.executeQuery(); //조회결과의 경우 ResultSet 자료구조 사용해야함
            if (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                author = Author.builder()
                        .id(id).name(name).email(email).password(password)
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(author); //null일 수도 있을 때
    }

    public List<Author> findAll(){
        List<Author> authorList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select * from author";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                Author author = Author.builder()
                        .id(id).name(name).email(email).password(password)
                        .build();
                authorList.add(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authorList;
    }

}
