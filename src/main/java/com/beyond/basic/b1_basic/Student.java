package com.beyond.basic.b1_basic;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    private String name;
    private String email;
    private List<Score> scores;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
//    내부클래스로 Score클래스 설정
    static class Score{
        private String subject;
        private int point;
    }
}
