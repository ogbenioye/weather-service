package com.ogbenioye.weatherservice.repository;

import com.ogbenioye.weatherservice.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region,String> {

}
