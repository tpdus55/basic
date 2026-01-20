package com.beyond.basic.b2_board.author.domain;

import lombok.*;

//domain은 db의 형태와 똑같이 설정
//조회할때 id값 필요
//Builder패턴은 AllArgs 생성자 기반으로 동작
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private Long id;
    private String name;
    private String email;
    private String password;
}
