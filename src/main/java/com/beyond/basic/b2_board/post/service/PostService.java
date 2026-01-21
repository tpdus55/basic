package com.beyond.basic.b2_board.post.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.repository.AuthorRepository;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.dtos.PostCreateDto;
import com.beyond.basic.b2_board.post.dtos.PostDetailDto;
import com.beyond.basic.b2_board.post.dtos.PostListDto;
import com.beyond.basic.b2_board.post.respository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void save(PostCreateDto dto){
//        authorEmail 존재 유효성 체크
        authorRepository.findByEmail(dto.getAuthorEmail())
                .orElseThrow(()->new NoSuchElementException("존재하지 않은 이메일입니다."));
        Post post = dto.toEntity();
        postRepository.save(post);
    }
    @Transactional(readOnly = true)
    public List<PostListDto> findAll(){
        List<Post> postList = postRepository.findByDelYn("N"); //게시글삭제가 안된 것들만 목록에 나오게 함
        List<PostListDto> postListDtos = new ArrayList<>();
        for(Post p : postList){
            PostListDto dto = PostListDto.fromEntity(p);
            postListDtos.add(dto);
        }
        return postListDtos;
    }
    @Transactional(readOnly = true)
    public PostDetailDto findById(Long id){
        Optional<Post> optPost = postRepository.findById(id);
        Post post = optPost.orElseThrow(()-> new NoSuchElementException("entity is not found"));
        PostDetailDto dto = PostDetailDto.fromEntity(post);
        return dto;
    }

    public void delete(Long id){
        Post post = postRepository.findById(id).orElseThrow(()->new NoSuchElementException("작성한 게시글이 없습니다."));
        post.delete();
//        실제로 db에서 삭제하는 것이 아닌 update형식으로 delete사용
    }
}
