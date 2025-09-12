package com.ogbenioye.weatherservice.repository;

import com.ogbenioye.weatherservice.model.Daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyReportRepository extends JpaRepository<Daily, String> {
    List<Daily> findByDateBetween(Long start, Long end);
}
