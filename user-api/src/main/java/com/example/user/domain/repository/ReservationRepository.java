package com.example.user.domain.repository;


import com.example.user.domain.entity.MemberEntity;
import com.example.user.domain.entity.ReservationEntity;
import com.example.user.domain.entity.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    boolean existsByMemberEntityAndStoreEntityAndDate(
            MemberEntity member,
            StoreEntity store,
            LocalDateTime Date
    );

    List<ReservationEntity> findAllByStoreEntity(StoreEntity storeEntity);

    Page<ReservationEntity> findAllByMemberEntity_Phone(String phone, Pageable pageable);

    Page<ReservationEntity> findAllByStoreEntity_StoreCode(String code, Pageable pageable);

    Optional<ReservationEntity> findByStoreEntity_StoreCodeAndMemberEntity_Phone(String phone, String storeCode);
}
