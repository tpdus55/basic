package com.beyond.basic.b2_board.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
//예외를 잡아가서 사용자에게 응답까지 주는 Handler
//Controller 계층 앞에 있는 Handler

//컨트롤러 어노테이션이 붙어잇는 모든 클래스의 예외를 아래 클래스에서 인터셉팅
//전역적 예외처리를 하면 컨트롤러의 try-catch 문은 필요없어짐
@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegal(IllegalArgumentException e){
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(400)
                .error_message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValidException(MethodArgumentNotValidException e){
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(400)
                .error_message(e.getFieldError().getDefaultMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElement(NoSuchElementException e){
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(404)
                .error_message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e){
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(500)
                .error_message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }

}
