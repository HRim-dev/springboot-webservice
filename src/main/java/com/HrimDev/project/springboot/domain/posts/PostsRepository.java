package com.HrimDev.project.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {//DB Layer 접근자(DAO)
                                                                   // JpaRepository<Entity클래스, PK타입> 상속하면 기본적인 CRUD 메소드 자동생성
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
