package com.spring316.user.repo;

import com.spring316.user.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select m from User m where m.id = :id")
    User getId(@Param("id") String id);

    Integer countById(@Param("id") String id);

    // 아이디로 user 찾기
    @Query("select m from User m where m.id = :id")
    Optional<User> getUser(@Param("id") String id);

}