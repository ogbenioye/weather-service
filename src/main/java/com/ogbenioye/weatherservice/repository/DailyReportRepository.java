package com.ogbenioye.weatherservice.repository;

import com.ogbenioye.weatherservice.model.Daily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyReportRepository extends JpaRepository<Daily, String> {
    List<Daily> findByDateBetween(Long start, Long end);
}
