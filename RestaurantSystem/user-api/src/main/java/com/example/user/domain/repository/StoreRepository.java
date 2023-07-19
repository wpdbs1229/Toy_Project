package com.example.user.domain.repository;
import com.example.user.domain.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    boolean existsByAddress(String address);



    Optional<StoreEntity> findByStoreCode(String code);

}
