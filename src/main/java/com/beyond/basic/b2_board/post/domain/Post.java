package com.beyond.basic.b2_board.post.domain;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @ToString
@Entity
@Builder
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 3000)
    private String contents;
    private String category;
//    @Column(nullable = false)
//    private Long authorId;

//    ManyToOne을 통해 FK설정(author_id 컬럼)
//    ManyToOne을 통해 author_id컬럼으로 author객체 조회 및 객체자동생성
//    fetch lazy(지연로딩) : author 객체를 사용하지 않는 한, author객체 생성X(서버부하감소)
    @ManyToOne(fetch = FetchType.LAZY)
//    ManyToOne 어노테이션만 추가하더라도, 아래옵션이 생략되어있는 것. fk를 설정하지 않고자 할때에는 NO_CONSTRAINT 설정하면됨
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),nullable = false)
    private Author author;
    @Builder.Default //default 값은 이렇게 세팅하면 됨(기본적)
    private String delYn = "N";


    public void delete(){
        this.delYn = "Y";
    }
}
