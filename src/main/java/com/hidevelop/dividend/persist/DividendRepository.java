package com.hidevelop.dividend.persist;

import com.hidevelop.dividend.persist.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {

    List<DividendEntity> findAllByCompanyId(Long id);

    @Transactional
    void deleteAllByCompanyId(Long Id);
    //복합키로 설정했기 때문에 기본 select문보다 빠름
    boolean existsByCompanyIdAndDate(Long companyId, LocalDateTime date);
}
