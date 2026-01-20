package com.beyond.basic.b2_board.author.controller;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Controller+ResponseBody
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

//    회원가입
    @PostMapping("/create")
    public String authorCreate(@RequestBody AuthorCreateDto dto){
        authorService.save(dto);
        System.out.println(dto);
        return "ok";
    }
//    회원목록조회
    @GetMapping("/list")
    public List<AuthorListDto> findAll(){
        List<AuthorListDto> dtoList = authorService.findAll();
        return dtoList;
    }
//    회원상세조회
    @GetMapping("/{id}")
    public AuthorDetailDto findbyId(@PathVariable Long id){
        AuthorDetailDto dto = authorService.findById(id);
        return dto;
    }
//    회원탈퇴
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        System.out.println(id);
        return "ok";
    }
}
//dto는 service에서 entity로 변환(중요*******)
