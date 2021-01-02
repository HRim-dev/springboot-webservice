package com.HrimDev.project.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts,Long> {//DB Layer 접근자(DAO)
                                                                   // JpaRepository<Entity클래스, PK타입> 상속하면 기본적인 CRUD 메소드 자동생성
}
