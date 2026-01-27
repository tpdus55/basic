package com.beyond.basic.b2_board.author.dtos;

import com.beyond.basic.b2_board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorEmailPwDto {
    private String email;
    private String password;

    public Author toEntity(){
        return Author.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}
