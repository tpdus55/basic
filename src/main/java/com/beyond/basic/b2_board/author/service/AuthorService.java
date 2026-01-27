package com.beyond.basic.b2_board.author.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.*;
import com.beyond.basic.b2_board.author.repository.*;
import com.beyond.basic.b2_board.common.auth.JwtTokenFilter;
import com.beyond.basic.b2_board.post.domain.Post;
import com.beyond.basic.b2_board.post.respository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

//Component 어노테이션을 통해 싱글톤(단 하나의)객체가 생성되고, 스프링에 의해 스프링 컨텍스트에서 관리됨
@Service
////반드시 초기화 되어야하는 필드(final 변수 등)를 대상으로 생성자를 자동생성해주는 어노테이션
//@RequiredArgsConstructor
//스프링에서 jpa를 활용할때 트랜잭션처리(commit, rollback)지원
//commit의 기준점 : 메서드 정상 종료 시점.
//rollback의 기준점 : 예외발생 했을경우
@Transactional
public class AuthorService {
////    의존성 주입(DI) 방법1. 필드주입 : Autowired 어노테이션 사용 (간편방식), final X, 다형성 설계 X
//    @Autowired
//    private  AuthorRepository authorRepository;

//    의존성 주입(DI) 방법2. 생성자 주입방식(가장 많이 사용되는 방식)
//    장점 1) final을 통해 상수로 사용가능(안정성 향상) : 생성자에서 값을 초기화해주기 때문에 가능
//    장점 2) 다형성 구현 가능(interface 사용가능)
//    장점 3) 순환참조방지(컴파일타임에 에러 check)
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenFilter jwtTokenFilter;
//    생성자가 하나밖에 없을 때에는 Autowired 생략 가능
    @Autowired
    public AuthorService(AuthorRepository authorRepository, PostRepository postRepository, PasswordEncoder passwordEncoder, JwtTokenFilter jwtTokenFilter){
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenFilter = jwtTokenFilter;
    }

//    의존성 주입 방법3. RequiredArgsConstructor 어노테이션 사용
//    반드시 초기화 되어야 하는 필드(final)를 선언하고, 위 어노테이션 선언 시 생성자주입방식으로 의존성이 주입됨
//    단점 :  다형성 설계 불가
//    private final AuthorRepository authorRepository;

    public void save(AuthorCreateDto dto){
//        (dto->Author)
////        방법1. 객체 직접 조립
////        1-1) 생성자만을 활용한 객체 조립
//        Author author = new Author(null,dto.getName(), dto.getEmail(), dto.getPassword());
//        1-2) Builder 패턴을 활용해 객체 조립(표준방식)
////        장점 : 1) 매개변수의 개수의 유연성 2) 매개변수의 순서의 유연성
//        Author author = Author.builder()
//                .name(dto.getName())
//                .email(dto.getEmail())
//                .password(dto.getPassword())
//                .build();
////        방법2. toEntity(dto->entity), fromEntity(entity->dto) 패턴을 통한 객체 조립
////        객체조립이라는 반복적인 작업을 별도의 코드로 떼어내 공통화하는 작업

//        email 중복여부 검증
//        Optional<Author> optAuthor = authorRepository.findByEmail(dto.getEmail());
        if(authorRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이메일이 중복입니다.");
        }

        Author author = dto.toEntity(passwordEncoder.encode(dto.getPassword()));
        Author authorDb = authorRepository.save(author); //영속성 컨텍스트에 넣어놓고
//        cascade persist를 활용한 예시
        author.getPostList().add(Post.builder().title("안녕하세요").author(authorDb).build()); //save한 후 수정

////        cascade 옵션이 아닌 예시
//        postRepository.save(Post.builder().title("안녕하세요").author(authorDb).build());


////        예외 발생 시 transactional 어노테이션에 의해 rollback처리
//        authorRepository.findById(10L).orElseThrow(()-> new NoSuchElementException("entity is not found"));
    }

//    트랜잭션 처리가 필요없는 조회만 있는 메서드의 경우 성능향상을 위해 readOnly 처리
    @Transactional(readOnly = true)
    public AuthorDetailDto findById(Long id){
        Optional<Author> optAuthor = authorRepository.findById(id);
        Author author = optAuthor.orElseThrow(()-> new NoSuchElementException("entity is not found"));

//        List<Post> postList = postRepository.findAllByAuthorIdAndDelYn(author.getId(),"N");
////        dto조립(Author->dto)
//        AuthorDetailDto dto = AuthorDetailDto.builder()
//                .id(author.getId())
//                .name(author.getName())
//                .email(author.getEmail())
//                .password(author.getPassword())
//                .build();
//        fromEntity는 아직 dto객체가 만들어지지 않은 상태이므로 static 메서드로 설계
//        AuthorDetailDto dto = AuthorDetailDto.fromEntity(author,0);
        AuthorDetailDto dto = AuthorDetailDto.fromEntity(author);

        return dto;
    }
    @Transactional(readOnly = true)
    public AuthorDetailDto myInfo(){
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Optional<Author> optAuthor = authorRepository.findByEmail(email);
        Author author = optAuthor.orElseThrow(()-> new NoSuchElementException("entity is not found"));
        AuthorDetailDto dto = AuthorDetailDto.fromEntity(author);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<AuthorListDto> findAll(){
//        List<Author> authorList = authorRepository.findAll();
////        dto 조립
//        List<AuthorListDto> authorListDtos = new ArrayList<>();
//        for(Author a : authorList){
////            AuthorListDto의 객체에 authorList안에 있는 것들을 넣어줌
//            AuthorListDto dto = new AuthorListDto(a.getId(),a.getName(),a.getEmail());
//            authorListDtos.add(dto);
//        }
        List<AuthorListDto> authorListDtos =
                authorRepository.findAll()
                        .stream().map(a-> AuthorListDto.fromEntity(a)).collect(Collectors.toList());
        return authorListDtos;
    }

    public void delete(Long id){
//        데이터 조회 후 없다면 예외처리
        Author author = authorRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("entity is not found"));
//        삭제 작업
        authorRepository.delete(author);
    }
    //    비밀번호 수정
    public void update(AuthorUpdatePwDto dto){
        Author author =  authorRepository.findByEmail(dto.getEmail())
                .orElseThrow(()-> new EntityNotFoundException("Entity is not found"));
        author.updatePassword(dto.getPassword());

////        insert,update 모두 save메서드 사용 -> 변경감지로 대체
//        authorRepository.save(author);

//        1. 영속성 컨텍스트 : 애플리케이션과 DB사이에서 객체를 보관하는 가상의 DB 역할수행
//        장점 1) 쓰기지연 : insert, update 등의 작업사항을 즉시 실행하지 않고, 커밋시점에 모아서 실행(성능향상)
//            2) 변경감지(dirty checking) : 영속상태(managed)의 엔티티는 트랜잭션 커밋시점에 변경감지를 통해 별도의 save없이 DB에 반영
    }
    public Author login(AuthorEmailPwDto dto){
        Optional<Author> optAuthor = authorRepository.findByEmail(dto.getEmail());
        boolean check = true;
        if(!optAuthor.isPresent()){
            check = false;
        }else{
            if(!passwordEncoder.matches(dto.getPassword(), optAuthor.get().getPassword())){
                check = false;
            }
        }
        if(!check){
            throw new IllegalArgumentException("email 또는 비밀번호가 일치하지 않습니다");
        }
        return optAuthor.get();
    }
}
