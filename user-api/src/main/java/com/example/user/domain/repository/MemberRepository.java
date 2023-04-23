package com.example.user.domain.repository;

import com.example.user.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Spliterator;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

   boolean existsByName(String name);

   Optional<MemberEntity> findByName(String name);
   Optional<MemberEntity> findByPhone(String phone);


}
