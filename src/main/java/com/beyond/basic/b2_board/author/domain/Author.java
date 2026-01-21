package com.beyond.basic.b2_board.author.domain;

import jakarta.persistence.*;
import lombok.*;

//domain은 db의 형태와 똑같이 설정
//조회할때 id값 필요
//Builder패턴은 AllArgs 생성자 기반으로 동작
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
//JPA에게 Entity 관리를 위임하기 위한 어노테이션
@Entity
public class Author {
    @Id //pk설정
//    identity-> auto_increment 설정. auto-> id생성전략을 jpa에게 자동설정하도록 위임
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long -> bigInt, String -> varchar, Integer -> int
    private Long id;
//    변수명이 컬럼명으로 그대로 생성. camel case는 언더스코어로 변경. ex) nickName -> nick_name
    private String name;
//    길이를 varchar(50) 제약조건(unique,not null) 설정
//    varchar 설정 안하면 디폴트로 varchar(255)

    @Column(length = 50, unique = true, nullable = false)
    private String email;
//    @Column(name = "pw") : 컬럼명의 변경이 가능하나, 일반적으로 변경하지 않고 그냥 일치시킴.
//    @Setter
    private String password;

    public void updatePassword(String password){
        this.password = password;
    }

}
