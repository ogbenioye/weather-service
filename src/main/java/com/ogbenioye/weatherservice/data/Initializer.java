package com.ogbenioye.weatherservice.data;

import com.ogbenioye.weatherservice.model.Region;
import com.ogbenioye.weatherservice.repository.RegionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

    private final RegionRepository regionRepo;

    public Initializer(RegionRepository regionRepo) {
        this.regionRepo = regionRepo;
    }

    @Override
    public void run(String... args) {
        if (regionRepo.count() == 0) {
            regionRepo.save(new Region("Nigeria", 9.0820, 8.6753));
            regionRepo.save(new Region("Ghana", 7.9465, 1.0232));
            regionRepo.save(new Region("Kenya", 0.0236, 37.9062));
            regionRepo.save(new Region("South Africa", 30.5595, 22.9375));
            regionRepo.save(new Region("Tanzania", 6.3690, 34.8888));
        }
    }
}
