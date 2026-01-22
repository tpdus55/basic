package com.beyond.basic.b2_board.author.dtos;

import com.beyond.basic.b2_board.author.domain.Author;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String street;
    private String zipCode;
    @OneToOne(fetch = FetchType.LAZY)
//    unique설정을 해줘야 1:1관계가 됨
    @JoinColumn(name = "author_id",unique = true, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),nullable = false)
    private Author author;

}
