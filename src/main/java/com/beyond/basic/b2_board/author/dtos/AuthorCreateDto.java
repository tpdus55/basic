package com.beyond.basic.b2_board.author.dtos;

//못바꾸는 값이 있으면 빼버림!

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthorCreateDto {
    private String name;
    private String email;
    private String password;
}
