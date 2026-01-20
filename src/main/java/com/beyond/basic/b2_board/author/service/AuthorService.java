package com.beyond.basic.b2_board.author.service;

import com.beyond.basic.b2_board.author.domain.Author;
import com.beyond.basic.b2_board.author.dtos.AuthorCreateDto;
import com.beyond.basic.b2_board.author.dtos.AuthorDetailDto;
import com.beyond.basic.b2_board.author.dtos.AuthorListDto;
import com.beyond.basic.b2_board.author.repository.AuthorJdbcRepository;
import com.beyond.basic.b2_board.author.repository.AuthorMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

//Component 어노테이션을 통해 싱글톤(단 하나의)객체가 생성되고, 스프링에 의해 스프링 컨텍스트에서 관리됨
@Service
////반드시 초기화 되어야하는 필드(final 변수 등)를 대상으로 생성자를 자동생성해주는 어노테이션
//@RequiredArgsConstructor
public class AuthorService {
////    의존성 주입(DI) 방법1. 필드주입 : Autowired 어노테이션 사용 (간편방식), final X, 다형성 설계 X
//    @Autowired
//    private  AuthorRepository authorRepository;

//    의존성 주입(DI) 방법2. 생성자 주입방식(가장 많이 사용되는 방식)
//    장점 1) final을 통해 상수로 사용가능(안정성 향상) : 생성자에서 값을 초기화해주기 때문에 가능
//    장점 2) 다형성 구현 가능(interface 사용가능)
//    장점 3) 순환참조방지(컴파일타임에 에러 check)
    private final AuthorJdbcRepository authorRepository;
//    생성자가 하나밖에 없을 때에는 Autowired 생략 가능
    @Autowired
    public AuthorService(AuthorJdbcRepository authorRepository){
        this.authorRepository = authorRepository;
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
        Author author = dto.toEntity();
        authorRepository.save(author);
    }
    public AuthorDetailDto findById(Long id){
        Optional<Author> optAuthor = authorRepository.findById(id);
        Author author = optAuthor.orElseThrow(()-> new NoSuchElementException("entity is not found"));
////        dto조립(Author->dto)
//        AuthorDetailDto dto = AuthorDetailDto.builder()
//                .id(author.getId())
//                .name(author.getName())
//                .email(author.getEmail())
//                .password(author.getPassword())
//                .build();
//        fromEntity는 아직 dto객체가 만들어지지 않은 상태이므로 static 메서드로 설계
        AuthorDetailDto dto = AuthorDetailDto.fromEntity(author);
        return dto;
    }
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

}
