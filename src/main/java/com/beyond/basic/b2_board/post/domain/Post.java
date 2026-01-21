package com.beyond.basic.b2_board.post.domain;

import com.beyond.basic.b2_board.author.domain.Author;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @ToString
@Entity
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 3000)
    private String contents;
    private String category;
    @Builder.Default
    private String delYn = "N";
    @Column(nullable = false)
    private String authorEmail;

    public void delete(){
        this.delYn = "Y";
    }
}
