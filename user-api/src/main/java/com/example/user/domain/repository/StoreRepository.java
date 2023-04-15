package com.example.user.domain.repository;
import com.example.user.domain.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    boolean existsByAddress(String address);



}
