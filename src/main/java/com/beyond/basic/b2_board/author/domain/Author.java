package com.beyond.basic.b2_board.author.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//domain은 db의 형태와 똑같이 설정
//조회할때 id값 필요
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private Long id;
    private String name;
    private String email;
    private String password;
}
