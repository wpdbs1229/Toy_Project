package com.hidevelop.weatherdiary.repository;


import com.hidevelop.weatherdiary.domain.DateWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DateWeatherRepository extends JpaRepository <DateWeather, LocalDate> {
    List<DateWeather> findAllByDate(LocalDate localDate);

}
