package com.HrimDev.project.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//JPAAuditing으로 생성시간/수정시간 자동화
@Getter
@MappedSuperclass   //JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class)//BaseTimeEntity클래스에 Auditing 기능을 포함
public abstract class BaseTimeEntity {//Entity의 상위클래스
    
    @CreatedDate//Entity가 생성되어 저장될 때 시간이 자동 저장됨
    private LocalDateTime createdDate;
    
    @LastModifiedDate//조회한 Entity의 값을 변경할 때 시간이 자동 저장됨
    private LocalDateTime modifiedDate;
}
