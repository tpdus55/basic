package com.beyond.basic.b2_board.common.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

//기본적으로는 Entity는 상속이 불가능한 구조.
//MappedSuperClass 어노테이션 사용 시 상속관계 가능
@MappedSuperclass
//getter설정을 따로 해주지 않으면 Author와 Post에서 getter가 잘 되지않을것
@Getter
public class BaseTimeEntity {
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;
}
