package com.beyond.basic.b2_board.post.controller;

import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //    게시글 등록
    @PostMapping("/post/create")
    public ResponseEntity<?> save(@RequestBody PostCreateDto dto){
        postService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }
    //    게시글 목록조회
    @GetMapping("/posts")
    public List<PostListDto> findAll(){
        List<PostListDto> postListDto = postService.findAll();
        return postListDto;
    }
    //    게시글 상세조회
    @GetMapping("/post/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        PostDetailDto dto = postService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    //    게시글 삭제
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        postService.delete(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }
}
