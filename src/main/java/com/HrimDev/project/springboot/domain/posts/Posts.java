package com.HrimDev.project.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter           //lombok, Getter: 클래스 내 모든 필드의 Getter 메소드 자동생성
@NoArgsConstructor//lombok, NoArgsConstructor:기본 생성자 자동 추가
@Entity     //JPA 어노테이션, Entity: 테이블과 링크될 클래스(이름_테이블명)
public class Posts {            /*Posts 클래스: 실제 DB와 매칭될 클래스(Entity 클래스:setter 메소드를 만들지 않음)*/

    @Id//해당 테이블의 PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 생성규칙
    private long id;

    @Column(length=500, nullable=false) //테이블의 칼럼(기본값인경우 생략가능)
    private String title;

    @Column(columnDefinition = "TEXT", nullable=false)
    private String content;

    private String author;

    @Builder        //해당 클래스의 빌더 패턴 클래스 생성: 생성시점에 값을 채워줌(생성자역할과 동일)
    public Posts(String title, String content, String author){
        this.title=title;
        this.content=content;
        this.author=author;
    }
}
