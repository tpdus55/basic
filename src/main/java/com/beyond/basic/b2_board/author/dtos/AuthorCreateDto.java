package com.beyond.basic.b2_board.author.dtos;

//못바꾸는 값이 있으면 빼버림!

import com.beyond.basic.b2_board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
//dto 계층은 엔티티만큼의 안정성을 우선하기보다는, 편의를 위해 setter도 일반적으로 추가.
@Data
public class AuthorCreateDto {
    private String name;
    private String email;
    private String password;

    public Author toEntity(){
        return Author.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
