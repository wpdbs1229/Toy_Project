package com.hidevelop.weatherdiary.repository;

import com.hidevelop.weatherdiary.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMemoRepository extends JpaRepository<Memo,Integer> {

}
