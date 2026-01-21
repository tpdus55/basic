package com.beyond.basic.b2_board.author.controller;

import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//ResponseEntity : http응답객체의 body뿐 아니라. 상태코드 및 헤더요소를 바꿔야 하는 경우에 사용
@RestController
@RequestMapping("/response_entity")
public class ResponseEntityController {

//    @ResponseStatus 어노테이션 사용 : 상황에 따른 분기처리의 어려움
//    상태코드를 헤더부분에서 보여주고싶을때
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/annotation")
    public String annotation(){
        return "ok";
    }

//    ResponseEntity 방식
    @GetMapping("/method1")
    public ResponseEntity<String> method1(){
        return new ResponseEntity<>("OK", HttpStatus.NOT_FOUND); //enum클래스에서 원하는 방식을 찾는 방식
    }

//    이 방식(<?>)을 통해 분기처리 실행
    @GetMapping("/method2")
    public ResponseEntity<?> method2(){
        return new ResponseEntity<>("OK", HttpStatus.NOT_FOUND); //enum클래스에서 원하는 방식을 찾는 방식
    }

//    가장 추천하는 방식
//    ResponseEntity, ?(아무거나 다 들어올 수 있음), 빌더패턴을 사용하여 status 상태코드, header, body를 쉽게 생성
    @GetMapping("/method3")
    public ResponseEntity<?> method3(){
        AuthorDetailDto dto = AuthorDetailDto.builder()
                .id(1L).name("hongildong").email("hong@naver.com").password("1234")
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
//                .header("Content-Type", "application/json") //header를 바꾸고 싶으면 이 방식으로
                .body(dto); //builder는 없지만 builder 패턴
    }
//이 방식은 추천 X
    @GetMapping("/method4")
    public ResponseEntity<?> method4(){
        AuthorDetailDto dto = AuthorDetailDto.builder()
                .id(1L).name("hongildong").email("hong@naver.com").password("1234")
                .build();
        return ResponseEntity.ok(dto);
    }


}
