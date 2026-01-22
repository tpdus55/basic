package com.beyond.basic.b2_board.author.domain;

import com.beyond.basic.b2_board.author.dtos.Address;
import com.beyond.basic.b2_board.common.BaseTimeEntity;
import com.beyond.basic.b2_board.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class Author extends BaseTimeEntity {
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

//    enum타입은 내부적으로 숫자값을 가지고 있으나, Enumerated(EnumType.STRING)를 사용하여 문자형태로 저장하겠다는 어노테이션
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

//    일반적으로 OneToMany는 선택사항, ManyToOne은 필수사항
//    mappedBy : ManyToOne쪽에 변수명을 문자열로 지정.(변수명은 Post쪽의 Author author를 말하는 것) -> 조회해야할 컬럼을 명시
//    연관관계의 주인설정 -> 즉 이쪽에서는 fk를 가지고 있는 곳은 Post다 라는 뜻 (연관관계의 주인은 author변수를 가지고 있는 Post에 있음을 명시)
//    orphanRemoval : 자식의 자식까지 연쇄적으로 삭제해야할 경우 모든 부모에 orphanRemoval = true 옵션 추가해줘야함
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
//        persist를 쓸때에는 반드시 초기화해줘야함
    private List<Post> postList = new ArrayList<>();

    @OneToOne(mappedBy = "author",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    public void updatePassword(String password){
        this.password = password;
    }

}
