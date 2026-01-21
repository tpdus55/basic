package com.beyond.basic.b2_board.post.controller;

import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import com.beyond.basic.b2_board.common.CommonErrorDto;
import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

//    게시글 등록
    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody PostCreateDto dto){
        try{
            postService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        }catch(NoSuchElementException e){
            e.printStackTrace();
            CommonErrorDto dtos = CommonErrorDto.builder()
                    .status_code(404)
                    .error_message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dtos);
        }

    }
//    게시글 목록
    @GetMapping("/posts")
    public List<PostListDto> findAll(){
        List<PostListDto> postListDto = postService.findAll();
        return postListDto;
    }
//    게시글 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            PostDetailDto dto = postService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }catch(NoSuchElementException e){
            e.printStackTrace();
            CommonErrorDto dtos = CommonErrorDto.builder()
                    .status_code(404)
                    .error_message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dtos);
        }

    }
//    게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            postService.delete(id);
            return ResponseEntity.status(HttpStatus.CREATED).body("ok");
        }catch(NoSuchElementException e){
            e.printStackTrace();
            CommonErrorDto dtos = CommonErrorDto.builder()
                    .status_code(404)
                    .error_message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dtos);
        }
    }
}
